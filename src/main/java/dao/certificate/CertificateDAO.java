package dao.certificate;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import common.JdbcConnectUtil;
import dto.certificate.MemberCertificateDTO;

public class CertificateDAO {
	
	public List<MemberCertificateDTO> MemberCertificates(String memberId){
		List<MemberCertificateDTO> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		// mc는 member_certificate의 별칭 , c는 certificate의 별칭입니다.
		String sql = "SELECT mc.member_cert_id, mc.member_id, mc.cert_id, mc.cert_date, " +
	             "c.cert_name , c.cert_summary " +
	             "FROM member_certificate mc " +
	             "JOIN certificate c ON mc.cert_id = c.cert_id " +
	             "WHERE mc.member_id = ?";
		try {
			conn = JdbcConnectUtil.getConnetion();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				MemberCertificateDTO dto = new MemberCertificateDTO();
				dto.setMemberCertId(rs.getInt("member_cert_id"));
				dto.setMemberId(rs.getString("member_id"));
				dto.setCertId(rs.getInt("cert_id"));
				dto.setCertDate(rs.getDate("cert_date"));
                dto.setCertName(rs.getString("cert_name"));
                dto.setCertSummary(rs.getString("cert_summary"));
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
