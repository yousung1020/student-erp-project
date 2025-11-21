package dao.admin;

import common.JdbcConnectUtil;
import dto.admin.DeptDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDeptDAO {
	public List<DeptDTO> selectAllDepartments(){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<DeptDTO> deptList = new ArrayList<>();
		
		String sql = "SELECT * FROM department ORDER BY dept_id ASC";
		
		try {
			conn = JdbcConnectUtil.getConnetion();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                DeptDTO dto = new DeptDTO();
                dto.setDeptId(rs.getInt("dept_id"));
                dto.setDeptName(rs.getString("dept_name"));
                deptList.add(dto);
            }
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcConnectUtil.close(conn, pstmt, rs);
		}
		return deptList;
	}
}
