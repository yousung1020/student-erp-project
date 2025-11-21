package common;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import java.io.IOException;

// 모든 요청과 응답에 대해 UTF-8 인코딩을 강제하는 필터
@WebFilter("/*")
public class EncodingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        // 모든 요청에 대해 UTF-8 인코딩 설정
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        // 다음 필터 또는 서블릿으로 요청과 응답 객체를 전달
        // 이 코드를 기준으로 요청 전/후에 공통 로직을 추가 가능
        chain.doFilter(request, response);
    }

    // Filter 인터페이스의 다른 메소드들은 기본 구현을 그대로 두기
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
