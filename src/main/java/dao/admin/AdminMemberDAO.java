package dao.admin;

import common.JdbcConnectUtil;
import dto.admin.AdminMemberCreateDTO;
import dto.admin.AdminMemberDeleteDTO;
import dto.admin.AdminMemberSelectDTO;
import dto.admin.AdminMemberUpdateDTO;
import dto.admin.AdminMemberDetailDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminMemberDAO {
	public int insertMember(AdminMemberCreateDTO dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO member (member_id, member_password, member_name,member_email,dept_id) VALUES (?,?,?,?,?)";
		int result = 0;
		
		try {
			conn = JdbcConnectUtil.getConnetion();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getMemberId());
			pstmt.setString(2, dto.getMemberPassword());
			pstmt.setString(3, dto.getMemberName());
			pstmt.setString(4, dto.getMemberEmail());
			pstmt.setInt(5, dto.getDeptId());
			
			result = pstmt.executeUpdate();
		}catch (SQLException e){
			e.printStackTrace();
		} finally {
			JdbcConnectUtil.close(conn, pstmt);
		}
		return result;
	}
	
	public List<AdminMemberSelectDTO> selectMemberList(){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<AdminMemberSelectDTO> memberList = new ArrayList<>();
		
		String sql = "SELECT member_id , member_name, member_email, dept_id FROM member WHERE is_admin = 0";
		
		try {
			conn = JdbcConnectUtil.getConnetion();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				AdminMemberSelectDTO dto = new AdminMemberSelectDTO();
				dto.setMemberId(rs.getString("member_id"));
				dto.setMemberName(rs.getString("member_name"));
                dto.setMemberEmail(rs.getString("member_email"));
                dto.setDeptId(rs.getInt("dept_id"));
                memberList.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcConnectUtil.close(conn, pstmt, rs);
		}
		return memberList;
	}
	
	public AdminMemberDetailDTO selectMemberDetail(String memberId) {
		Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        AdminMemberDetailDTO dto = null;
        
        String sql = "SELECT member_id, member_name, member_email, dept_id FROM member WHERE member_id = ?";
        
        try {
        	conn = JdbcConnectUtil.getConnetion();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, memberId);
            rs = pstmt.executeQuery();
            
            if(rs.next()) {
            	dto = new AdminMemberDetailDTO();
            	dto.setMemberId(rs.getString("member_id"));
            	dto.setMemberName(rs.getString("member_name"));
            	dto.setMemberEmail(rs.getString("member_email"));
            	dto.setDeptId(rs.getInt("dept_id"));
            }
        } catch(SQLException e) {
        	e.printStackTrace();
        } finally {
			JdbcConnectUtil.close(conn, pstmt, rs);
		}
        return dto;
	}
	
	public int updateMember(AdminMemberUpdateDTO dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "UPDATE member SET member_password = ? WHERE member_id = ?";
		int result = 0;
		
		try {
			conn = JdbcConnectUtil.getConnetion();
	        pstmt = conn.prepareStatement(sql);
	        
	        pstmt.setString(1, dto.getNewPassword());
	        pstmt.setString(2, dto.getMemberId());
	        
	        result = pstmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcConnectUtil.close(conn, pstmt);
		}
		return result;
	}
	
	public int deleteMember(AdminMemberDeleteDTO dto) {
		Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "DELETE FROM member WHERE member_id = ?";
        int result = 0;
        
        try {
        	conn = JdbcConnectUtil.getConnetion();
            pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, dto.getMemberId());
            
            result = pstmt.executeUpdate();
        } catch(SQLException e) {
        	e.printStackTrace();
        } finally {
			JdbcConnectUtil.close(conn, pstmt);
		}
        return result;
	}
	
	public boolean checkMemberIdExists(String memberId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean exists = false;
		
		String sql = "SELECT COUNT(*) FROM member WHERE member_id = ?";
		
		try {
			conn = JdbcConnectUtil.getConnetion();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				if (rs.getInt(1) > 0) {
					exists = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return exists;
	}
}
