package com.yangwei.user.service;

import com.yangwei.user.domain.UserBasicInfo;

/**
 * @author 杨威
 */
public interface UserService {

    /**
     * 根据用户ID查询用户
     * @param userId 用户ID
     * @return UserBasicInfo
     */
    UserBasicInfo findByUserId(String userId);

    /**
     * 新增用户
     * @param userBasicInfo 用户信息
     * @return UserBasicInfo
     */
    UserBasicInfo createByUserBasicInfo(UserBasicInfo userBasicInfo);
}
