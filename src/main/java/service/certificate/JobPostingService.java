package service.certificate;

import dto.certificate.JobPostingDTO;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JobPostingService {
    public List<JobPostingDTO> searchJobPostings(String keyword, int page) throws IOException {
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
            // 빈 리스트 반환
            return jobList;
        }

        // 이것도 마찬가지
        Elements postings = jobListContainer.select("div[data-sentry-component=\"CardJob\"]");
        System.out.println("해당 페이지에서의 공고 글 수: " + postings.size());

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

        for(JobPostingDTO jobPostingDTO:jobList){
            System.out.println(jobPostingDTO.getTitle());
            System.out.println(jobPostingDTO.getCompanyName());
            System.out.println(jobPostingDTO.getUrl());
            System.out.println(jobPostingDTO.getLocation());
            System.out.println(jobPostingDTO.getField());
        }

        System.out.println(jobList.size());
        return jobList;
    }
}
