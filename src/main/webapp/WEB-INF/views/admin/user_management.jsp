<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 회원 관리</title>
</head>
<body>

<c:if test="${not empty error}">
    <p style="color: red;">🚨 오류 발생: ${error}</p>
</c:if>
<c:if test="${not empty param.message}">
    <p style="color: green;">✅ 메시지: ${param.message}</p>
</c:if>
<c:if test="${not empty param.error}">
    <p style="color: red;">❌ 오류: ${param.error}</p>
</c:if>

## 👥 회원 목록

<c:choose>
    <c:when test="${not empty memberList}">
        <table border="1">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>이름</th>
                    <th>이메일</th>
                    <th>부서 ID</th>
                    <th>관리</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="member" items="${memberList}">
                    <tr>
                        <td><c:out value="${member.memberId}" /></td>
                        <td><c:out value="${member.memberName}" /></td>
                        <td><c:out value="${member.memberEmail}" /></td>
                        <td><c:out value="${member.deptId}" /></td>
                        <td>
                            <a href="user-manage?action=detail&memberId=${member.memberId}">상세/수정</a>
                            <form action="user-manage" method="post" style="display:inline;" onsubmit="return confirm('정말로 ${member.memberId} 회원을 삭제하시겠습니까?');">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="memberId" value="${member.memberId}">
                                <button type="submit">삭제</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:when>
    <c:otherwise>
        <p>등록된 회원 정보가 없습니다.</p>
    </c:otherwise>
</c:choose>

<hr>

## ➕ 회원 등록 폼

<form action="user-manage" method="post">
    <input type="hidden" name="action" value="create">
    
    <div>
        <label for="memberId">아이디:</label>
        <input type="text" id="memberId" name="memberId" required>
    </div>
    <div>
        <label for="memberPassword">비밀번호:</label>
        <input type="password" id="memberPassword" name="memberPassword" required>
    </div>
    <div>
        <label for="memberName">이름:</label>
        <input type="text" id="memberName" name="memberName" required>
    </div>
    <div>
        <label for="memberEmail">이메일:</label>
        <input type="email" id="memberEmail" name="memberEmail" required>
    </div>
    
    <div>
        <label for="deptId">부서:</label>
        <select id="deptId" name="deptId" required>
            <option value="">부서를 선택하세요</option>
            <c:if test="${not empty deptList}">
                <c:forEach var="dept" items="${deptList}">
                    <option value="${dept.deptId}">
                        <c:out value="${dept.deptName}" />
                    </option>
                </c:forEach>
            </c:if>
            <c:if test="${empty deptList}">
                <option value="" disabled>부서 목록 없음</option>
            </c:if>
        </select>
    </div>
    
    <button type="submit">회원 등록</button>
</form>

</body>
</html>