package dto.certificate;

import java.util.Date;

public class MemberCertificateDTO {
	private Integer memberCertId;
	private String memberId;
	private Integer certId;
	private String certName;
	private String certSummary;
	private Date certDate;
	public Integer getMemberCertId() {
		return memberCertId;
	}
	public void setMemberCertId(Integer memberCertId) {
		this.memberCertId = memberCertId;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public Integer getCertId() {
		return certId;
	}
	public void setCertId(Integer certId) {
		this.certId = certId;
	}
	public Date getCertDate() {
		return certDate;
	}
	public void setCertDate(Date certDate) {
		this.certDate = certDate;
	}
	public String getCertName() {
		return certName;
	}
	public void setCertName(String certName) {
		this.certName = certName;
	}
	public String getCertSummary() {
		return certSummary;
	}
	public void setCertSummary(String certSummary) {
		this.certSummary = certSummary;
	}
}
