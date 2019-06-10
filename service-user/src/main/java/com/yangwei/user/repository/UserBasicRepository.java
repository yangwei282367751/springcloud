package com.yangwei.user.repository;

import com.yangwei.user.domain.UserBasicInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author 杨威
 */
@Repository
public interface UserBasicRepository extends CrudRepository<UserBasicInfo,String> {

    /**
     * 根据用户ID查询用户
     * @param userId 用户ID
     * @return UserBasicInfo
     */
    UserBasicInfo findByUserId(String userId);
}
