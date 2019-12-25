package spring.trade.controller;

import spring.annotation.LoginRequired;
import spring.dto.BaseCommonResult;
import spring.dto.result.BasePage;
import spring.model.MRecoveryGoods;
import spring.trade.dto.request.AdminOrderReq;
import spring.trade.dto.request.AdminRecoveryRequest;
import spring.trade.dto.request.RecoveryOfferRequest;
import spring.trade.dto.request.RecoveryRequest;
import spring.trade.dto.result.AdminTradeDetailsResult;
import spring.trade.dto.result.AdminTradeResult;
import spring.trade.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(description = "订单相关接口列表", basePath = "/ordersCenter/admin/orders")
@RestController
@RequestMapping("/ordersCenter/admin/orders")
public class TradeAdminController {

    @Autowired
    private OrderService pOrderService;

    /**
     *
     * 功能描述:查询会员订单
     * @param request
     * @return
     */
    @ApiOperation(value = "查询会员订单", httpMethod = "POST")
    @RequestMapping(value = "/order", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @LoginRequired
    public BaseCommonResult<BasePage<AdminTradeResult>> getAdminOrder(@Validated @RequestBody AdminOrderReq request) {
        return pOrderService.getAdminOrder(request);
    }

    /**
     *
     * 功能描述:查询会员订单
     * @param orderId
     * @return
     */
    @ApiOperation(value = "订单详情", httpMethod = "GET")
    @RequestMapping(value = "/order/{orderId}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @LoginRequired
    public BaseCommonResult<AdminTradeDetailsResult> getAdminOrderDetails(@PathVariable Long orderId) {
        return pOrderService.getAdminOrderDetails(orderId);
    }

    @ApiOperation(value = "取消订单", httpMethod = "GET")
    @RequestMapping(value = "/delete/order/{orderNo}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @LoginRequired
    public BaseCommonResult deleteOrder(@PathVariable Long orderNo) {
        return pOrderService.deleteOrder(orderNo,null);
    }

    /**
     *
     * 功能描述:添加回收物品
     * @param request
     * @return
     */
    @ApiOperation(value = "会员回收列表", httpMethod = "POST")
    @RequestMapping(value = "/recovery/orderList", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @LoginRequired
    public BaseCommonResult<BasePage<MRecoveryGoods>> recoveryOrderList(@Validated @RequestBody AdminRecoveryRequest request) {
        return pOrderService.adminRecoveryOrderList(request);
    }

    @ApiOperation(value = "回收订单关闭", httpMethod = "GET")
    @RequestMapping(value = "/recovery/delete/order/{orderNo}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @LoginRequired
    public BaseCommonResult deleteRecoveryOrder(@PathVariable Long orderNo) {
        return pOrderService.deleteRecoveryOrder(orderNo);
    }
    @ApiOperation(value = "确认回收订单", httpMethod = "GET")
    @RequestMapping(value = "/recovery/update/order/{orderNo}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @LoginRequired
    public BaseCommonResult updateRecoveryOrder(@PathVariable Long orderNo) {
        return pOrderService.updateRecoveryOrder(orderNo);
    }
    @ApiOperation(value = "回收订单详细", httpMethod = "GET")
    @RequestMapping(value = "/recovery/detailed/order/{orderNo}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @LoginRequired
    public BaseCommonResult<MRecoveryGoods> detailedRecoveryOrder(@PathVariable Long orderNo) {
        return pOrderService.detailedRecoveryOrder(orderNo);
    }

    @ApiOperation(value = "会员回收报价", httpMethod = "POST")
    @RequestMapping(value = "/recovery/offer", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @LoginRequired
    public BaseCommonResult<MRecoveryGoods> recoveryOffer(@Validated @RequestBody RecoveryOfferRequest request) {
        return pOrderService.recoveryOffer(request);
    }

    @ApiOperation(value = "会员回收二次报价", httpMethod = "POST")
    @RequestMapping(value = "/recovery/two/offer", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @LoginRequired
    public BaseCommonResult<MRecoveryGoods> eRecoveryOffer(@Validated @RequestBody RecoveryOfferRequest request) {
        return pOrderService.eRecoveryOffer(request);
    }
}
