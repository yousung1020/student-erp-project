package dto.certificate;

public class CertificateDTO {
    private int certId;
    private String certName;
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

    public String getCertTrend() {
        return certTrend;
    }

    public void setCertTrend(String certTrend) {
        this.certTrend = certTrend;
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
