package service.certificate;

import dao.certificate.MajorInfoDAO;
import dto.certificate.MajorInfoDTO;

public class MajorInfoService {

    private final MajorInfoDAO majorInfoDAO = new MajorInfoDAO();
    public MajorInfoDTO getMajorRelatedInfo(String majorSeq) throws Exception {
        if (majorSeq == null || majorSeq.isEmpty()) {
            throw new IllegalArgumentException("학과 ID가 유효하지 않습니다.");
        }
        return majorInfoDAO.getMajorInfoFromAPI(majorSeq);
    }
}