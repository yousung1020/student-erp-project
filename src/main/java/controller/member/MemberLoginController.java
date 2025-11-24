package controller.member;

import dto.member.MemberInfoDTO;
import dto.member.MemberLoginDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import service.member.MemberService;

@WebServlet("/member/login")
public class MemberLoginController extends HttpServlet {
    private final MemberService memberService = new MemberService();

    // 로그인 폼(login.jsp)를 보여주는 메서드
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/member/login.jsp").forward(request, response);
    }

    // 로그인 폼에서 전송된 데이터를 처리하는 메서드
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String memberId = request.getParameter("memberId");
        String memberPassword = request.getParameter("memberPassword");

        MemberLoginDTO memberLoginDTO = new MemberLoginDTO();
        memberLoginDTO.setMemberId(memberId);
        memberLoginDTO.setMemberPassword(memberPassword);

        boolean isLoginSuccess = memberService.login(memberLoginDTO);

        if (isLoginSuccess) {
            // getContextPath(): 프로젝트 path (student-erp-project/)
            MemberInfoDTO memberInfo = memberService.getMemberInfo(memberId);

            // 해당 계정이 관리자 계정일 경우
            if(memberInfo.getIsAdmin()){
                response.sendRedirect(request.getContextPath() + "/api/admin/user-manage");
                return;
            }

            HttpSession session = request.getSession();
            session.setAttribute("loggedMemberId", memberLoginDTO.getMemberId());
            response.sendRedirect(request.getContextPath() + "/home");
        }
        else{
            // 로그인 실패 시 에러 메세지를 request 객체에 담아 다시 로그인 페이지로 포워드
            request.setAttribute("loginError", "아이디 또는 비밀번호가 일치하지 않습니다.");
            // doGet을 다시 호출해서 로그인 폼 페이지를 보여주기
            doGet(request, response);
        }
    }
}
