package dto.certificate;

// 채용 공고 정보를 담기 위한 dto
public class JobPostingDTO {
    private String title;
    private String companyName;
    private String url;
    private String location;
    private String field;

    @Override
    public String toString() {
        return "JobPostingDTO{" +
                "title='" + title + '\'' +
                ", companyName='" + companyName + '\'' +
                ", url='" + url + '\'' +
                ", location='" + location + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
