package com.yangwei.order.domain;


import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author 杨威
 */
@Data
@Entity
@Table(name="T_ORDER_BASIC_INFO")
public class OrderBasicInfo {

  @Id
  @GeneratedValue(generator = "order-uuid")
  @GenericGenerator(name="order-uuid", strategy = "uuid")
  @Column(name="order_id")
  private String orderId;

  @Column(name="order_name")
  private String orderName;

  @Column(name="order_user_id")
  private String orderUserId;

  @Column(name="goods_id")
  private String goodsId;

  @Column(name="order_create_time")
  private long orderCreateTime;

}
