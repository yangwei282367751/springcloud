package com.yangwei.goods.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author 杨威
 */
@Data
@Entity
@Table(name="T_GOODS_BASIC_INFO")
public class GoodsBasicInfo {

  @Id
  @GeneratedValue(generator = "goods-uuid")
  @GenericGenerator(name="goods-uuid", strategy = "uuid")
  @Column(name="goods_id")
  private String goodsId;

  @Column(name="goods_name")
  private String goodsName;

  @Column(name="is_non_sold_out")
  private long isNonSoldOut;

  @Column(name="stocks_number")
  private long stocksNumber;

}
