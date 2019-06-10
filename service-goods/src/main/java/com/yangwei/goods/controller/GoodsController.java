package com.yangwei.goods.controller;

import com.yangwei.goods.domain.GoodsBasicInfo;
import com.yangwei.goods.service.GoodsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 杨威
 */
@RestController
public class GoodsController {

    @Resource
    private GoodsService goodsService;

    @PostMapping("/updateStocksNumberByGoodId")
    public GoodsBasicInfo updateStocksNumberByGoodId (String goodsId, Long number){
        return goodsService.updateStocksNumberByGoodsId(goodsId,number);
    }
}
