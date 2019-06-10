package com.yangwei.order.domain;

import lombok.Data;

/**
 * @author 杨威
 */
@Data
public class GoodsBasicInfo {


  private String goodsId;

  private String goodsName;

  private long isNonSoldOut;

  private long stocksNumber;

}
