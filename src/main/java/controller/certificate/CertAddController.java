package controller.certificate;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.certificate.CertificateService;

import java.io.IOException;

import dto.certificate.MemberCertificateDTO;

@WebServlet("/certificate/add")
public class CertAddController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String memberId = (String) request.getSession().getAttribute("loggedMemberId");
		
		int certificateId = 0;
        String certificateParam = request.getParameter("certId");
        
        try {
            if (certificateParam != null && !certificateParam.isEmpty()) {
            	certificateId = Integer.parseInt(certificateParam);
            }
        } catch (NumberFormatException e) {
            System.err.println("[Controller Error] 자격증 ID 파라미터가 유효한 숫자가 아닙니다: " + certificateParam);
            request.setAttribute("errorMessage", "유효하지 않은 자격증 정보가 제출되었습니다. 목록에서 다시 선택해주세요.");
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        MemberCertificateDTO memCertDTO = new MemberCertificateDTO();
        memCertDTO.setMemberId(memberId);
        memCertDTO.setCertId(certificateId);
        String date = request.getParameter("acquisitionDate");
        if(date != null && !date.isEmpty()) {
        	try {
        		java.sql.Date certDate = java.sql.Date.valueOf(date);
                memCertDTO.setCertDate(certDate);
        	} catch (IllegalArgumentException e) {
                System.err.println("[Controller Error] 올바르지 않은 날짜 형식: " + date);
                request.setAttribute("errorMessage", "날짜 형식이 올바르지 않습니다. yyyy-MM-dd 형식으로 입력해주세요.");
                response.sendRedirect(request.getContextPath() + "/home");
                return;
            }
        } else {
        	request.setAttribute("errorMessage", "취득일을 입력해주세요.");
        	response.sendRedirect(request.getContextPath() + "/home");
            return;
        }
        CertificateService certificateService = new CertificateService();
        boolean isSuccess = certificateService.addCert(memCertDTO);
        if (isSuccess) {
        	response.sendRedirect(request.getContextPath() + "/home");
        } else {
        	request.setAttribute("errorMessage", "자격증 추가 중 시스템 오류가 발생했습니다.");
        	response.sendRedirect(request.getContextPath() + "/home");
        }
	}

}
