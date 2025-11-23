package dao.department;

import common.JdbcConnectUtil;
import dto.department.DepartmentDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.JdbcConnectUtil;
import dto.member.DepartmentDTO;

public class DepartmentDAO {
	private final String SQL_SELECT_ALL = "SELECT dept_id, dept_name FROM department ORDER BY dept_name ASC";
	
	public List<DepartmentDTO> getAllDepartments() {
        List<DepartmentDTO> departmentList = new ArrayList<>();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = JdbcConnectUtil.getConnetion();
            pstmt = con.prepareStatement(SQL_SELECT_ALL);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                String deptId = rs.getString("dept_id");
                String deptName = rs.getString("dept_name");
                
                departmentList.add(new DepartmentDTO(deptId, deptName));
            }

        } catch (SQLException e) {
            System.err.println("[DepartmentDAO] 학과 목록 조회 중 DB 오류 발생: " + e.getMessage());
            e.printStackTrace();
        } finally {
            JdbcConnectUtil.close(con, pstmt, rs);
        }

        // DB 연결 실패 시에도 기본 옵션을 제공하거나 빈 리스트를 반환할 수 있습니다.
        if (departmentList.isEmpty()) {
             System.out.println("[DepartmentDAO] DB에서 학과 정보를 가져오는 데 실패했습니다. 기본값을 추가합니다.");
             // 테스트용 기본값을 추가하여 프론트엔드 테스트를 용이하게 합니다.
             departmentList.add(new DepartmentDTO("COMP", "컴퓨터공학과"));
             departmentList.add(new DepartmentDTO("ELEC", "전자공학과"));
             departmentList.add(new DepartmentDTO("BUSS", "경영학과"));
             departmentList.add(new DepartmentDTO("ARTD", "디자인학과"));
        }

        return departmentList;
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
}
