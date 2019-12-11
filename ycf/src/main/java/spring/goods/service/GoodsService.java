package spring.goods.service;

import spring.dto.BaseCommonResult;
import spring.dto.result.BasePage;
import spring.exception.GoodsException;
import spring.goods.dto.request.GoodsListReq;
import spring.goods.dto.request.GoodsListRequest;
import spring.goods.dto.request.GoodsMemberRequset;
import spring.goods.dto.request.RecommendedRequest;
import spring.goods.dto.response.GoodsDetailsResponse;
import spring.goods.dto.response.GoodsMemberResponse;
import spring.goods.dto.response.RecommendedResponse;
import spring.mapper.GoodsMapper;
import spring.mapper.PGoodsMapper;
import spring.model.PGoods;
import spring.model.PGoodsExample;
import spring.utils.ResultBuilder;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class GoodsService {

    @Autowired
    private DozerBeanMapper dozer;
    @Autowired
    private PGoodsMapper pGoodsMapper;
    @Autowired
    private GoodsMapper goodsMapper;

    @Transient
    public BaseCommonResult<PGoods> createGoods(GoodsListRequest request) {
        log.info("请求参数:{}",request);
        PGoods map = dozer.map(request, PGoods.class);
        int i = pGoodsMapper.insertSelective(map);
        log.info("返回参数:{}",map);
        return ResultBuilder.success(map);
    }

    @Transient
    public BaseCommonResult<PGoods> update( GoodsListRequest request) {
        log.info("请求参数:{}",request);
        PGoods map = dozer.map(request, PGoods.class);
        int i = pGoodsMapper.updateByPrimaryKeySelective(map);
        log.info("返回参数:{}",map);
        return ResultBuilder.success(map);
    }


    public BaseCommonResult<BasePage<PGoods>> list(GoodsListReq request) {
        BasePage<PGoods> pageResult = new BasePage();
        log.info("分页查询商品列表,请求参数为：{}", request);
        try {
            PageHelper.startPage(request.getPage(), request.getPageSize());
            List<PGoods> list = goodsMapper.selectGoodsList(request);

            PageInfo<PGoods> pageInfo = new PageInfo<>(list);
            pageResult.setList(list);
            pageResult.setPageInfo(pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getPages(), pageInfo.getTotal());
        }catch (GoodsException e) {
            log.info("分页查询商品列表异常，异常信息为：{}", e);
            ResultBuilder.fail("系统异常");
        }
        log.info("分页查询商品列表接口结束");
        return ResultBuilder.success(pageResult);
    }

    /**
     * 新品推荐
     * @param requset
     * @return
     */
    public BaseCommonResult<BasePage<GoodsMemberResponse>> goodsMemberGoods(GoodsMemberRequset requset) {
        BasePage<GoodsMemberResponse> pageResult = new BasePage();
        log.info("会员新品分页查询商品列表,请求参数为：{}", requset);
        try {
            PageHelper.startPage(requset.getPage(), requset.getPageSize());
            PGoodsExample example = new PGoodsExample();
            example.createCriteria().andGoodsTypeEqualTo(0).andGoodsStateEqualTo(0).andGoodsNumTypeEqualTo(1);
            example.setOrderByClause("create_time desc");
            List<PGoods> list = pGoodsMapper.selectByExample(example);
          List<GoodsMemberResponse> goodsMemberResponseList = new ArrayList<>();
            if (list.size()>0){
                GoodsMemberResponse goodsMemberResponse = new GoodsMemberResponse();
                for (PGoods  goods:list) {
                    GoodsMemberResponse map = dozer.map(goods, GoodsMemberResponse.class);
                    goodsMemberResponseList.add(map);
                }
            }
            PageInfo<GoodsMemberResponse> pageInfo = new PageInfo<>(goodsMemberResponseList);
            pageResult.setList(goodsMemberResponseList);
            pageResult.setPageInfo(pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getPages(), pageInfo.getTotal());
        }catch (GoodsException e) {
            log.info("会员新品分页查询商品列表异常，异常信息为：{}", e);
            ResultBuilder.fail("系统异常");
        }
        log.info("会员新品分页查询商品列表接口结束");
        return ResultBuilder.success(pageResult);
    }

    public BaseCommonResult<BasePage<RecommendedResponse>> recommendedlist(RecommendedRequest requset) {
        BasePage<RecommendedResponse> pageResult = new BasePage();
        log.info("会员底部推荐分页查询商品列表,请求参数为：{}", requset);
        try {
            PageHelper.startPage(requset.getPage(), requset.getPageSize());
            List<RecommendedResponse> list = goodsMapper.selectRecommendedlist(requset);
            PageInfo<RecommendedResponse> pageInfo = new PageInfo<>(list);
            pageResult.setList(list);
            pageResult.setPageInfo(pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getPages(), pageInfo.getTotal());
        }catch (GoodsException e) {
            log.info("会员底部推荐分页查询商品列表异常，异常信息为：{}", e);
            ResultBuilder.fail("系统异常");
        }
        log.info("会员底部推荐分页查询商品列表接口结束");
        return ResultBuilder.success(pageResult);
    }

    public BaseCommonResult<BasePage<RecommendedResponse>> searchlist(RecommendedRequest requset) {
        BasePage<RecommendedResponse> pageResult = new BasePage();
        log.info("会员底部推荐分页查询商品列表,请求参数为：{}", requset);
        try {
            PageHelper.startPage(requset.getPage(), requset.getPageSize());
            List<RecommendedResponse> list = goodsMapper.searchlist(requset);
            PageInfo<RecommendedResponse> pageInfo = new PageInfo<>(list);
            pageResult.setList(list);
            pageResult.setPageInfo(pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getPages(), pageInfo.getTotal());
        }catch (GoodsException e) {
            log.info("会员底部推荐分页查询商品列表异常，异常信息为：{}", e);
            ResultBuilder.fail("系统异常");
        }
        log.info("会员底部推荐分页查询商品列表接口结束");
        return ResultBuilder.success(pageResult);
    }

    public GoodsDetailsResponse goodsDetails(Long id) {
        log.info("商品详情查询ID: {}",id);
        PGoods goods = pGoodsMapper.selectByPrimaryKey(id);
        GoodsDetailsResponse map = dozer.map(goods, GoodsDetailsResponse.class);
        log.info("商品详情查询返回: {}",map);
        return map;
    }
}