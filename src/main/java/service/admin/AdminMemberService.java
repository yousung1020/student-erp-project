package service.admin;

import common.PasswordUtil;
import dao.admin.AdminDeptDAO;
import dao.admin.AdminMemberDAO;
import dto.admin.AdminMemberDeleteDTO;
import dto.admin.AdminMemberDetailDTO;
import dto.admin.AdminMemberSelectDTO;
import dto.admin.AdminMemberUpdateDTO;
import dto.admin.AdminMemberCreateDTO;
import dto.admin.DeptDTO;
import service.admin.exception.MemberNotFoundException;

import java.util.List;

public class AdminMemberService {
	private AdminMemberDAO adminMemberDAO = new AdminMemberDAO();
	private AdminDeptDAO adminDeptDAO = new AdminDeptDAO();
	
	public int registerMember(AdminMemberCreateDTO dto) {
		String memberId = dto.getMemberId();
		if (adminMemberDAO.checkMemberIdExists(memberId)) {
			return 0;
		}

		String hashedPassword = PasswordUtil.hashPassword(dto.getMemberPassword());
        dto.setMemberPassword(hashedPassword);

		try {
			int result = adminMemberDAO.insertMember(dto);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public List<AdminMemberSelectDTO> getMemberList(){
		return adminMemberDAO.selectMemberList();
	}
	
	public AdminMemberDetailDTO getMemberDetail(String memberId) {
		AdminMemberDetailDTO memberDetail = adminMemberDAO.selectMemberDetail(memberId);
		
		if (memberDetail == null) {
			throw new MemberNotFoundException(memberId + "해당 ID의 회원을 찾을 수 없습니다.");
		}
		
		return memberDetail;
	}
	
	public boolean updatePassword(AdminMemberUpdateDTO dto) {
        String hashedPassword = PasswordUtil.hashPassword(dto.getNewPassword());
        dto.setNewPassword(hashedPassword);

		int result = adminMemberDAO.updatePassword(dto);
		
		return result > 0;
	}
	
	public boolean updateDepartmentId(AdminMemberUpdateDTO dto) {
		int result = adminMemberDAO.updateDepartment(dto);
		
		return result > 0;
	}
	
	public boolean removeMember(AdminMemberDeleteDTO dto) {
		String targetId = dto.getMemberId();
		AdminMemberDetailDTO targetMember = adminMemberDAO.selectMemberDetail(targetId);
		if (targetMember == null) {
            throw new MemberNotFoundException(targetId + " ID를 가진 회원을 찾을 수 없습니다. (삭제 실패)");
        }
		
		int result = adminMemberDAO.deleteMember(dto);
		
		return result > 0;
		
	}
	
	public List<DeptDTO> getAllDepartments() {
        // 부서 DAO를 호출하여 목록을 반환합니다.
        return adminDeptDAO.selectAllDepartments();
    }
}
