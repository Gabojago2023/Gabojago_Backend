package com.gabojago.trip.auth.service;

import com.gabojago.trip.auth.dto.OAuthTokenDto;
import com.gabojago.trip.auth.dto.OAuthUserInfoDto;
import com.gabojago.trip.auth.exception.KakaoAuthenticateException;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;


@Slf4j
@Service
public class KakaoAuthService implements SocialAuthService {
    @Value("${auth.kakao.token-req-url}")
    private String KAKAO_TOKEN_REQUEST_URL;
    @Value("${auth.kakao.user-info-request-url}")
    private String KAKAO_USER_INFO_REQUEST_URL;

    @Value("${auth.kakao.client-id}")
    private String CLIENT_ID ;

    @Value("${auth.kakao.client-secret}")
    private String CLIENT_SECRET;

    @Value("${auth.kakao.redirect-url-sign}")
    private String REDIRECT_URI_SIGN ;

    @Value("${auth.kakao.redirect-url-login}")
    private String REDIRECT_URI_LOGIN ;

    @Autowired
    private RestTemplate restTemplate;




    @Override
    public OAuthTokenDto getToken(String code) {
        Map<String, Object> params = generateParams(code);
        log.debug("[GETTOKEN] " + params);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            requestBody.add(entry.getKey(), entry.getValue().toString());
        }

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        JSONParser parser;
        JSONObject elem;
        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    KAKAO_TOKEN_REQUEST_URL,
                    HttpMethod.POST,
                    requestEntity,
                    String.class);
            log.debug("[GETTOKEN2] " + responseEntity.getStatusCode());

            if (responseEntity.getStatusCode() != HttpStatus.OK) {
                log.debug("카카오 API 연결 실패 : code ={}", code);
                throw new KakaoAuthenticateException("[1] 카카오 서버와의 연결에 실패하였습니다.");
            }

            String responseBody = responseEntity.getBody();
            parser = new JSONParser();
            elem = (JSONObject) parser.parse(responseBody);
            log.debug("[DATA] " + elem);
            return OAuthTokenDto.builder()
                    .access_token(elem.get("access_token").toString())
                    .refresh_token(elem.get("refresh_token").toString())
                    .token_type(elem.get("token_type").toString())
                    .expires_in(elem.get("expires_in").toString())
                    .scope(elem.get("scope").toString())
                    .id_token(elem.get("id_token").toString())
                    .build();

        } catch (ParseException e) {
            log.debug("JSON 파싱 실패 : {}", e.getMessage());
            throw new KakaoAuthenticateException("요청에 인가 코드가 존재하지 않습니다.");
        } catch (Exception e) {
            log.debug("카카오 API 연결 실패: {}", code);
            log.debug(e.getMessage());
            throw new KakaoAuthenticateException("[2] 카카오 서버와의 연결에 실패하였습니다.");
        }
    }

    @Override
    public OAuthUserInfoDto getUserInfo(OAuthTokenDto dto) {
        log.debug("[getUserInfo] " + KAKAO_USER_INFO_REQUEST_URL);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", "Bearer " + dto.getAccess_token());

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        try {

            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    KAKAO_USER_INFO_REQUEST_URL, HttpMethod.POST, requestEntity, String.class);

            String responseBody = responseEntity.getBody();
            log.debug("[UserInfo-responseBody] " + responseBody);
            JSONParser parser = new JSONParser();
            JSONObject userInfo = (JSONObject) parser.parse(responseBody);

            JSONObject kakaoAccount = (JSONObject) userInfo.get("kakao_account");
            log.debug("[UserInfo-JsonObject-kakao_account] " + kakaoAccount);
            String email = (boolean) kakaoAccount.get("has_email") == true ? kakaoAccount.get("email").toString() : "";
            JSONObject properties = (JSONObject) userInfo.get("properties");
            log.debug("[UserInfo-JsonObject-properties] " + properties);

            return OAuthUserInfoDto.builder()
                    .providerId(userInfo.get("id").toString())
                    .email(email)
                    .name(properties.get("nickname").toString())
                    .profileImg(properties.get("profile_image").toString())
                    .build();

        } catch (ParseException e) {
            log.debug("파싱 실패: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private Map<String, Object> generateParams(String code) {
        Map<String, Object> params = new HashMap<>();
        params.put("grant_type", "authorization_code");
        params.put("client_id", CLIENT_ID);
        params.put("client_secret", CLIENT_SECRET);
        params.put("redirect_uri", REDIRECT_URI_SIGN);
        params.put("code", code);
        return params;
    }
}
