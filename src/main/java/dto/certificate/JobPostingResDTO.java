package dto.certificate;

import java.util.List;

public class JobPostingResDTO {
    private List<JobPostingDTO> jobList;
    private int totalPages;
    private int currentPage;
    private int totalJobPostings;

    public List<JobPostingDTO> getJobList() {
        return jobList;
    }

    public void setJobList(List<JobPostingDTO> jobList) {
        this.jobList = jobList;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalJobPostings() {
        return totalJobPostings;
    }

    public void setTotalJobPostings(int totalJobPostings) {
        this.totalJobPostings = totalJobPostings;
    }
}
