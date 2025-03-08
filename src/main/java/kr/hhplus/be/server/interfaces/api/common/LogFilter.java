package kr.hhplus.be.server.interfaces.api.common;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
@Slf4j
public class LogFilter implements Filter {//TODO HTTP 메소드, 요청/응답 시간, 클라이언트 IP, 응답 상태 코드

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("Log Filter init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String uuid = UUID.randomUUID().toString();
        MDC.put("logId", uuid);

        try {
            chain.doFilter(request, response);
        } finally {
            MDC.clear(); // 요청-응답 완료 후 정리
        }
    }

    @Override
    public void destroy() {
        log.info("Log Filter destroy");
    }

//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//
//        HttpSession session = httpRequest.getSession(false); // 세션이 없으면 null 반환
//        String uri = httpRequest.getRequestURI();
//        String method = httpRequest.getMethod();
//        String clientIp = httpRequest.getRemoteAddr();
//
//        long startTime = System.currentTimeMillis();
//        long endTime = System.currentTimeMillis();
//        log.info("Request URI: {}, Duration: {} ms", uri, (endTime - startTime));
//
//        // 세션에 로그인 정보가 없고 보호된 경로에 접근하는 경우
//        if (session == null || session.getAttribute("userId") == null) {
//            if (uri.startsWith("/api/v1/point/**")) { // 보호된 API 경로
//                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                httpResponse.getWriter().write("Unauthorized: Please log in.");
//                log.info("uri" + uri);
//                log.info("Unauthorized access. URI: {}, Method: {}, Client IP: {}, StartTime: {}", uri, method, clientIp, startTime);
//                return;
//            }
//        }
//
//        if (session == null || session.getAttribute("userId") == null) {
//            if (uri.startsWith("/api/v1/payment/**")) { // 보호된 API 경로
//                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                httpResponse.getWriter().write("Unauthorized: Please log in.");
//                log.info("uri" + uri);
//                log.info("Unauthorized access. URI: {}, Method: {}, Client IP: {}, StartTime: {}", uri, method, clientIp, startTime);
//                return;
//            }
//        }
//
//        if (session == null || session.getAttribute("userId") == null) {
//            if (uri.startsWith("/api/v1/orders/**")) { // 보호된 API 경로
//                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                httpResponse.getWriter().write("Unauthorized: Please log in.");
//                log.info("uri" + uri);
//                log.info("Unauthorized access. URI: {}, Method: {}, Client IP: {}, StartTime: {}", uri, method, clientIp, startTime);
//                return;
//            }
//        }
//
//        if (session == null || session.getAttribute("userId") == null) {
//            if (uri.startsWith("/api/v1/coupons/**")) { // 보호된 API 경로
//                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                httpResponse.getWriter().write("Unauthorized: Please log in.");
//                log.info("uri" + uri);
//                log.info("Unauthorized access. URI: {}, Method: {}, Client IP: {}, StartTime: {}", uri, method, clientIp, startTime);
//                return;
//            }
//        }
//
//        // 다음 필터 또는 컨트롤러로 요청 전달
//        chain.doFilter(request, response);
//    }

}
