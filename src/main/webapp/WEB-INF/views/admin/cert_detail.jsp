<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>자격증 상세 정보</title>
<style>
    body { font-family: Arial, sans-serif; margin: 20px; }
    table { border-collapse: collapse; width: 60%; margin-top: 20px; }
    th, td { border: 1px solid #ddd; padding: 12px; text-align: left; }
    th { background-color: #eaf6ff; width: 150px; }
    .message { color: green; font-weight: bold; margin-bottom: 10px; }
    .error { color: red; font-weight: bold; margin-bottom: 10px; }
</style>
</head>
<body>

<h2>🔍 자격증 상세 정보</h2>
<hr>

<%-- 메시지/에러 출력 (Redirect된 경우 param으로 받음) --%>
<c:if test="${not empty param.message}">
    <p class="message">✅ 성공: <c:out value="${param.message}" /></p>
</c:if>
<c:if test="${not empty param.error}">
    <p class="error">❌ 오류: <c:out value="${param.error}" /></p>
</c:if>

<c:choose>
    <c:when test="${not empty certificateDetail}">
        <p>ID: <c:out value="${certificateDetail.certId}" /></p>

        <table>
            <tr>
                <th>자격증 이름</th>
                <td><c:out value="${certificateDetail.certName}" /></td>
            </tr>
            <tr>
                <th>관련 직무</th>
                <td><c:out value="${certificateDetail.certJob}" /></td>
            </tr>
            <tr>
                <th>자격증 요약</th>
                <td><c:out value="${certificateDetail.certSummary}" /></td>
            </tr>
            <tr>
                <th>트렌드/전망</th>
                <td><c:out value="${certificateDetail.certTrend}" /></td>
            </tr>
        </table>
        
        <p style="margin-top: 20px;">
            <a href="cert-manage?action=list">⬅️ 목록으로 돌아가기</a>
            
            <%-- 수정 버튼을 눌렀을 때, 목록 페이지로 돌아가서 해당 ID의 수정 폼을 열도록 스크립트 호출 --%>
            <button onclick="location.href='cert-manage?certId=${certificateDetail.certId}#edit-form-${certificateDetail.certId}'">수정</button>
            
            <%-- 삭제 폼 --%>
            <form action="cert-manage" method="post" style="display:inline;" onsubmit="return confirm('정말로 [${certificateDetail.certName}] 자격증을 삭제하시겠습니까?');">
                <input type="hidden" name="action" value="delete">
                <input type="hidden" name="certId" value="${certificateDetail.certId}">
                <button type="submit" style="background-color: #f44336; color: white;">삭제</button>
            </form>
        </p>
    </c:when>
    <c:otherwise>
        <p class="error">자격증 정보를 찾을 수 없습니다.</p>
        <a href="cert-manage?action=list">⬅️ 목록으로 돌아가기</a>
    </c:otherwise>
</c:choose>

</body>
</html>