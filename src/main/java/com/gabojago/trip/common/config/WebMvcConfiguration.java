package com.gabojago.trip.common.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    //TODO: http method check

    private final List<String> PATTERNS = Arrays.asList("/auth/refresh-access-token",
"/one-pick",
"/one-pick/like",
"/one-pick/random",
"/one-pick/random-all",
"/places",
"/places/{placeId}/comment",
"/places/{placeId}/comment/{commentId}",
"/places/{placeId}/comment/{commentId}",
"/places/{placeId}/detail",
"/places/{placeId}/scrap",
"/places/keyword",
"/places/scrap",
"/ticket/count",
"/ticket/purchase",
"/plans/**",
"/users/nickname-available",
"/attendance/**"); // 조회도 로그인 했을 때만? -> comments 조회는 /comments로

    private JwtInterceptor jwtInterceptor;

    public WebMvcConfiguration(JwtInterceptor jwtInterceptor) {
        this.jwtInterceptor = jwtInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(jwtInterceptor).addPathPatterns(PATTERNS);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // TODO: setting pattern CORS
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedOrigins("http://localhost:3000","https://one-pick-go.com")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true);

    }

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }



}
/*
/auth/{SOCIAL_TYPE}/login
/auth/{SOCIAL_TYPE}/signin

/one-pick/rank
/one-pick/rankers
/places/{placeId}/comments
/places/gugun/{sidoId}
/places/like-place
/places/random-images
/places/sido-list
 */