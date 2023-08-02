package com.example.my.config.security.auth.oauth2;

import java.util.Map;

public class GoogleOAuth2UserInfo implements OAuth2UserInfo {

    private final Map<String, Object> attributes;

    public GoogleOAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProviderId() {
        return String.valueOf(attributes.get("sub"));
    }

    @Override
    public String getProvider() {
        return "google";
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

}
