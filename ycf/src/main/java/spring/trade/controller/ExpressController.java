package spring.trade.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import spring.annotation.LoginRequired;
import spring.config.alioss.AliHttpUtils;
import spring.dto.BaseCommonResult;
import spring.utils.ResultBuilder;

import java.util.*;

@Api(description = "物流接口", basePath = "/expressCenter/express")
@RestController
@RequestMapping("/ordersCenter/express")
@Slf4j
public class ExpressController {

    /**
     *
     * 功能描述:查询物流
     * @param expressNo
     * @return
     */
    @ApiOperation(value = "物流查询", httpMethod = "GET")
    @RequestMapping(value = "/expre/{expressNo}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @LoginRequired
    public BaseCommonResult getExpress(@ApiParam("物流单号") @PathVariable String expressNo) {
        String host = "https://wuliu.market.alicloudapi.com";
        String path = "/kdi";

        String method = "GET";

        String appcode = "02245a89168c4477beeef74e2b081090";  // !!! 替换这里填写你自己的AppCode 请在买家中心查看

        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE " + appcode); //格式为:Authorization:APPCODE 83359fd73fe11248385f570e3c139xxx
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("no", expressNo);  // !!! 请求参数  运单号

        // 物流信息
        String returnStr = "";

        Map<String, Object> returnMap = new HashMap<String, Object>();

        try {
            log.info("---------开始查询运单号为{}的物流信息", expressNo);

            // 重新查询最新物流信息
            HttpResponse response = AliHttpUtils.doGet(host, path, method, headers, querys);

            // 从返回信息中拿到
            returnStr = EntityUtils.toString(response.getEntity());

            if (StringUtils.isEmpty(returnStr)) {
                ResultBuilder.fail("查询物流信息失败");
            }
            log.info("---------此次查询的物流信息来至于重新查询");
            log.info("---------查询运单号为{}的物流信息结束,物流信息：{}", expressNo, returnStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultBuilder.success(returnStr);
    }
}
