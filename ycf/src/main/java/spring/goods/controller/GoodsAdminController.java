package spring.goods.controller;

import spring.annotation.LoginRequired;
import spring.dto.BaseCommonResult;
import spring.dto.result.BasePage;
import spring.goods.dto.request.GoodsListReq;
import spring.goods.dto.request.GoodsListRequest;
import spring.goods.dto.response.GoodsStateResponse;
import spring.goods.service.GoodsService;
import spring.model.PGoods;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(description = "后台管理商品信息相关接口列表", basePath = "/goodsCenter/admin/goods")
@RestController
@RequestMapping("/goodsCenter/admin/goods")
public class GoodsAdminController {

    @Autowired
    private GoodsService goodsService;

    /**
     *
     * 功能描述:商品列表
     * @param request
     * @return
     */
    @ApiOperation(value = "分页查询商品列表", httpMethod = "GET")
    @RequestMapping(value = "/good", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public @ResponseBody BaseCommonResult<BasePage<PGoods>> list(@Valid GoodsListReq request) {
        return goodsService.list(request);
    }

    @ApiOperation(value = "查询商品状态数量", httpMethod = "GET")
    @RequestMapping(value = "/goodsState", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public @ResponseBody BaseCommonResult<GoodsStateResponse> goodsState() {
        return goodsService.goodsState();
    }
    /**
     *
     * 功能描述:商品列表
     * @param request
     * @return
     */
    @ApiOperation(value = "添加商品", httpMethod = "POST")
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @LoginRequired
    public BaseCommonResult<PGoods> createGoods(@Validated @RequestBody GoodsListRequest request) {
        return goodsService.createGoods(request);
    }

    @ApiOperation(value = "更新商品", httpMethod = "POST")
    @RequestMapping(value = "/update/good", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @LoginRequired
    public @ResponseBody
    BaseCommonResult<PGoods> update( @RequestBody @Valid GoodsListRequest request) {
        return goodsService.update(request);
    }


}
