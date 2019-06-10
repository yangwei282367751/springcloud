package com.yangwei.oauth2.service;

import com.yangwei.oauth2.domain.UserBasicInfo;
import com.yangwei.oauth2.repository.UserBasicRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author 杨威
 */
@Service
public class LoginUserDetailsService implements UserDetailsService {

    @Resource
    private UserBasicRepository userBasicRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserBasicInfo loginUser = userBasicRepository.findByUsername(username);
        return Optional.ofNullable(loginUser)
                .orElseThrow(() -> new UsernameNotFoundException("用户不存在"));
    }
}
