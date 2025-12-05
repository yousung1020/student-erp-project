<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>자격증 관리 통합 페이지</title>
<style>
    body { font-family: Arial, sans-serif; margin: 20px; }
    table { border-collapse: collapse; width: 100%; margin-top: 15px; }
    th, td { border: 1px solid #ddd; padding: 10px; text-align: left; }
    th { background-color: #f2f2f2; }
    textarea { width: 98%; height: 60px; resize: vertical; } 
    .message { color: green; font-weight: bold; margin-bottom: 10px; }
    .error { color: red; font-weight: bold; margin-bottom: 10px; }
    .edit-row { background-color: #fffacd; }
</style>
</head>
<body>

<h2>📚 자격증 관리 통합 페이지</h2>
<hr>

<c:if test="${not empty message}">
    <p class="message">✅ 성공: <c:out value="${message}" /></p>
</c:if>
<c:if test="${not empty error}">
    <p class="error">❌ 오류: <c:out value="${error}" /></p>
</c:if>

<c:if test="${not empty param.message}">
    <p class="message">✅ 성공: <c:out value="${param.message}" /></p>
</c:if>
<c:if test="${not empty param.error}">
    <p class="error">❌ 오류: <c:out value="${param.error}" /></p>
</c:if>

➕ 새 자격증 추가

<form action="cert-manage" method="post">
    <input type="hidden" name="action" value="create">
    
    <table>
        <tr>
            <th>이름</th>
            <th>직무</th> 
            <th>요약</th>
            <th>트렌드</th>
            <th>등록</th>
        </tr>
        <tr>
            <td><input type="text" name="certName" required></td>
            <td><textarea name="certJob"></textarea></td> 
            <td><textarea name="certSummary" required></textarea></td>
            <td><textarea name="certTrend" required></textarea></td>
            <td><button type="submit">추가</button></td>
        </tr>
    </table>
</form>

<h2>📄 자격증 목록 (총 ${fn:length(certList)}개)</h2>

<c:choose>
    <c:when test="${not empty certList}">
        <table border="1">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>이름</th>
                    <th>관련 직무</th>
                    <th>관리</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="cert" items="${certList}">
                    
                    <tr id="edit-form-${cert.certId}" 
                        style="display:none;" 
                        class="edit-row">
                        <td colspan="4">
                            <h4>ID: <c:out value="${cert.certId}" /> 수정</h4>
                            <form action="cert-manage" method="post">
                                <input type="hidden" name="action" value="update">
                                <input type="hidden" name="certId" value="${cert.certId}">
                                
                                <p>이름: <input type="text" name="certName" value="<c:out value="${cert.certName}" />" required></p>
                                <p>직무:</p><textarea name="certJob"><c:out value="${cert.certJob}" /></textarea>
                                <p>요약:</p><textarea name="certSummary" required><c:out value="${cert.certSummary}" /></textarea>
                                <p>트렌드:</p><textarea name="certTrend" required><c:out value="${cert.certTrend}" /></textarea>
                                
                                <button type="submit">수정 완료</button>
                                <button type="button" onclick="toggleEditForm(${cert.certId})">취소</button>
                            </form>
                        </td>
                    </tr>
                    
                    <tr>
                        <td><c:out value="${cert.certId}" /></td>
                        <td>
                            <c:out value="${cert.certName}" />
                        </td>
                        <td>
                            <c:out value="${cert.certJob}" />
                        </td>
                        <td>
                            <button type="button" onclick="toggleEditForm(${cert.certId})">수정</button>
                            <form action="cert-manage" method="post" style="display:inline;" onsubmit="return confirm('정말로 [${cert.certName}] 자격증을 삭제하시겠습니까?');">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="certId" value="${cert.certId}">
                                <button type="submit">삭제</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:when>
    <c:otherwise>
        <p>등록된 자격증 정보가 없습니다.</p>
    </c:otherwise>
</c:choose>

<script>
    function toggleEditForm(certId) {
        const row = document.getElementById('edit-form-' + certId);
        if (row) {
            row.style.display = row.style.display === 'none' ? 'table-row' : 'none';
        }
    }
    window.onload = function() {
        const urlParams = new URLSearchParams(window.location.search);
        const certId = urlParams.get('certId');
        
        const updateMessage = urlParams.get('message');
        const updateError = urlParams.get('error');

        if ((updateMessage === 'updateSuccess' || updateError === 'updateFailed') && certId) {
            const editRow = document.getElementById('edit-form-' + certId);
            if (editRow) {
                editRow.style.display = 'table-row';
            }
        }
    };
</script>
</body>
</html>