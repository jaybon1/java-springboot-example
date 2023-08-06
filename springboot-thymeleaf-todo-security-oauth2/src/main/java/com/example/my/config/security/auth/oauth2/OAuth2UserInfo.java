package com.example.my.config.security.auth.oauth2;

import java.util.Map;

public interface OAuth2UserInfo {
    String getProviderId();
    String getProvider();
    Map<String, Object> getAttributes();
}
