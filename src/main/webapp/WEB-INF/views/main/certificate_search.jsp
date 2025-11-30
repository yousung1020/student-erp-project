<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div id="cert-search-area" class="internal-content">
    <div class="search-input-group">
        <input type="text" id="cert-search-input" placeholder="자격증 이름을 검색하세요 (예: 정보처리기사)">
        <%-- 검색 버튼에 id 추가 --%>
        <button type="button" id="cert-search-button">🔍 검색</button>
    </div>
    
    <%-- 검색 결과가 동적으로 표시될 영역 --%>
    <div id="search-results-list">
        <%-- 초기에는 비워둠 --%>
    </div>
</div>

<%-- JS 파일 로드를 위한 contextPath 변수 선언 --%>
<script>
    var contextPath = "${pageContext.request.contextPath}";
    console.log(contextPath);
</script>
<script src="${pageContext.request.contextPath}/js/certificate/certificate-search.js"></script>