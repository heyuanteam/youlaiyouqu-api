package spring.wechat.controller;

import io.swagger.annotations.ApiParam;
import spring.annotation.LoginRequired;
import spring.config.redis.util.RedisLockUtil;
import spring.dto.BaseCommonResult;
import spring.enums.UserErrorCodeEnum;
import spring.exception.GoodsException;
import spring.exception.MemberException;
import spring.goods.service.GoodsService;
import spring.mapper.UUserMemberMapper;
import spring.model.POrders;
import spring.model.UUserMember;
import spring.trade.service.OrderService;
import spring.utils.ResultBuilder;
import spring.wechat.dto.requset.PayReq;
import spring.wechat.dto.result.WechatAppIdResult;
import spring.wechat.service.PaymentService;
import spring.wechat.service.WechatService;
import spring.wechat.utils.BeanToMap;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.*;

@Api(description = "微信管理", basePath = "/payment/")
@RestController
@RequestMapping(value = "/payment/")
public class PaymentController {
    private static Logger logger = LoggerFactory.getLogger(PaymentController.class);
    private final static String GRANTTYPE = "authorization_code";
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private WechatService wechatService;
    @Autowired
    private UUserMemberMapper userMemberMapper;

    @ApiOperation(value = "获取微信唯一OPEN_ID", httpMethod = "GET")
    @RequestMapping(value ="/getOperatorIdOrderList/{code}",method = RequestMethod.GET)
    public BaseCommonResult<WechatAppIdResult> getOperatorIdOrderList(@PathVariable String code) throws Exception{
        logger.info("获取微信OPEN_ID请求参数:"+code);
        String operatorid = wechatService.getOpenid(code);
        logger.info("获取微信OPEN_ID返回参数:"+operatorid);
        WechatAppIdResult wechatAppIdResult = new WechatAppIdResult();
        wechatAppIdResult.setCode(operatorid);
        return ResultBuilder.success(wechatAppIdResult);
    }


    /**
     * <p>统一下单入口</p>
     *
     * @throws Exception
     */
    @ApiOperation(value = "下单接口", httpMethod = "POST")
    @ResponseBody
    @RequestMapping(value="toPay", method= RequestMethod.POST)
    @LoginRequired
    public BaseCommonResult toPay(@RequestBody PayReq req) throws Exception {

        List<POrders> orderInfo = orderService.getOrder(req);
        if(orderInfo.size() == 0){
            return ResultBuilder.fail("订单不存在！");
        }else if( Integer.parseInt(orderInfo.get(0).getOrderState()) > 0){
            //订单状态:0待支付,1支付成功,2支付失败,3待发货,4已发货,5确认收货,6订单完成,7申请退款,8退款中,9退款完成,10拒绝退款,11取消订单,12订单关闭
            return ResultBuilder.fail("订单已支付或者已取消!");
        }else{
            logger.info("【小程序支付服务】请求订单编号:["+orderInfo.get(0).getOrderNo()+"]");
            POrders orders = orderInfo.get(0);
            //加redis锁，解决一个会员同时多次请求支付
            Map<String, Object> resMap =  RedisLockUtil.executeSynchOperate((locked) ->
            {
                Map<String, Object> lockVO=null;
                try {
                    if (!locked) {
                        throw new GoodsException(UserErrorCodeEnum.REDIS_LOCK_FAIL.getCode(), UserErrorCodeEnum.REDIS_LOCK_FAIL.getMsg());
                    }
                    lockVO = paymentService.xcxPayment(orders.getOrderNo(),orders.getOrderPrice(),req.getOpenId(),orders.getOrderState());
                } catch (MemberException e){
                    throw new MemberException(e.getErrorCode(), e.getMessage());
                }catch(GoodsException e){
                    throw new MemberException(e.getResponseCode(), e.getMessage());
                } catch (Exception e) {
                     e.printStackTrace();
                }
                return lockVO;
            } ,"order_No"+orders.getOrderNo(), 3000);

            UUserMember uUserMember = userMemberMapper.selectByPrimaryKey(orderInfo.get(0).getUserId());
            if (uUserMember.getButton() == 1){
                uUserMember.setButton(0);
                userMemberMapper.updateByPrimaryKey(uUserMember);
            }
            if("SUCCESS".equals(resMap.get("returnCode")) && "OK".equals(resMap.get("returnMsg"))){
                //统一下单成功
                resMap.remove("returnCode");
                resMap.remove("returnMsg");
                logger.info("【小程序支付服务】支付下单成功！");
                return ResultBuilder.success(resMap);
            }else{
                logger.info("【小程序支付服务】支付下单失败！原因:"+resMap.get("returnMsg"));
                Object returnMsg = resMap.get("returnMsg");
                return ResultBuilder.fail(returnMsg.toString());
            }
        }
    }


    @ApiOperation(value = "支付回调接口", httpMethod = "GET")
    @RequestMapping(value ="/toPay/{orderNo}/{type}",method = RequestMethod.GET)
    public BaseCommonResult getOperatorIdOrderList(@PathVariable String orderNo,@ApiParam("1支付成功2支付失败") @PathVariable int type) {
        logger.info("支付回调接口请求参数:"+orderNo+"成功与失败:"+type);
        PayReq req = new PayReq();
        req.setOrderNo(orderNo);
        List<POrders> orderInfo = orderService.getOrder(req);
        if (orderInfo.size()>0){
            if (type == 1){
                orderService.updateOrder(orderInfo.get(0),"1");
            }else{
                orderService.updateOrder(orderInfo.get(0),"2");
            }
        }
        return ResultBuilder.success();
    }








    /**
     * <p>回调Api</p>
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @ApiOperation(value = "支付回调接口")
    @RequestMapping(value="xcxNotify")
    public void xcxNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        InputStream inputStream =  request.getInputStream();
        //获取请求输入流
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len=inputStream.read(buffer))!=-1){
            outputStream.write(buffer,0,len);
        }
        outputStream.close();
        inputStream.close();
        Map<String,Object> map = BeanToMap.getMapFromXML(new String(outputStream.toByteArray(),"utf-8"));
        logger.info("【小程序支付回调】 回调数据： \n"+map);
        String resXml = "";
        String returnCode = (String) map.get("return_code");
        String orderId = (String) map.get("order_id");
        PayReq req = new PayReq();
        req.setOrderNo(orderId);
        List<POrders> orderInfo = orderService.getOrder(req);
        if ("SUCCESS".equalsIgnoreCase(returnCode)) {
            String returnmsg = (String) map.get("result_code");
            if("SUCCESS".equals(returnmsg)){
                //更新数据
                int result = paymentService.xcxNotify(map);
                if(result > 0){

                    orderService.updateOrder(orderInfo.get(0),"1");
                    //支付成功
                    resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                            + "<return_msg><![CDATA[OK]]></return_msg>"+"</xml>";
                }
            }else{
                orderService.updateOrder(orderInfo.get(0),"2");
                resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                        + "<return_msg><![CDATA[报文为空]></return_msg>" + "</xml>";
                logger.info("支付失败:"+resXml);
            }
        }else{
            orderService.updateOrder(orderInfo.get(0),"2");
            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                    + "<return_msg><![CDATA[报文为空]></return_msg>" + "</xml>";
            logger.info("【订单支付失败】");
        }

        logger.info("【小程序支付回调响应】 响应内容：\n"+resXml);
        response.getWriter().print(resXml);
    }

}