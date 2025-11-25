package service.certificate;

import java.util.List;

import dao.certificate.CertificateDAO;
import dto.certificate.CertificateDTO;

public class CertificateService {
	private final CertificateDAO certificateDAO = new CertificateDAO();
	
	public List<CertificateDTO> getCertificates(String memberId){
		return certificateDAO.MemberCertificates(memberId);
	}
}
