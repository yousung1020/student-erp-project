package service.member;

import common.PasswordUtil;
import dao.member.MemberDAO;
import dto.member.MemberInfoDTO;
import dto.member.MemberLoginDTO;
import dto.member.MemberSignUpDTO;
import dto.member.MemberUpdateDTO;

public class MemberService {
    private final MemberDAO memberDAO = new MemberDAO();

    //회원탈퇴 로직
    public boolean memberDelete(String memberId) {
        return memberDAO.memberDelete(memberId);
    }

    //회원가입 성공여부 반환
    public boolean signup(MemberSignUpDTO memberDto) {
        // ID 중복 체크 로직
        if (memberDAO.isIdExists(memberDto.getMemberId())) {
            System.err.println("[Service] 회원가입 실패: 아이디 '" + memberDto.getMemberId() + "'가 이미 존재합니다.");
            return false;
        }

        String password = memberDto.getMemberPassword();

        // 비밀번호 해싱
        String hashedPassword = PasswordUtil.hashPassword(password);

        // 해싱된 비밀번호를 DTO에 다시 설정
        memberDto.setMemberPassword(hashedPassword);

        return memberDAO.signup(memberDto);
    }

    //ID 중복 체크 로직
    public boolean isIdExists(String memberId) {
        return memberDAO.isIdExists(memberId);
    }

    // 로그인을 위한 비즈니스 로직
    public boolean login(MemberLoginDTO mdto) {
        // db에서 해싱된 패스워드
        String hashedPassword = memberDAO.getPasswordById(mdto.getMemberId());

        // 해당 id의 회원이 존재하지 않으면(비밀번호 자체가 존재하지 않으면) 로그인 실패
        if (hashedPassword == null) {
            return false;
        }

        // 사용자가 입력한 평문의 비밀번호
        String password = mdto.getMemberPassword();

        // 두 비밀번호가 일치하는지 확인하고, 결과를 반환
        return PasswordUtil.checkPassword(password, hashedPassword);
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

        // 사용자가 입력한 평문의 새 비밀번호를 가져옴
        String password = memberUpdateDTO.getMemberPassword();
        // 비밀번호가 비어있는지 확인 (비밀번호를 비운 상태에서 폼을 제출하면 비밀번호가 유지되게끔)
        if (password == null || password.trim().isEmpty()) {
            memberUpdateDTO.setMemberPassword(null);
        } else {
            // 새 비밀번호를 해싱한 후 다시 dto에 설정
            String hashedPassword = PasswordUtil.hashPassword(password);
            memberUpdateDTO.setMemberPassword(hashedPassword);
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