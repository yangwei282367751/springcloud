package com.yangwei.goods.service.impl;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.yangwei.goods.domain.GoodsBasicInfo;
import com.yangwei.goods.repository.GoodsBasicRepository;
import com.yangwei.goods.service.GoodsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 杨威
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Resource
    private GoodsBasicRepository goodsBasicRepository;

    @Override
    @LcnTransaction
    public GoodsBasicInfo updateStocksNumberByGoodsId(String goodsId, Long number) {
        GoodsBasicInfo goodsBasicInfo = goodsBasicRepository.findByGoodsId(goodsId);
        goodsBasicInfo.setStocksNumber(number + goodsBasicInfo.getStocksNumber());
        return goodsBasicRepository.save(goodsBasicInfo);
    }
}
