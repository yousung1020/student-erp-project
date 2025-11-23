package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import dto.member.DepartmentDTO;
import dao.department.DepartmentDAO;

@WebServlet(name = "DepartmentController", urlPatterns = "/api/departments")
public class DepartmentController extends HttpServlet {
       
	private DepartmentDAO departmentDAO;

    public void init() throws ServletException {
        super.init();
        this.departmentDAO = new DepartmentDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. 학과 목록 조회
        List<DepartmentDTO> departments = departmentDAO.getAllDepartments();

        // 2. 응답 설정 (JSON 타입)
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        // 3. 순수 Java (StringBuilder)를 사용하여 JSON 문자열 수동 생성
        String json = convertListToJson(departments);
        
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }

    /**
     * List<DepartmentDTO> 객체를 JSON 문자열로 변환합니다.
     * 외부 라이브러리(Jackson 등) 없이 순수 Java 코드로 구현합니다.
     * @param departments 학과 DTO 리스트
     * @return JSON 형식의 문자열
     */
    private String convertListToJson(List<DepartmentDTO> departments) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("[");
        
        for (int i = 0; i < departments.size(); i++) {
            DepartmentDTO dept = departments.get(i);
            
            // 객체 시작
            jsonBuilder.append("{");
            
            // "deptId" 필드 추가
            jsonBuilder.append("\"deptId\":\"").append(escapeJson(dept.getDeptId())).append("\",");
            
            // "deptName" 필드 추가
            jsonBuilder.append("\"deptName\":\"").append(escapeJson(dept.getDeptName())).append("\"");
            
            // 객체 종료
            jsonBuilder.append("}");
            
            // 마지막 요소가 아니면 콤마 추가
            if (i < departments.size() - 1) {
                jsonBuilder.append(",");
            }
        }
        
        jsonBuilder.append("]");
        return jsonBuilder.toString();
    }

    /**
     * JSON 값으로 사용될 문자열 내의 특수 문자를 이스케이프 처리합니다.
     * (예: 큰따옴표, 백슬래시 등)
     */
    private String escapeJson(String value) {
        if (value == null) {
            return "";
        }
        // 간단한 이스케이프 처리 (완벽한 처리는 아니지만, 일반적인 학과 이름에는 충분)
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }

}
