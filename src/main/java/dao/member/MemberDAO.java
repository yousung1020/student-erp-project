package dao.member;

import common.JdbcConnectUtil;
import dto.member.MemberInfoDTO;
import dto.member.MemberLoginDTO;
import dto.member.MemberUpdateDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberDAO {
    public boolean memberLogin(MemberLoginDTO mdto) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean result = false;
        String FIND_MEMBER = "SELECT * FROM member WHERE member_id = ? AND member_password = ?;";

        try {
            conn = JdbcConnectUtil.getConnetion();
            pstmt = conn.prepareStatement(FIND_MEMBER);

            pstmt.setString(1, mdto.getMemberId());
            pstmt.setString(2, mdto.getMemberPassword());

            rs = pstmt.executeQuery();

            result = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectUtil.close(conn, pstmt, rs);
        }

        return result;
    }

    public int memberUpdate(MemberUpdateDTO mdto) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int result = 0;
        // 비밀번호 유무에 따른 동적 쿼리를 생성하기 위해 StringBuilder 객체 사용
        StringBuilder updateMemberBuiler = new StringBuilder("UPDATE member SET member_email = ? ");

        boolean isExistPassword = mdto.getMemberPassword() != null;

        // 패스워드가 존재할 때만 update
        if (isExistPassword) {
            updateMemberBuiler.append(", member_password = ? ");
        }

        updateMemberBuiler.append(", dept_id = ? WHERE member_id = ?; ");

        try {
            conn = JdbcConnectUtil.getConnetion();
            pstmt = conn.prepareStatement(updateMemberBuiler.toString());
            // PreparedStatement의 파라미터 인덱스 (비밀번호 유무에 따른 동적 업데이트를 위함)
            int paramIndex = 1;

            pstmt.setString(paramIndex++, mdto.getMemberEmail());

            if (isExistPassword) {
                pstmt.setString(paramIndex++, mdto.getMemberPassword());
            }
            pstmt.setInt(paramIndex++, mdto.getDeptId());
            pstmt.setString(paramIndex, mdto.getMemberId());

            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectUtil.close(conn, pstmt);
        }

        return result;
    }

    public MemberInfoDTO findMemberById(String memberId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        MemberInfoDTO memberInfo = null;
        String FIND_BY_ID = "SELECT m.member_id, m.member_name, m.member_email, d.dept_id, d.dept_name "
                + "FROM member m JOIN department d ON m.dept_id = d.dept_id "
                + "WHERE m.member_id = ?";

        try {
            conn = JdbcConnectUtil.getConnetion();
            pstmt = conn.prepareStatement(FIND_BY_ID);
            pstmt.setString(1, memberId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                memberInfo = new MemberInfoDTO();
                memberInfo.setMemberId(rs.getString("member_id"));
                memberInfo.setMemberName(rs.getString("member_name"));
                memberInfo.setMemberEmail(rs.getString("member_email"));
                memberInfo.setDeptId(rs.getInt("dept_id"));
                memberInfo.setDeptName(rs.getString("dept_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectUtil.close(conn, pstmt, rs);
        }

        return memberInfo;
    }
}
