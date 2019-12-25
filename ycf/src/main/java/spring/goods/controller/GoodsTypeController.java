package spring.goods.controller;

import spring.annotation.LoginRequired;
import spring.dto.BaseCommonResult;
import spring.goods.dto.request.GoodsTypeAddRequest;
import spring.goods.dto.request.GoodsTypeAllListRequest;
import spring.goods.dto.request.GoodsTypeListRequest;
import spring.goods.dto.request.GoodsTypeUpdateRequest;
import spring.goods.dto.response.GoodsTypeDetailsResponse;
import spring.goods.dto.response.GoodsTypeResponse;
import spring.goods.dto.response.GoodsTypeViewForSupplierResponse;
import spring.goods.dto.response.GoodsTypeViewListResponse;
import spring.goods.service.GoodsTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(description = "商品分类模块后台相关接口列表", basePath = "/admin/goodsType")
@RestController
@RequestMapping("/goodsCenter/admin/goodsType")
public class GoodsTypeController {

	@Autowired
	private GoodsTypeService goodsTypeService;

	@ApiOperation(value = "分页查询商品分类列表", httpMethod = "GET")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public @ResponseBody
	BaseCommonResult<GoodsTypeViewListResponse> queryOrderListByPage(
			@Valid GoodsTypeListRequest req) {
		return goodsTypeService.list(req);
	}
	
	@ApiOperation(value = "查询商品分类全部列表", httpMethod = "GET")
	@RequestMapping(value = "/allList", method = RequestMethod.GET)
	public @ResponseBody BaseCommonResult<List<GoodsTypeResponse>> queryOrderListByPage(
			@Valid GoodsTypeAllListRequest req) {
		return goodsTypeService.allList(req);
	}
	
	@ApiOperation(value = "查询商品分类详情", httpMethod = "GET")
	@RequestMapping(value = "/details", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	public @ResponseBody BaseCommonResult<GoodsTypeDetailsResponse> details(
			@RequestParam("id") Integer id) {
		return goodsTypeService.details(id);
	}
	
	@ApiOperation(value = "更新商品分类", httpMethod = "POST")
	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@LoginRequired
	public @ResponseBody BaseCommonResult<Void> update(
			@RequestBody @Valid GoodsTypeUpdateRequest request) {
		return goodsTypeService.update(request);
	}
	
	@ApiOperation(value = "新增商品分类", httpMethod = "POST")
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@LoginRequired
	public @ResponseBody BaseCommonResult<Void> add(
			@RequestBody @Valid GoodsTypeAddRequest request) {
		return goodsTypeService.add(request);
	}
	
	@ApiOperation(value = "删除商品分类", httpMethod = "DELETE")
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE, produces = "application/json;charset=utf-8")
	@LoginRequired
	public @ResponseBody BaseCommonResult<Void> delete(
			@RequestParam(name="id") Integer id) {
		return goodsTypeService.delete(id);
	}

    @ApiOperation(value = "获取商家类目列表", httpMethod = "GET")
    @RequestMapping(value = "/supplier/list", method = RequestMethod.GET)
    public @ResponseBody BaseCommonResult<List<GoodsTypeViewForSupplierResponse>> querySupplierTypeListByPage(@Valid GoodsTypeAllListRequest request) {
        return goodsTypeService.listForSupplier(request);
    }
}
