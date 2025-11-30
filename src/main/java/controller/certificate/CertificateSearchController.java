package controller.certificate;

import com.google.gson.Gson;
import dto.certificate.CertificateDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import service.certificate.CertificateService;

@WebServlet("/api/certificates/search")
public class CertificateSearchController extends HttpServlet {
    private final CertificateService certificateService = new CertificateService();
    // Gson: json 구조에 대해 직렬화, 역직렬화 해주는 자바의 라이브러리
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String keyword = request.getParameter("certKeyword");

        List<CertificateDTO> certificates = certificateService.searchCertsByName(keyword);

        String jsonResponse = gson.toJson(certificates);

        // 클라이언트한테 json 데이터로 응답
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        // getWriter(): 응답 본문(body)에 데이터를 쓰기 위한 PrintWriter 객체를 반환
        // write(): 문자열의 데이터를 응답 본문에 작성 (줄바꿈X)
        // 즉, 텍스트 기반의 응답을 생성할 때 사용되는 방법
        response.getWriter().write(jsonResponse);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
