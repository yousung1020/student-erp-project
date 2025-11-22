package service;

import dto.member.MemberSignUpDTO;

import dao.MemberDAO;

public class MemberService {

	private MemberDAO memberDAO;
    
    public MemberService() {
        this.memberDAO = new MemberDAO();
    }
    
    public boolean isIdExists(String memberId) {
        return memberDAO.isIdExists(memberId);
    }

    public boolean signup(MemberSignUpDTO memberDto) { 
        
        // 1. ID(memberId) 중복 체크 로직
        if (memberDAO.isIdExists(memberDto.getMemberId())) { 
            System.err.println("[Service] 회원가입 실패: 아이디 '" + memberDto.getMemberId() + "'가 이미 존재합니다.");
            return false; // 중복이면 가입 실패
        }

        return memberDAO.signup(memberDto);
    }
}
