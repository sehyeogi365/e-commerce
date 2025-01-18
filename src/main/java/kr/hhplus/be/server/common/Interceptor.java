package kr.hhplus.be.server.common;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Slf4j
public class Interceptor implements HandlerInterceptor {//TODO 중복코드 제거

    private boolean isProtectedPath(String uri) {
        return uri.startsWith("/api/v1/point/") ||
                uri.startsWith("/api/v1/payment/") ||
                uri.startsWith("/api/v1/orders/") ||
                uri.startsWith("/api/v1/coupons/");
    }

    @Override
    public boolean preHandle(
            HttpServletRequest request
            , HttpServletResponse response
            , Object handler
    ) throws IOException {

        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        String uri = request.getRequestURI();

        if(userId == null) {//로그인 안 되었을때
            if(uri.startsWith("/api/v1/product/")) {
                return true;//못가게 하려면 폴스
            }

            if(isProtectedPath(uri)){
                log.info("Unauthorized access attempt. URI: {}, Client IP: {}", uri, request.getRemoteAddr());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                log.info("로그인후 접속해주시길 바랍니다." + uri);
                return false;//못가게 하려면 폴스
            }

        }
        return true;
    }
}
