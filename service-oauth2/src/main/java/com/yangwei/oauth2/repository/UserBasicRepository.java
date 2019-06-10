package com.yangwei.oauth2.repository;

import com.yangwei.oauth2.domain.UserBasicInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author 杨威
 */
@Repository
public interface UserBasicRepository extends CrudRepository<UserBasicInfo,String> {

    /**
     * 根据用户名查询用户信息
     * @param username 用户名
     * @return UserBasicInfo用户实体
     */
    UserBasicInfo findByUsername(String username);
}
