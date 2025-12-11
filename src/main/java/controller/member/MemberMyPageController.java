package controller.member;

import dto.department.DepartmentDTO;
import dto.member.MemberInfoDTO;
import dto.member.MemberUpdateDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import service.department.DepartmentService;
import service.member.MemberService;

@WebServlet("/member/my-page")
public class MemberMyPageController extends HttpServlet {
    private final MemberService memberService = new MemberService();
    private final DepartmentService departmentService = new DepartmentService();

    // 마이페이지에 접속할 때
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        // 세션이 없으면 새로 생성하지 않음
        HttpSession session = request.getSession(false);

        // 로그인이 되어있지 않다면
        if (session == null || session.getAttribute("loggedMemberId") == null) {
            // 로그인 페이지로 redirect
            response.sendRedirect(request.getContextPath() + "/member/login");
            // redirect 후에는 메서드 실행 종료를 해야 함
            return;
        }

        String loggedMemberId = (String) session.getAttribute("loggedMemberId");

        try {
            // 회원 정보 조회
            MemberInfoDTO memberInfo = memberService.getMemberInfo(loggedMemberId);
            // 전체 학과 목록 조회
            List<DepartmentDTO> departments = departmentService.deptFindAll();

            // 조회된 회원, 학과 정보를 jsp 페이지로 포워드
            request.setAttribute("memberInfo", memberInfo);
            request.setAttribute("departments", departments);
            request.getRequestDispatcher("/WEB-INF/views/member/my_page.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "오류가 발생했습니다: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/admin/error.jsp").forward(request, response);
        }
    }

    // 회원 정보가 수정될 때
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("loggedMemberId") == null) {
            response.sendRedirect(request.getContextPath() + "/member/login");
            return;
        }

        String loggedMemberId = (String) session.getAttribute("loggedMemberId");

        MemberUpdateDTO memberUpdateDTO = new MemberUpdateDTO();

        memberUpdateDTO.setMemberId(loggedMemberId);
        memberUpdateDTO.setMemberPassword(request.getParameter("memberPassword"));
        memberUpdateDTO.setDeptId(Integer.parseInt(request.getParameter("deptId")));
        String emailLocal = request.getParameter("emailLocal");
        String emailDomain = request.getParameter("emailDomain");
        String emailSelect = request.getParameter("emailSelect");
        if ("direct".equals(emailSelect)) {
        	emailLocal += "@" + emailDomain;
        } else
        	emailLocal += "@" + emailSelect;
        
        String emailRegex = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$";

        if (!emailLocal.matches(emailRegex)) {
        	request.setAttribute("errorType", "email_miss");
            request.setAttribute("errorMessage", "올바른 이메일 형식이 아닙니다.");
            String msg = "올바른 이메일 형식이 아닙니다.";
            String encodedMsg = URLEncoder.encode(msg, "UTF-8");
            response.sendRedirect(
            	    request.getContextPath()
            	    + "/member/my-page?status=fail&errorType=email_miss&errorMessage=" + encodedMsg
            	);
            return;
        }
        memberUpdateDTO.setMemberEmail(emailLocal);

        boolean isSuccess = memberService.updateMember(memberUpdateDTO);

        if (isSuccess) {
            response.sendRedirect(request.getContextPath() + "/member/my-page?status=success");
        } else {
            request.setAttribute("error", "이메일은 비워둘 수 없습니다.");
            // 메세지를 utf-8 방식으로 url 인코딩
            String encodedMsg = URLEncoder.encode("회원 정보 업데이트에 실패했습니다.", "UTF-8");
            response.sendRedirect(request.getContextPath() + "/member/my-page?status=error&errorMsg=" + encodedMsg);
        }
    }
}
