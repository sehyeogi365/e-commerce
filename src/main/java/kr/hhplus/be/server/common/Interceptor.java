package kr.hhplus.be.server.common;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

public class Interceptor implements HandlerInterceptor {

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

            if(uri.startsWith("/api/v1/payment/")) {
                return false;//못가게 하려면 폴스
            }

            if(uri.startsWith("/api/v1/orders/")) {
                return false;
            }

            if(uri.startsWith("/api/v1/coupons/receive")) {
                return false;
            }

            if(uri.startsWith("/api/v1/coupons/")) {
                return false;
            }
        }

        return true;
    }
}
