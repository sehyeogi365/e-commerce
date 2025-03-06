package kr.hhplus.be.server.config;

import kr.hhplus.be.server.interfaces.api.common.Interceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        Interceptor interceptor = new Interceptor();//객체 생성
        registry.addInterceptor(interceptor)
                .addPathPatterns("/api/v1/**")//인터셉터를 거쳐서 처리할 url 규칙
                .excludePathPatterns("/api/v1/products/**");//추가 패턴 인터셉터를 거치지 않을 예외 url 규칙
    }

}
