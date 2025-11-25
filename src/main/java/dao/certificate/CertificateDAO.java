package dao.certificate;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import common.JdbcConnectUtil;
import dto.certificate.CertificateDTO;

public class CertificateDAO {
	
	public List<CertificateDTO> MemberCertificates(String memberId){
		List<CertificateDTO> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		// mc는 member_certificate의 별칭 , c는 certificate의 별칭입니다.
		String sql = "SELECT c.cert_id, c.cert_name, c.cert_job, c.cert_summary, c.cert_trend " +
	             "FROM member_certificate mc " +
	             "JOIN certificate c ON mc.cert_id = c.cert_id " +
	             "WHERE mc.member_id = ?";
		try {
			conn = JdbcConnectUtil.getConnetion();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				CertificateDTO dto = new CertificateDTO();
				dto.setCertId(rs.getInt("cert_id"));
				dto.setCertName(rs.getString("cert_name"));
				dto.setCertJob(rs.getString("cert_job"));
				dto.setCertSummary(rs.getString("cert_summary"));
                dto.setCertTrend(rs.getString("cert_trend"));
                
                list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcConnectUtil.close(conn, pstmt,rs);
		}
		return list;
	}
}
