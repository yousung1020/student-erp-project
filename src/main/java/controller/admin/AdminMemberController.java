package controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import service.admin.AdminMemberService;
import service.admin.exception.MemberNotFoundException;
import java.util.List;

import dto.admin.AdminMemberDeleteDTO;
import dto.admin.AdminMemberUpdateDTO;
import dto.admin.AdminMemberDetailDTO;
import dto.admin.AdminMemberSelectDTO;
import dto.admin.AdminMemberCreateDTO;

/**
 * Servlet implementation class AdminMemberController
 */
@WebServlet("/api/admin/user-manage")
public class AdminMemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AdminMemberService memberService = new AdminMemberService();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		
		if("list".equals(action)) {
			handleListRequest(request, response);
		}
		else if("detail".equals(action)) {
			handleDetailRequest(request, response);
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		
		if("create".equals(action)) {
			handleCreateRequest(request, response);
		}
		else if("update".equals(action)) {
			handleUpdateRequest(request, response);
		}
		if("delete".equals(action)) {
			handleDeleteRequest(request, response);
		}
	}

	private void handleListRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			List<AdminMemberSelectDTO> memberList = memberService.getMemberList();
			request.setAttribute("memberList", memberList);
			
			request.getRequestDispatcher("/views/admin/user_management.jsp");
		} catch (Exception e) {
			request.setAttribute("error", "회원 목록 조회 중 서버 오류 발생 : " + e.getMessage());
			request.getRequestDispatcher(")
		}
	}


}