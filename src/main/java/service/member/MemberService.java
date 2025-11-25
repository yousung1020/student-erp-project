package service.member;

import dao.member.MemberDAO;
import dto.member.MemberInfoDTO;
import dto.member.MemberLoginDTO;
import dto.member.MemberSignUpDTO;
import dto.member.MemberUpdateDTO;

public class MemberService {
    private final MemberDAO memberDAO = new MemberDAO();
    
    
    //회원가입 성공여부 반환
    public boolean signup(MemberSignUpDTO memberDto) {     
        // ID 중복 체크 로직
        if (memberDAO.isIdExists(memberDto.getMemberId())) { 
            System.err.println("[Service] 회원가입 실패: 아이디 '" + memberDto.getMemberId() + "'가 이미 존재합니다.");
            return false;
        }

        return memberDAO.signup(memberDto);
    }

    //ID 중복 체크 로직
    public boolean isIdExists(String memberId) {
        return memberDAO.isIdExists(memberId);
    }

    // 로그인을 위한 비즈니스 로직
    public boolean login(MemberLoginDTO mdto) {
        // 추후에 비밀번호를 해싱하여 db의 해시값과 비교하는 로직 추가 예정(보안)
        // 지금은 dao의 로직을 그대로 위임
        return memberDAO.memberLogin(mdto);
    }

    // 회원 정보를 가져오는 비즈니스 로직
    public MemberInfoDTO getMemberInfo(String memberId) {
        return memberDAO.findMemberById(memberId);
    }

    // 회원 정보를 수정하는 비즈니스 로직
    public boolean updateMember(MemberUpdateDTO memberUpdateDTO) {
        // 이메일이 비어있는지 확인
        if (memberUpdateDTO.getMemberEmail() == null || memberUpdateDTO.getMemberEmail().trim().isEmpty()) {
            return false;
        }

        // 비밀번호가 비어있는지 확인 (비밀번호를 비운 상태에서 폼을 제출하면 비밀번호가 유지되게끔)
        if(memberUpdateDTO.getMemberPassword() == null || memberUpdateDTO.getMemberPassword().trim().isEmpty()) {
            memberUpdateDTO.setMemberPassword(null);
        }

        try {
            int rows = memberDAO.memberUpdate(memberUpdateDTO);
            // 사용자가 값을 변경하지 않아 업데이트된 행이 0개인 경우에도 성공으로 처리
            return rows >= 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}