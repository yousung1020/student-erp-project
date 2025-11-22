package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import common.JdbcConnectUtil;
import dto.member.MemberSignUpDTO;

public class MemberDAO {
	Connection con = null; //클래스필드 아니고 로컬변수라서 초기화까지
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	private final String SQL_SIGNUP = "INSERT INTO member(member_id, member_password, member_name, member_email, dept_id) VALUES(?, ?, ?, ?, ?)";
    private final String SQL_ID_CHECK = "SELECT COUNT(*) FROM member WHERE member_id = ?";
	
    public boolean isIdExists(String memberId) {
        boolean exists = false;

        try {
            con = JdbcConnectUtil.getConnetion();
            pstmt = con.prepareStatement(SQL_ID_CHECK);
            pstmt.setString(1, memberId);
            
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                // COUNT(*) 결과가 1 이상이면 중복
                exists = rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // DB 오류 시 false 반환 (중복이 아니라고 가정)
            return false; 
        } finally {
            JdbcConnectUtil.close(con, pstmt, rs);
        }

        return exists;
    }
    
	public boolean signup(MemberSignUpDTO mdto) {
		int result = 0;
		try {
			
			con = JdbcConnectUtil.getConnetion();
			
			pstmt = con.prepareStatement(SQL_SIGNUP);
			
			pstmt.setString(1, mdto.getMemberId());
			pstmt.setString(2, mdto.getMemberPassword());
			pstmt.setString(3, mdto.getMemberName());
			pstmt.setString(4, mdto.getMemberEmail());
			pstmt.setInt(5, mdto.getMemberDept());
			
			result = pstmt.executeUpdate();		
 
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcConnectUtil.close(con, pstmt);
		}

		return result > 0;
	}
}
