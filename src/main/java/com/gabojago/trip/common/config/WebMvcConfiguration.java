package com.gabojago.trip.common.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    //TODO: http method check
    private final List<String> PATTERNS = Arrays.asList("/users/**","/articles/**/edit",
            "/like-places/**",
            "places/scrap",
            "places/**/scrap",
            "places/**/comment/**"); // 조회도 로그인 했을 때만?

    private JwtInterceptor jwtInterceptor;

    public WebMvcConfiguration(JwtInterceptor jwtInterceptor) {
        this.jwtInterceptor = jwtInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // registry.addInterceptor(jwtInterceptor).addPathPatterns(PATTERNS);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // TODO: setting pattern CORS
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true);

    }


}
