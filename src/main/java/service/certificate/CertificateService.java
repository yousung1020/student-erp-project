package service.certificate;

import dto.certificate.CertificateDTO;
import java.util.ArrayList;
import java.util.List;

import dao.certificate.CertificateDAO;
import dto.certificate.MemberCertificateDTO;

public class CertificateService {
	private final CertificateDAO certificateDAO = new CertificateDAO();
	
	public boolean upsertCert(MemberCertificateDTO mdto) {
		if(certificateDAO.existsCert(mdto.getMemberId(), mdto.getCertId()))
			return certificateDAO.updateCert(mdto);
		else
			return certificateDAO.addCert(mdto);
	}
	
	public List<CertificateDTO> certFindAll(){
		List<CertificateDTO> certificates = certificateDAO.certFindAll();
		return certificates;
	}
	
	public List<MemberCertificateDTO> getCertificates(String memberId){
		return certificateDAO.memberCertificates(memberId);
	}

    public List<CertificateDTO> searchCertsByName(String keyword){
        // 검색어가 비어있거나 너무 짧으면 검색X
        if(keyword == null || keyword.trim().isEmpty()){
            return new ArrayList<>();
        }

        return certificateDAO.searchCertsByName(keyword);
    }
}
