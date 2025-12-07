<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>마이페이지 - 내 정보 수정</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">

    <%-- CDN(Content Delivery Network): 라이브러리 파일을 직접 다운로드하지 않고, 전 세계에 분산된 서버를 통해 빠르게 불러오는 방식. --%>
    <%-- Select2 라이브러리의 디자인(CSS) 파일 로드 --%>
    <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
    <%-- Select2가 의존하는 jQuery 라이브러리 로드 --%>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <%-- Select2 라이브러리의 기능(JavaScript) 파일 로드 --%>
    <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
</head>
<body>
    <%-- 공통 헤더와 푸터 include --%>
    <jsp:include page="/WEB-INF/views/common/header.jsp" />

    <div>
        <h1>내 정보 수정</h1>
        
        <%-- param: URL의 파라미터(쿼리스트링) 값을 가져오는 EL 내장 객체. 예: /mypage?status=success 의 'status' 값을 가져옴. --%>
        <c:if test="${param.status == 'success'}">
            <p style="color: green;">회원 정보가 성공적으로 수정되었습니다!</p>
        </c:if>
        <c:if test="${param.status == 'fail'}">
            <p style="color: red;">회원 정보 수정에 실패했습니다. <c:if test="${not empty param.errorMsg}"> (${param.errorMsg})</c:if></p>
        </c:if>
        <%-- error: 컨트롤러가 포워드할 때 request.setAttribute("error", "메시지")로 넘겨준 값을 EL로 표현. --%>
        <c:if test="${not empty error}">
            <p style="color: red;">${error}</p>
        </c:if>

        <form action="${pageContext.request.contextPath}/member/my-page" method="POST">
            <div>
                <label for="memberId">아이디</label>
                <input type="text" id="memberId" name="memberId" value="${memberInfo.memberId}" readonly>
            </div>
            <div>
                <label for="memberName">이름</label>
                <input type="text" id="memberName" name="memberName" value="${memberInfo.memberName}" readonly>
            </div>
            <div>
                <label for="memberEmail">이메일</label>
                <input type="email" id="memberEmail" name="memberEmail" value="${memberInfo.memberEmail}">
            </div>
            <div>
                <label for="deptId">학과</label>
                <%-- select 태그: 드롭다운 목록의 전체적인 틀. name="deptId"로 지정하여, 폼 제출 시 선택된 option의 value가 이 이름으로 전송됨. --%>
                <select id="deptId" name="deptId" style="width: 100%; padding: 10px;">
                    <c:forEach var="dept" items="${departments}">
                        <%-- option 태그: 드롭다운의 각 항목. value에는 학과 ID, 보이는 텍스트는 학과 이름으로 설정. --%>
                        <%-- 현재 회원의 학과와 목록의 학과가 같으면 'selected' 속성을 출력 --%>
                        <option value="${dept.deptId}" ${memberInfo.deptId == dept.deptId ? 'selected' : ''}>
                            ${dept.deptName}
                        </option>
                    </c:forEach>
                </select>
            </div>
            <div>
                <label for="memberPassword">새 비밀번호</label>
                <input type="password" id="memberPassword" name="memberPassword" placeholder="변경할 비밀번호 (비워두면 변경 안됨)">
            </div>
            <div>
                <button type="submit">정보 수정</button>
            </div>
        </form>
        <form action="${pageContext.request.contextPath}/member/delete" method="POST" onsubmit="return confirm('정말 탈퇴하시겠습니까?');">
        	<button type="submit">회원 탈퇴</button>
        </form>
    </div>

    <jsp:include page="/WEB-INF/views/common/footer.jsp" />

    <%-- 자바스크립트 코드 블록 --%>
    <script type="text/javascript">
        // HTML 문서의 모든 요소가 로드된 후, 중괄호 안의 코드를 실행하라는 jQuery 문법.
        $(document).ready(function() {
            // $('#deptId'): id가 'deptId'인 HTML 요소를 선택.
            // .select2(): 선택된 요소에 Select2 라이브러리의 기능을 적용하라는 명령.
            $('#deptId').select2();
        });
    </script>
</body>
</html>
