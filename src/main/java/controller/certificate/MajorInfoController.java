package controller.certificate;

import dto.certificate.MajorInfoDTO;
import dto.member.MemberInfoDTO;
import service.certificate.MajorInfoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/major/info")
public class MajorInfoController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private final MajorInfoService majorInfoService = new MajorInfoService();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
		MemberInfoDTO loginMember = (MemberInfoDTO) request.getAttribute("loginMember");
		if (loginMember != null) {

        String majorSeq = String.valueOf(loginMember.getDeptId());
		try {
			MajorInfoDTO relatedInfo = majorInfoService.getMajorRelatedInfo(majorSeq);
			request.setAttribute("majorInfo", relatedInfo);
		} catch (IllegalArgumentException e) {
            request.setAttribute("infoError", "조회를 위한 학과 정보가 부족합니다.");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("infoError", "학과 관련 정보 로딩 중 시스템 오류 발생.");
		}
		}
	}
}