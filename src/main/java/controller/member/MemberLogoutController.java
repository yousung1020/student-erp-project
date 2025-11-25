package controller.member;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/member/logout")
public class MemberLogoutController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        // 세션이 존재하는지 확인
        if (session != null){
            // 세션 자체를 무효화 함 (removeAttribute 는 특정 세션 객체 값만 삭제함)
            session.invalidate();
        }

        response.sendRedirect(request.getContextPath()+ "/home");
    }
}
