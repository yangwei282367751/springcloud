package com.yangwei.order.repository;

import com.yangwei.order.domain.OrderBasicInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author 杨威
 */
@Repository
public interface OrderBasicRepository extends CrudRepository<OrderBasicInfo,String> {

    /**
     * 根据用户ID查询用户
     * @param orderId 用户ID
     * @return UserBasicInfo
     */
    OrderBasicInfo findByOrderId(String orderId);
}
