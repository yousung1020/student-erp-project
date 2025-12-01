<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div id="add-cert-area" class="internal-content">
    <form action="${pageContext.request.contextPath}/certificate/add" method="POST">
        <h3>새 자격증 추가</h3>

        <div class="form-group">
            <label for="add-cert-id">자격증 이름</label>
            <%-- 이름으로 검색 가능한 Select2 드롭다운 --%>
            <select id="add-cert-id" name="certId" style="width: 100%;">
                <option value="">자격증을 선택하세요</option>
                <%-- CertListController가 request에 담아준 'certificates' 리스트를 사용 --%>
                <c:forEach var="cert" items="${certificates}">
                    <option value="${cert.certId}">${cert.certName}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label for="acquisitionDate">취득일</label>
            <input type="date" id="acquisitionDate" name="acquisitionDate" required>
        </div>
        
        <button type="submit">내 자격증으로 추가</button>
    </form>
</div>

<%-- Select2 라이브러리를 이 select 태그에 적용하는 스크립트 --%>
<script type="text/javascript">
    $(document).ready(function() {
        $('#add-cert-id').select2({
            placeholder: "자격증을 검색하거나 선택하세요",
            allowClear: true
        });
    });
</script>