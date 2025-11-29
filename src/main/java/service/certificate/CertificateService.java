package service.certificate;

import java.util.List;

import dao.certificate.CertificateDAO;
import dto.certificate.CertificateDTO;
import dto.certificate.MemberCertificateDTO;

public class CertificateService {
	private final CertificateDAO certificateDAO = new CertificateDAO();
	
	public List<CertificateDTO> certFindAll(){
		
		List<CertificateDTO> certificates = certificateDAO.certFindAll();
		
		return certificates;
	}
	
	public List<MemberCertificateDTO> getCertificates(String memberId){
		return certificateDAO.MemberCertificates(memberId);
	}
}
