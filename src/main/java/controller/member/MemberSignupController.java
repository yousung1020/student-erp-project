package controller.member;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.department.DepartmentService;
import service.member.MemberService;

import java.io.IOException;
import java.util.List;
import dto.department.DepartmentDTO;
import dto.member.MemberSignUpDTO;

@WebServlet("/member/signup")
public class MemberSignupController extends HttpServlet {

	private final DepartmentService departmentService = new DepartmentService();
	
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		try {
			// 학과목록 전부 가져오기
            List<DepartmentDTO> departments = departmentService.deptFindAll();
            request.setAttribute("departments", departments);
            request.getRequestDispatcher("/WEB-INF/views/member/signup.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "오류가 발생했습니다: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/admin/error.jsp").forward(request, response);
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        
        String memberId = request.getParameter("memberId");
        String memberPassword = request.getParameter("memberPassword");
        String passwordConfirm = request.getParameter("passwordConfirm");
        String memberName = request.getParameter("memberName");
        
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
            try {
                List<DepartmentDTO> departments = departmentService.deptFindAll();
                request.setAttribute("departments", departments);
            } catch (Exception e) {
                e.printStackTrace();
            }
            request.getRequestDispatcher("/WEB-INF/views/member/signup.jsp").forward(request, response);
            return;
        }
        
        if (memberPassword == null || !memberPassword.equals(passwordConfirm)) {
            request.setAttribute("errorType", "PASSWORD_MISMATCH");
            request.setAttribute("errorMessage", "비밀번호가 일치하지 않습니다. 다시 확인해주세요.");
            
            try {
                List<DepartmentDTO> departments = departmentService.deptFindAll();
                request.setAttribute("departments", departments);
            } catch (Exception e) {
                e.printStackTrace();
            }

            request.getRequestDispatcher("/WEB-INF/views/member/signup.jsp").forward(request, response);
            
            return;
        }
        
        int departmentId = 0;
        String departmentParam = request.getParameter("deptId");
        
        try {
            if (departmentParam != null && !departmentParam.isEmpty()) {
                departmentId = Integer.parseInt(departmentParam);
            }
        } catch (NumberFormatException e) {
            System.err.println("[Controller Error] 학과 ID 파라미터가 유효한 숫자가 아닙니다: " + departmentParam);
            request.setAttribute("errorMessage", "유효하지 않은 학과 정보가 제출되었습니다. 목록에서 다시 선택해주세요.");
            request.getRequestDispatcher("/WEB-INF/views/member/signup.jsp").forward(request, response);
            return;
        }
        
        MemberSignUpDTO signUpDTO = new MemberSignUpDTO(
        		memberId, 
                memberPassword, 
                memberName, 
                emailLocal, 
                departmentId
            );
  		
  		MemberService memberService = new MemberService(); 
  		
  		boolean isSuccess = memberService.signup(signUpDTO);
        
        if (isSuccess) {
            request.getSession().setAttribute("message", "회원가입에 성공했습니다! 이제 로그인해 주세요.");
            response.sendRedirect(request.getContextPath() + "/member/login");
        } else {
            if (memberService.isIdExists(memberId)) { 
                request.setAttribute("errorType", "ID_DUPLICATION");
                request.setAttribute("errorMessage", "입력하신 학번은 이미 사용 중입니다.");
            } else {
                request.setAttribute("errorType", "DB_ERROR");
                request.setAttribute("errorMessage", "회원가입 처리 중 시스템 오류가 발생했습니다.");
            }
            
            try {
                List<DepartmentDTO> departments = departmentService.deptFindAll();
                request.setAttribute("departments", departments);
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            request.getRequestDispatcher("/WEB-INF/views/member/signup.jsp").forward(request, response);
        }
    }
}