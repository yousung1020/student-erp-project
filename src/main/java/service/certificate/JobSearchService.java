package service.certificate;

import dto.certificate.JobPostingDTO;
import dto.certificate.JobPostingResDTO;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JobSearchService {
    public static void main(String[] args) throws IOException {
        JobSearchService jobPostingService = new JobSearchService();
        JobPostingResDTO a = jobPostingService.searchJobPostings("정보처리기사", 1);
    }

    public JobPostingResDTO searchJobPostings(String keyword, int page) throws IOException {
        String encodedKeyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8);

        StringBuilder urlBuilder = new StringBuilder("https://www.jobkorea.co.kr/Search?stext=");
        urlBuilder.append(encodedKeyword);
        urlBuilder.append("&Page_No=").append(page);

        String url = urlBuilder.toString();

        List<JobPostingDTO> jobList = new ArrayList<>();

        // html 문서 가져오기
        Document doc = Jsoup.connect(url).get();

        // 페이지 전체가 아닌, 해당 태그의 data-sentry-component="JobList" 속성만 선택함
        Elements jobListContainer = doc.select("div[data-sentry-component=\"JobList\"]");

        if (jobListContainer == null) {
            System.out.println("채용 공고 목록 컨테이너가 업33");
            // 빈 dto 반환
            return new JobPostingResDTO();
        }

        // 이것도 마찬가지
        Elements postings = jobListContainer.select("div[data-sentry-component=\"CardJob\"]");

        for (Element post:  postings) {
            JobPostingDTO jobPostingDTO = new JobPostingDTO();

            // a 태그만을 위한 Elements(List)
            // 그니까 data-sentry-element="Block\ 해당 속성 안에 <a> 태그가 두 개 있는데, 첫 번째 a 태그의 요소 값에는 공고 제목이 있고, 두 번째에는 해당 회사(또는 가게) 이름이 있삼!!!
            // 그래서 Elements(List) 형태로 select를 한 후 특정 인덱스의 값을 추출하는 방식
            Elements aTag = post.select("div[data-sentry-element=\"Block\"]").select("a");

            // 공고 제목
            String title = aTag.get(0).text();

            // 회사 이름
            String companyName = aTag.get(1).text();

            // 세부 내용을 확인할 수 있는 url (잡코리아로 접속됨)
            String detailUrl = aTag.get(0).attr("href");

            // Flex 속성의 세부 정보만을 위한 Element, 2번 인덱스(3번 째)에 해당 Flex 속성 안에 얻어내야 할 정보들이 있음(위치, 직업 분야).
            Element flexAttr = post.select("div[data-sentry-element=\"Block\"]").select("div[data-sentry-element=\"Flex\"]").get(2);

            String location = flexAttr.select("div[data-sentry-component=\"GrayChip\"]").get(0).text();
            String field = flexAttr.select("div[data-sentry-component=\"GrayChip\"]").get(1).text();

            jobPostingDTO.setTitle(title);
            jobPostingDTO.setCompanyName(companyName);
            jobPostingDTO.setUrl(detailUrl);
            jobPostingDTO.setLocation(location);
            jobPostingDTO.setField(field);

            jobList.add(jobPostingDTO);
        }

        // 해당 자격증으로 공고를 검색했을시 총 몇 건의 공고 글이 있는지 여부
        int totalJobPostings = Integer.parseInt(
                doc.select("div[data-sentry-component=\"JobList\"]").select("span[data-sentry-element=\"Typography\"]").get(1).text().replace(",", "")
        );

        // 한 페이지에 20건씩 공고글이 있기 때문에 20개를 기준으로 페이징 할 예정 (ceil은 소수점 올림으로, 예를 들어 21건에 데이터가 있다고 하면 2페이지이므로, 21/20 한 결과에서 소수점 부분을 올려줘야 함)
        int totalPages = (int) Math.ceil((double) totalJobPostings / 20);

        // 페이징 정보가 포함된 응답 dto 설정
        JobPostingResDTO jobPostingResDTO = new JobPostingResDTO();
        jobPostingResDTO.setJobList(jobList);
        jobPostingResDTO.setTotalJobPostings(totalJobPostings);
        jobPostingResDTO.setTotalPages(totalPages);
        jobPostingResDTO.setCurrentPage(page);

        System.out.println("해당 페이지의 공고 글 수: " + jobPostingResDTO.getJobList().size());
        System.out.println("총 공고 글 수: " + jobPostingResDTO.getTotalJobPostings());
        System.out.println("현재 페이지: " + jobPostingResDTO.getCurrentPage());
        System.out.println("총 페이지: " + jobPostingResDTO.getTotalPages());
        System.out.println("-----------------------------------");
        return jobPostingResDTO;
    }
}
