package controller.certificate;

import dto.certificate.CertificateDTO;
import dto.certificate.MemberCertificateDTO;
import service.certificate.CertificateService;
import dto.member.MemberInfoDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/cert/list")
public class CertListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final CertificateService certificateService = new CertificateService();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			// 전체 자격증 목록은 로그인 여부와 상관없이 항상 가져오도록 if 블록 밖으로 이동
			List<CertificateDTO> certificates = certificateService.certFindAll();
			request.setAttribute("certificates", certificates);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("certError", "전체 자격증 목록 로딩 중 오류 발생");
		}

		MemberInfoDTO loginMember = (MemberInfoDTO) request.getAttribute("loginMember");
		if (loginMember != null) {
			String memberId = loginMember.getMemberId();
			try {
				// 사용자의 자격증 목록은 로그인 했을 때만 가져옴
				List<MemberCertificateDTO> myCertList = certificateService.getCertificates(memberId);
				request.setAttribute("myCertList", myCertList);
			} catch (Exception e) {
				e.printStackTrace();
				// certError 속성 이름을 구분하여 어떤 부분에서 에러가 났는지 명확하게 함
				request.setAttribute("myCertError",  "내 자격증 로딩 중 오류 발생");
			}
		}
	}
}
