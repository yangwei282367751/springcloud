package com.yangwei.user.controller;

import com.yangwei.common.entity.ResponseEntity;
import com.yangwei.user.domain.UserBasicInfo;
import com.yangwei.user.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 杨威
 */
@RestController
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 创建用户
     * @param userBasicInfo 用户基本信息
     * @return ResponseEntity
     */
    @PostMapping("/createByUserBasicInfo")
    public UserBasicInfo createByUserBasicInfo(UserBasicInfo userBasicInfo) {
        return userService.createByUserBasicInfo(userBasicInfo);
    }

    /**
     * 查询用户
     * @param userId 用户ID
     * @return ResponseEntity
     */
    @GetMapping("/findByUserId")
    public UserBasicInfo findByUserId(String userId){
        return userService.findByUserId(userId);
    }
}
