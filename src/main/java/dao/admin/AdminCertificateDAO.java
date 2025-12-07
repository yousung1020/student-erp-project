package dao.admin;

import common.JdbcConnectUtil;
import dto.admin.AdminCertificateDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminCertificateDAO {
	public int inserCertificate(AdminCertificateDTO dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO certificate (cert_name, cert_job, cert_summary,"+
					 " cert_trend) VALUES (? , ? , ? , ?)";
		try {
			conn = JdbcConnectUtil.getConnetion();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getCertName());
			pstmt.setString(2, dto.getCertJob());
			pstmt.setString(3, dto.getCertSummary());
			pstmt.setString(4, dto.getCertTrend());
			int result = pstmt.executeUpdate();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		} finally {
			JdbcConnectUtil.close(conn, pstmt);
		}
		
	}
	
	public List<AdminCertificateDTO> selectAllCertificates(){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<AdminCertificateDTO> list = new ArrayList<>();
		
		String sql = "SELECT * FROM certificate ORDER BY cert_id ASC";
		try {
			conn = JdbcConnectUtil.getConnetion();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				AdminCertificateDTO dto = new AdminCertificateDTO();
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
			JdbcConnectUtil.close(conn, pstmt, rs);
		}
		return list;
	}
	
	public int updateCertificate(AdminCertificateDTO dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "UPDATE certificate SET cert_name = ? , cert_job = ? , cert_summary = ? , cert_trend = ? WHERE cert_id = ?";
		
		try {
			conn = JdbcConnectUtil.getConnetion();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getCertName());
            pstmt.setString(2, dto.getCertJob());
            pstmt.setString(3, dto.getCertSummary());
            pstmt.setString(4, dto.getCertTrend());
            pstmt.setInt(5, dto.getCertId());
            
            return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			JdbcConnectUtil.close(conn, pstmt);
		}
	}
	
	public int deleteCertificate(int certId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM certificate WHERE cert_id = ?";
		try {
			conn = JdbcConnectUtil.getConnetion();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, certId);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			JdbcConnectUtil.close(conn, pstmt);
		}
	}
}
