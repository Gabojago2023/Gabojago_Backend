package com.gabojago.trip.auth.oauth;

public interface OAuthUserInfo {
    String getProviderId();
    String getProvider();
    String getEmail();
    String getName();
}
