package spring.goods.controller;

import spring.annotation.LoginRequired;
import spring.dto.BaseCommonResult;
import spring.goods.dto.request.*;
import spring.goods.dto.response.TypeAttrAllListResponse;
import spring.goods.dto.response.TypeAttrAndAttrValueAllListResponse;
import spring.goods.dto.response.TypeAttrDetailsResponse;
import spring.goods.dto.response.TypeAttrListResponse;
import spring.goods.service.TypeAttrService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(description = "商品attr模块后台相关接口列表", basePath = "/admin/typeAttr")
@RestController
@RequestMapping("/goodsCenter/admin/typeAttr")
public class TypeAttrController {

	@Autowired
	private TypeAttrService typeAttrService;

	@ApiOperation(value = "分页查询商品attr列表", httpMethod = "GET")
	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	public @ResponseBody BaseCommonResult<TypeAttrListResponse> list(
			@Valid TypeAttrListRequest request) {
		return typeAttrService.list(request);
	}
	
	@ApiOperation(value = "获取商品attr及属性值全部列表", httpMethod = "GET")
	@RequestMapping(value = "/allAttrAndAttrValueList", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	public @ResponseBody
	BaseCommonResult<List<TypeAttrAndAttrValueAllListResponse>> allAttrAndAttrValueList(
			@Valid TypeAttrAndAttrValueAllListRequest request) {
		return typeAttrService.allAttrAndAttrValueList(request);
	}
	
	@ApiOperation(value = "查询商品attr全部列表", httpMethod = "GET")
	@RequestMapping(value = "/allList", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	public @ResponseBody BaseCommonResult<List<TypeAttrAllListResponse>> allList(
			@Valid TypeAttrAllListRequest request) {
		return typeAttrService.allList(request);
	}
	
	@ApiOperation(value = "查询商品attr详情", httpMethod = "GET")
	@RequestMapping(value = "/details", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	public @ResponseBody BaseCommonResult<TypeAttrDetailsResponse> details(
			@RequestParam("id") Integer id) {
		return typeAttrService.details(id);
	}
	
	@ApiOperation(value = "更新商品attr", httpMethod = "POST")
	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@LoginRequired
	public @ResponseBody BaseCommonResult<Void> update(
			@RequestBody @Valid TypeAttrUpdateRequest request) {
		return typeAttrService.update(request);
	}
	
	@ApiOperation(value = "新增商品attr", httpMethod = "POST")
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@LoginRequired
	public @ResponseBody BaseCommonResult<Void> add(
			@RequestBody @Valid TypeAttrAddRequest request) {
		return typeAttrService.add(request);
	}
	
	@ApiOperation(value = "删除商品attr", httpMethod = "DELETE")
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE, produces = "application/json;charset=utf-8")
	@LoginRequired
	public @ResponseBody BaseCommonResult<Void> delete(
			@RequestParam("id") Integer id) {
		return typeAttrService.delete(id);
	}
}
