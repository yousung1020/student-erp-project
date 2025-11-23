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
import dto.admin.DeptDTO;
/**
 * Servlet implementation class AdminMemberController
 */
@WebServlet("/api/admin/user-manage")
public class AdminMemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AdminMemberService memberService = new AdminMemberService();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		if (action == null || action.isEmpty()) {
			action = "list";
		}
		if("list".equals(action)) {
			handleListRequest(request, response);
		}
		else if("detail".equals(action)) {
			handleDetailRequest(request, response);
		}
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		
		if("create".equals(action)) {
			handleCreateRequest(request, response);
		}
		else if("update".equals(action)) {
			String updateType = request.getParameter("updateType");
			if ("password".equals(updateType)) {
				handlePasswordUpdateRequest(request, response);
			}
			else if ("department".equals(updateType)) {
				handleDepartmentUpdateRequest(request, response);
			}
		}
		else if("delete".equals(action)) {
			handleDeleteRequest(request, response);
		}
	}

	private void handleListRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			List<AdminMemberSelectDTO> memberList = memberService.getMemberList();
			request.setAttribute("memberList", memberList);
			List<DeptDTO> deptList = memberService.getAllDepartments(); 
	        request.setAttribute("deptList", deptList);
	        
			request.getRequestDispatcher("/WEB-INF/views/admin/user_management.jsp").forward(request, response);
		} catch (Exception e) {
			request.setAttribute("error", "회원 목록 조회 중 서버 오류 발생 : " + e.getMessage());
			request.getRequestDispatcher("/WEB-INF/views/admin/error.jsp").forward(request, response);
		}
	}
	
	private void handleDetailRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String memberId = request.getParameter("memberId");
		
		try {
			AdminMemberDetailDTO memberDetail = memberService.getMemberDetail(memberId);
			
			request.setAttribute("memberDetail", memberDetail);
			List<DeptDTO> deptList = memberService.getAllDepartments(); 
	        request.setAttribute("departmentList", deptList);
			request.getRequestDispatcher("/WEB-INF/views/admin/user_detail.jsp").forward(request,response);
		} catch (MemberNotFoundException e) {
			request.setAttribute("error", e.getMessage());
			request.getRequestDispatcher("/WEB-INF/views/admin/error.jsp").forward(request, response);
		} catch (Exception e) {
			request.setAttribute("error", "회원 상세 정보 조회 중 오류 발생: " + e.getMessage());
			request.getRequestDispatcher("/WEB-INF/views/admin/error.jsp").forward(request, response);
		}
	}
	
	private void handleCreateRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AdminMemberCreateDTO createDTO = new AdminMemberCreateDTO();
		createDTO.setMemberId(request.getParameter("memberId"));
		createDTO.setMemberPassword(request.getParameter("memberPassword"));
		createDTO.setMemberName(request.getParameter("memberName"));
		createDTO.setMemberEmail(request.getParameter("memberEmail"));
		createDTO.setDeptId(Integer.parseInt(request.getParameter("deptId")));
		
		try {
			int success = memberService.registerMember(createDTO);
			
			if (success == 1) {
				response.sendRedirect("user-manage?action=list&message=success");
			}
			else if (success == 0) {
				request.setAttribute("error", "회원 등록 실패: 입력하신 ID는 이미 사용 중입니다.");
	            List<AdminMemberSelectDTO> memberList = memberService.getMemberList();
	            request.setAttribute("memberList", memberList);
	            List<DeptDTO> deptList = memberService.getAllDepartments(); 
	            request.setAttribute("deptList", deptList);
	            request.getRequestDispatcher("/WEB-INF/views/admin/user_management.jsp").forward(request, response);
			}
			else {
                request.setAttribute("error", "회원 가입 처리에 실패했습니다. (DB 반영 실패)");
                request.getRequestDispatcher("/WEB-INF/views/admin/error.jsp").forward(request, response);
            }
		} catch (Exception e) { 
            request.setAttribute("error", "회원 등록 중 오류 발생: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/admin/error.jsp").forward(request, response);
        }
	}
	
	private void handlePasswordUpdateRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AdminMemberUpdateDTO updateDto = new AdminMemberUpdateDTO();
        updateDto.setMemberId(request.getParameter("memberId"));
        updateDto.setNewPassword(request.getParameter("newPassword"));
        
        try {
        	boolean success = memberService.updatePassword(updateDto);
        	if (success) {
        		response.sendRedirect("user-manage?action=detail&memberId=" + updateDto.getMemberId() + "&message=passwordUpdateSuccess");
        	}
        	else {
        		response.sendRedirect("user-manage?action=detail&memberId=" + updateDto.getMemberId() + "&error=passwordUpdateFailed");
        	}
        } catch (MemberNotFoundException e) {
        	response.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        } catch (Exception e) {
        	response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "비밀번호 수정 중 서버 오류 발생");
        }
	}
	private void handleDepartmentUpdateRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AdminMemberUpdateDTO updateDto = new AdminMemberUpdateDTO();
        updateDto.setMemberId(request.getParameter("memberId"));
        updateDto.setDepartmentId(Integer.parseInt(request.getParameter("newDeptId")));
        
        try {
        	boolean success = memberService.updateDepartmentId(updateDto);
        	if (success) {
        		response.sendRedirect("user-manage?action=detail&memberId=" + updateDto.getMemberId() + "&message=departmentUpdateSuccess");
        	}
        	else {
        		response.sendRedirect("user-manage?action=detail&memberId=" + updateDto.getMemberId() + "&error=departmentUpdateFailed");
        	}
        } catch (MemberNotFoundException e) {
        	response.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        } catch (Exception e) {
        	response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "학과 ID 수정 중 서버 오류 발생");
        }
	}
	private void handleDeleteRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AdminMemberDeleteDTO deleteDto = new AdminMemberDeleteDTO();
        deleteDto.setMemberId(request.getParameter("memberId"));
        
        try {
            boolean success = memberService.removeMember(deleteDto);
            if (success) {
                response.sendRedirect("user-manage?action=list&message=deleteSuccess");
            } else {
                response.sendRedirect("user-manage?action=list&error=deleteFailed");
            }
        } catch (MemberNotFoundException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            request.setAttribute("error", "회원 삭제 중 오류 발생: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/admin/error.jsp").forward(request, response);
        }
	}
}