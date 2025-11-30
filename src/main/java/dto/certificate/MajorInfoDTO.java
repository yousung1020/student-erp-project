package dto.certificate;

public class MajorInfoDTO {
    private String majorName;
    private String relatedJobs;
    private String qualifications;

    public String getMajorName() {
        return majorName;
    }
    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public String getRelatedJobs() {
        return relatedJobs;
    }
    public void setRelatedJobs(String relatedJobs) {
        this.relatedJobs = relatedJobs;
    }

    public String getQualifications() {
        return qualifications;
    }
    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
    }
}