package controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import dto.admin.AdminCertificateDTO;
import service.admin.AdminCertificateService;

@WebServlet("/api/admin/cert-manage")
public class AdminCertificateController extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private final AdminCertificateService certService = new AdminCertificateService();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleListRequest(request, response);
	}
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {   
        request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");		
		if ("create".equals(action)) {
            handleCreateRequest(request, response);
        } else if ("update".equals(action)) {
            handleUpdateRequest(request, response);
        } else if ("delete".equals(action)) {
            handleDeleteRequest(request, response);
        }
	}
    
	private void handleListRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<AdminCertificateDTO> certList = certService.getAllCertificates();
            request.setAttribute("certList", certList);
            
            request.getRequestDispatcher("/WEB-INF/views/admin/cert_management.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "자격증 목록 조회 중 서버 오류 발생: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/admin/error.jsp").forward(request, response);
        }
    }
    
	private void handleCreateRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AdminCertificateDTO newCert = createCertFromRequest(request);
        
        try {
            boolean success = certService.addCertificate(newCert); 
            
            if (success) {
                response.sendRedirect("cert-manage?action=list&message=certCreateSuccess");
            } else {
                response.sendRedirect("cert-manage?action=list&error=certCreateFailed");
            }
        } catch (Exception e) { 
             response.sendRedirect("cert-manage?action=list&error=certCreateError");
        }
    }
    
    private void handleUpdateRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AdminCertificateDTO updatedCert = createCertFromRequest(request);
        int certId = updatedCert.getCertId();
        try {
            boolean success = certService.updateCertificate(updatedCert);
            
            if (success) {
                response.sendRedirect("cert-manage?action=list&message=updateSuccess&certId=" + certId);
            } else {
                response.sendRedirect("cert-manage?action=list&error=updateFailed&certId="+certId);
            }
        } catch (Exception e) {
            response.sendRedirect("cert-manage?action=list&error=updateError&certId="+certId);
        }
    }
    
	private void handleDeleteRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int certId = Integer.parseInt(request.getParameter("certId"));
            boolean success = certService.deleteCertificate(certId);  
            if (success) {
                response.sendRedirect("cert-manage?action=list&message=deleteSuccess");
            } else {
                response.sendRedirect("cert-manage?action=list&error=deleteFailed");
            }
        } catch (NumberFormatException e) {
             response.sendRedirect("cert-manage?action=list&error=deleteInvalidId");
        } catch (Exception e) {
             response.sendRedirect("cert-manage?action=list&error=deleteError");
        }
    }
    
    private AdminCertificateDTO createCertFromRequest(HttpServletRequest request) {
        AdminCertificateDTO cert = new AdminCertificateDTO();
        String certIdStr = request.getParameter("certId");
        if (certIdStr != null && !certIdStr.isEmpty()) {
            cert.setCertId(Integer.parseInt(certIdStr)); 
        }   
        cert.setCertName(request.getParameter("certName"));
        cert.setCertJob(request.getParameter("certJob"));
        cert.setCertSummary(request.getParameter("certSummary"));
        cert.setCertTrend(request.getParameter("certTrend"));
        return cert;
    }
}