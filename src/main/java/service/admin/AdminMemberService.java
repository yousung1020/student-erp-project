package service.admin;

import dao.admin.AdminMemberDAO;
import dto.admin.AdminMemberDeleteDTO;
import dto.admin.AdminMemberDetailDTO;
import dto.admin.AdminMemberSelectDTO;
import dto.admin.AdminMemberUpdateDTO;
import service.admin.exception.MemberNotFoundException;
import dto.admin.AdminMemberCreateDTO;
import java.util.List;

public class AdminMemberService {
	private AdminMemberDAO adminMemberDAO = new AdminMemberDAO();
	
	public boolean registerMember(AdminMemberCreateDTO dto) {
		int result = adminMemberDAO.insertMember(dto);
		
		return result > 0;
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
		int result = adminMemberDAO.updateMember(dto);
		
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
}
