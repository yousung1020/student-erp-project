package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.MemberService;

import java.io.IOException;

import dto.member.MemberSignUpDTO;
import dao.MemberDAO;

@WebServlet(name = "MemberController", urlPatterns = "/member/signup")
public class MemberController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.getRequestDispatcher("/WEB-INF/views/member/signup.jsp").forward(request, response);
       
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. 인코딩 설정 (가장 먼저 수행, 한글 깨짐 방지)
        request.setCharacterEncoding("UTF-8");
        
        // 2. 폼 데이터(파라미터) 받기
        // *주의: 파라미터 이름(name="")이 signup.jsp의 폼 입력 필드와 일치해야 합니다.
        String studentId = request.getParameter("studentId");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String memberEmail = request.getParameter("memberEmail");
        String department = request.getParameter("department");
        
        int departmentId = 0;
        String departmentParam = request.getParameter("department");
        
        try {
            if (departmentParam != null && !departmentParam.isEmpty()) {
                departmentId = Integer.parseInt(departmentParam);
            }
        } catch (NumberFormatException e) {
            // 숫자로 변환할 수 없는 값이 넘어온 경우 (예: 사용자가 직접 문자를 입력하고 선택 안 한 경우)
            System.err.println("[Controller Error] 학과 ID 파라미터가 유효한 숫자가 아닙니다: " + departmentParam);
            request.setAttribute("errorMessage", "유효하지 않은 학과 정보가 제출되었습니다. 목록에서 다시 선택해주세요.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/member/signup.jsp");
            dispatcher.forward(request, response);
            return;
        }
        
        //3. JDBC
        MemberSignUpDTO signUpDTO = new MemberSignUpDTO(
                studentId, 
                password, 
                name, 
                memberEmail, 
                departmentId
            );
  		
  		MemberService memberService = new MemberService(); 
  		
  		boolean isSuccess = memberService.signup(signUpDTO);
        
        // 5. 결과에 따른 응답 처리
        if (isSuccess) {
            request.getSession().setAttribute("message", "회원가입에 성공했습니다! 이제 로그인해 주세요.");
            response.sendRedirect(request.getContextPath() + "/member/login");
        } else {
        	// ID 중복 오류 체크
            if (memberService.isIdExists(studentId)) { 
                request.setAttribute("errorType", "ID_DUPLICATION");
                request.setAttribute("errorMessage", "입력하신 학번은 이미 사용 중입니다.");
            } else {
                // 기타 오류 (DB 연결 실패, SQL 오류 등)
                request.setAttribute("errorType", "DB_ERROR");
                request.setAttribute("errorMessage", "회원가입 처리 중 시스템 오류가 발생했습니다.");
            }
            request.getRequestDispatcher("/WEB-INF/views/member/signup.jsp").forward(request, response);
        }
    }
}