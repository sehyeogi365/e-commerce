package kr.hhplus.be.server.common;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SessionAuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession(false); // 세션이 없으면 null 반환
        String uri = httpRequest.getRequestURI();

        // 세션에 로그인 정보가 없고 보호된 경로에 접근하는 경우
        if (session == null || session.getAttribute("userId") == null) {
            if (uri.startsWith("/api/v1/point/**")) { // 보호된 API 경로
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                httpResponse.getWriter().write("Unauthorized: Please log in.");
                return;
            }
        }

        if (session == null || session.getAttribute("userId") == null) {
            if (uri.startsWith("/api/v1/payment/**")) { // 보호된 API 경로
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                httpResponse.getWriter().write("Unauthorized: Please log in.");
                return;
            }
        }

        if (session == null || session.getAttribute("userId") == null) {
            if (uri.startsWith("/api/v1/orders/**")) { // 보호된 API 경로
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                httpResponse.getWriter().write("Unauthorized: Please log in.");
                return;
            }
        }

        if (session == null || session.getAttribute("userId") == null) {
            if (uri.startsWith("/api/v1/coupons/**")) { // 보호된 API 경로
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                httpResponse.getWriter().write("Unauthorized: Please log in.");
                return;
            }
        }

        // 다음 필터 또는 컨트롤러로 요청 전달
        chain.doFilter(request, response);
    }

}
