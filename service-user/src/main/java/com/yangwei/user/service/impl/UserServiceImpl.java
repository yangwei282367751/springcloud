package com.yangwei.user.service.impl;

import com.yangwei.user.domain.UserBasicInfo;
import com.yangwei.user.repository.UserBasicRepository;
import com.yangwei.user.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 杨威
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserBasicRepository userBasicRepository;

    @Override
    public UserBasicInfo findByUserId(String userId) {
        return userBasicRepository.findByUserId(userId);
    }

    @Override
    public UserBasicInfo createByUserBasicInfo(UserBasicInfo userBasicInfo) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userBasicInfo.setPassword(passwordEncoder.encode(userBasicInfo.getPassword()));
        return userBasicRepository.save(userBasicInfo);
    }
}
