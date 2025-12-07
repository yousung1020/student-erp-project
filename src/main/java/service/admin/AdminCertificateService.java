package service.admin;

import dto.admin.AdminCertificateDTO;

import java.util.List;

import dao.admin.AdminCertificateDAO;

public class AdminCertificateService {
	private AdminCertificateDAO dao = new AdminCertificateDAO();
	
	public List<AdminCertificateDTO> getAllCertificates(){
		return dao.selectAllCertificates();
	}
	
	public boolean addCertificate(AdminCertificateDTO dto) {
		return dao.inserCertificate(dto) > 0 ;
	}
	
	public boolean updateCertificate(AdminCertificateDTO dto) {
		return dao.updateCertificate(dto) > 0;
	}
	
	public boolean deleteCertificate(int certId) {
		return dao.deleteCertificate(certId) > 0;
	}
}
