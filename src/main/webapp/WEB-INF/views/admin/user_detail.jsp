<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>회원 상세 및 수정</title>
</head>
<body>
    <h2>회원 상세 정보</h2>
    
    <c:if test="${not empty memberDetail}">
        <p><strong>아이디:</strong> ${memberDetail.memberId}</p>
        <p><strong>이름:</strong> ${memberDetail.memberName}</p>
        <p><strong>이메일:</strong> ${memberDetail.memberEmail}</p>
        <p><strong>학과 ID:</strong> ${memberDetail.deptId}</p>
    </c:if>

    <hr>
    
    <h3>비밀번호 수정</h3>
    <form action="user-manage" method="post">
        <input type="hidden" name="action" value="update">
        <input type="hidden" name="memberId" value="${memberDetail.memberId}">
        <input type="hidden" name="updateType" value="password">
        <label for="newPassword">새 비밀번호:</label>
        <input type="password" id="newPassword" name="newPassword" required><br><br>
        
        <button type="submit">비밀번호 수정</button>
    </form>
    
    <hr>
    
    <h3>학과 정보 수정</h3>
        <form action="user-manage" method="post">
        <input type="hidden" name="action" value="update">
        <input type="hidden" name="memberId" value="${memberDetail.memberId}">
        <input type="hidden" name="updateType" value="department">
        <label for="newDeptId">새 학과:</label>
        <select id="newDeptId" name="newDeptId" required>
        	<c:forEach var="dept" items="${departmentList}">
        		<option value="${dept.deptId}"
        				<c:if test="${memberDetail.deptId == dept.deptId}"> selected</c:if>>
        			${dept.deptName} (${dept.deptId})
        		</option>
        	</c:forEach>
        </select><br><br>
        <button type="submit">학과 수정</button>
    </form>
    <a href="user-manage?action=list">회원 목록으로</a>
</body>
</html>