package service.certificate;

import java.util.List;

import dao.certificate.CertificateDAO;
import dto.certificate.MemberCertificateDTO;

public class CertificateService {
	private final CertificateDAO certificateDAO = new CertificateDAO();
	
	public List<MemberCertificateDTO> getCertificates(String memberId){
		return certificateDAO.MemberCertificates(memberId);
	}
}
