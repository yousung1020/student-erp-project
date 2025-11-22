package dto.member;

public class MemberSignUpDTO {
	private String memberId;
	private String memberPassword;
	private String memberName;
	private String memberEmail;
	private int memberDept;
	
	public MemberSignUpDTO() {}
	
	public MemberSignUpDTO(String memberId, String memberPassword, String memberName, String memberEmail, int memberDept) {
        this.memberId = memberId;
        this.memberPassword = memberPassword;
        this.memberName = memberName;
        this.memberEmail = memberEmail;
        this.memberDept = memberDept;
    }
	
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getMemberPassword() {
		return memberPassword;
	}
	public void setMemberPassword(String memberPassword) {
		this.memberPassword = memberPassword;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getMemberEmail() {
		return memberEmail;
	}
	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}
	public int getMemberDept() {
		return memberDept;
	}
	public void setMemberDept(int memberDept) {
		this.memberDept = memberDept;
	}
	
	
}
