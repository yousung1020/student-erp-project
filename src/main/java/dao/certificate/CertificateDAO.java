package dao.certificate;

import dto.certificate.CertificateDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import common.JdbcConnectUtil;
import dto.certificate.CertificateDTO;
import dto.certificate.MemberCertificateDTO;
import dto.department.DepartmentDTO;
import dto.member.MemberSignUpDTO;

public class CertificateDAO {

    public List<MemberCertificateDTO> memberCertificates(String memberId) {
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

            while (rs.next()) {
                MemberCertificateDTO dto = new MemberCertificateDTO();
                dto.setMemberCertId(rs.getInt("member_cert_id"));
                dto.setMemberId(rs.getString("member_id"));
                dto.setCertId(rs.getInt("cert_id"));
                dto.setCertDate(rs.getDate("cert_date"));
                list.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcConnectUtil.close(conn, pstmt, rs);
        }
        return list;
    }
      
	public boolean addCert(MemberCertificateDTO mdto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String SQL_ADDCERT = "INSERT INTO member_certificate(member_id, cert_id, cert_date) VALUES(?, ?, ?)";
		
		int result = 0;
		try {
			con = JdbcConnectUtil.getConnetion();
			
			pstmt = con.prepareStatement(SQL_ADDCERT);
			
			pstmt.setString(1, mdto.getMemberId());
			pstmt.setInt(2, mdto.getCertId());
			pstmt.setDate(3, new java.sql.Date(mdto.getCertDate().getTime()));
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcConnectUtil.close(con, pstmt);
		}
		
		return result > 0;
	}
	
	public List<CertificateDTO> certFindAll() {
		Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<CertificateDTO> certificates = new ArrayList<>();
        String FIND_ALL_CERT = "SELECT cert_id, cert_name FROM certificate;";

        try {
            conn = JdbcConnectUtil.getConnetion();
            pstmt = conn.prepareStatement(FIND_ALL_CERT);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                CertificateDTO certificateDTO = new CertificateDTO();
                certificateDTO.setCertId(rs.getInt("cert_id"));
                certificateDTO.setCertName(rs.getString("cert_name"));
                certificates.add(certificateDTO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectUtil.close(conn, pstmt, rs);
        }

        return certificates;
	}
	
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
            JdbcConnectUtil.close(conn, pstmt, rs);
        }
        return list;
    }

    public List<CertificateDTO> searchCertsByName(String keyword) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<CertificateDTO> certificates = new ArrayList<>();
        // cert_summary와 cert_trend 컬럼도 함께 조회
        String SQL = "SELECT cert_id, cert_name, cert_summary, cert_trend FROM certificate WHERE cert_name LIKE ? LIMIT 10";

        try {
            conn = JdbcConnectUtil.getConnetion();
            pstmt = conn.prepareStatement(SQL);
            // 검색 방식을 'starts with' (keyword%)에서 'contains' (%keyword%)로 변경
            pstmt.setString(1, "%" + keyword + "%");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                CertificateDTO dto = new CertificateDTO();
                dto.setCertId(rs.getInt("cert_id"));
                dto.setCertName(rs.getString("cert_name"));
                dto.setCertSummary(rs.getString("cert_summary"));
                dto.setCertTrend(rs.getString("cert_trend")); // certTrend 필드 설정
                certificates.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectUtil.close(conn, pstmt, rs);
        }
        return certificates;
    }
}
