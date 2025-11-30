<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<head>
    <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
</head>
<%-- "자격증 추가" 폼 섹션 --%>
<div id="content-add-cert" class="internal-content">
    <form action="${pageContext.request.contextPath}/certificate/add" method="POST">
        <h3>새 자격증 추가</h3>
        <div class="form-group">
            <label for="certName">자격증 이름</label>
            <select id="certId" name="certId" style="width: 100%; padding: 10px;" required>
                <c:forEach var="cert" items="${certificates}">
                    <option value="${cert.certId}">
                        ${cert.certName}
                    </option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label for="acquisitionDate">취득일</label>
            <input type="date" id="acquisitionDate" name="acquisitionDate">
        </div>
        <button type="submit">추가하기</button>
    </form>
</div>

<script type="text/javascript">
    // HTML 문서의 모든 요소가 로드된 후, 중괄호 안의 코드를 실행하라는 jQuery 문법.
    $(document).ready(function() {
        $('#certId').select2();
    });
</script>