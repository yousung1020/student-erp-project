package controller.certificate;

import com.google.gson.Gson;
import dto.certificate.JobPostingResDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import service.certificate.JobSearchService;

@WebServlet("/api/job/search")
public class JobSearchController extends HttpServlet {
    private final JobSearchService jobSearchService = new JobSearchService();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("jobSearchKeyword");
        String pageParam = request.getParameter("pageNo");
        int page = (pageParam != null && !pageParam.isEmpty()) ? Integer.parseInt(pageParam) : 1;
        
        JobPostingResDTO jobPostingResDTO = null;

        if (keyword != null && !keyword.trim().isEmpty()) {
            jobPostingResDTO = jobSearchService.searchJobPostings(keyword, page);
        }

        // 결과를 JSON으로 변환
        String jsonResponse = gson.toJson(jobPostingResDTO);

        // 클라이언트에게 JSON 데이터로 응답
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse);
    }
}
