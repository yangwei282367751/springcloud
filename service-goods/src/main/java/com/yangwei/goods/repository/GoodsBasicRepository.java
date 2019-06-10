package com.yangwei.goods.repository;

import com.yangwei.goods.domain.GoodsBasicInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author 杨威
 */
@Repository
public interface GoodsBasicRepository extends CrudRepository<GoodsBasicInfo,String> {

    /**
     * 通过Id查询商品详情
     * @param goodsId 商品Id
     * @return GoodsBasicInfo
     */
    GoodsBasicInfo findByGoodsId(String goodsId);

}
