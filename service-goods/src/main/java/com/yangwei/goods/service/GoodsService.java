package com.yangwei.goods.service;

import com.yangwei.goods.domain.GoodsBasicInfo;

/**
 * @author 杨威
 */
public interface GoodsService {

    /**
     * 更新库存
     * @param goodsId 商品ID
     * @param number 商品更新数量
     * @return GoodsBasicInfo
     */
    GoodsBasicInfo updateStocksNumberByGoodsId(String goodsId, Long number);

}
