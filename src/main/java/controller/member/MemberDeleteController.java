package controller.member;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.member.MemberService;

import java.io.IOException;

@WebServlet("/member/delete")
public class MemberDeleteController extends HttpServlet {
	private final MemberService memberService = new MemberService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String memberId = (String)session.getAttribute("loggedMemberId");
		
		memberService.memberDelete(memberId);
		session.invalidate();
		
		response.sendRedirect(request.getContextPath() + "/");
	}
	
	

}
