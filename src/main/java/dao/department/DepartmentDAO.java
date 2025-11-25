package dao.department;

import common.JdbcConnectUtil;
import dto.department.DepartmentDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAO {
    public List<DepartmentDTO> deptFindAll() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<DepartmentDTO> departments = new ArrayList<>();
        String FIND_ALL_DEPT = "SELECT dept_id, dept_name FROM department;";

        try {
            conn = JdbcConnectUtil.getConnetion();
            pstmt = conn.prepareStatement(FIND_ALL_DEPT);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                DepartmentDTO departmentDTO = new DepartmentDTO();
                departmentDTO.setDeptId(rs.getInt("dept_id"));
                departmentDTO.setDeptName(rs.getString("dept_name"));
                departments.add(departmentDTO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectUtil.close(conn, pstmt, rs);
        }

        return departments;
    }

    public String findDeptNameById(int deptId) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String FIND_NAME_BY_DEPT_ID = "SELECT dept_name FROM department WHERE dept_id = ?;";

        try {
            con = JdbcConnectUtil.getConnetion();
            pstmt = con.prepareStatement(FIND_NAME_BY_DEPT_ID);

            pstmt.setInt(1, deptId);

            rs = pstmt.executeQuery();

            if(rs.next()) {
                return rs.getString("dept_id");
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            JdbcConnectUtil.close(con, pstmt, rs);
        }

        return null;
    }
}
