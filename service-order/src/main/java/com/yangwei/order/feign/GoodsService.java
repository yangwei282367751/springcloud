package com.yangwei.order.feign;

import com.yangwei.common.entity.ResponseEntity;
import com.yangwei.order.domain.GoodsBasicInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 杨威
 */
@FeignClient(value = "service-goods")
public interface GoodsService {

    /**
     * 远程调用更新商品仓库数量
     * @param goodsId 商品Id
     * @param number 更新数量
     * @return 请求结果
     */
    @PostMapping("/updateStocksNumberByGoodId")
    ResponseEntity<GoodsBasicInfo> updateStocksNumberByGoodId (@RequestParam("goodsId") String goodsId, @RequestParam("number") Long number);

}
