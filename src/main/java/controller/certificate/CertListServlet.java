package controller.certificate;

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
public class CertListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final CertificateService certificateService = new CertificateService();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MemberInfoDTO loginMember = (MemberInfoDTO) request.getAttribute("loginMember");
		if (loginMember != null) {
			String memberId = loginMember.getMemberId();
			try {
				List<MemberCertificateDTO> myCertList = certificateService.getCertificates(memberId);
				request.setAttribute("myCertList", myCertList);
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("certError",  "자격증 로딩 중 오류 발생");
			}
		}
	}
}
