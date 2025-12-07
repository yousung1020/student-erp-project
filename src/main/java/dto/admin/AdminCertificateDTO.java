package dto.admin;

public class AdminCertificateDTO {
    private int certId;
	private String certName;
    private String certJob;
    private String certSummary;
    private String certTrend;
    public int getCertId() {
		return certId;
	}
	public void setCertId(int certId) {
		this.certId = certId;
	}
	public String getCertName() {
		return certName;
	}
	public void setCertName(String certName) {
		this.certName = certName;
	}
	public String getCertJob() {
		return certJob;
	}
	public void setCertJob(String certJob) {
		this.certJob = certJob;
	}
	public String getCertSummary() {
		return certSummary;
	}
	public void setCertSummary(String certSummary) {
		this.certSummary = certSummary;
	}
	public String getCertTrend() {
		return certTrend;
	}
	public void setCertTrend(String certTrend) {
		this.certTrend = certTrend;
	}
}