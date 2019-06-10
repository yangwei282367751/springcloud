package com.yangwei.oauth2.config;

import com.yangwei.oauth2.domain.UserBasicInfo;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * 生成token携带信息
 * @author 杨威
 */
public class CustomTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        final Map<String, Object> additionalInfo = new HashMap<>(16);
          UserBasicInfo userBasicInfo = (UserBasicInfo) authentication.getUserAuthentication().getPrincipal();
          additionalInfo.put("username", userBasicInfo.getUsername());
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;
    }
}
