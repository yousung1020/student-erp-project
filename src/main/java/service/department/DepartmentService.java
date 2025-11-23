package service.department;

import dao.department.DepartmentDAO;
import dto.department.DepartmentDTO;
import java.util.List;

public class DepartmentService {
    private final DepartmentDAO departmentDAO = new DepartmentDAO();

    public List<DepartmentDTO> deptFindAll() {
        // 추후 커스텀 예외 처리 로직 추가 예정
        // 지금은 dao 한테 로직 위임
        List<DepartmentDTO> departments = departmentDAO.deptFindAll();

        return departments;
    }
}
