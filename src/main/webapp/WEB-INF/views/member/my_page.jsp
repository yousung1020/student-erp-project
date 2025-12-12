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
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body>
    <%-- 공통 헤더와 푸터 include --%>
    <jsp:include page="/WEB-INF/views/common/header.jsp" />

    <div class="max-w-xl mx-auto mt-10 p-8 bg-white rounded-xl shadow-lg space-y-6">
        <h1 class="text-2xl font-bold text-gray-800">내 정보 수정</h1>
        
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

        <form action="${pageContext.request.contextPath}/member/my-page" method="POST" class="space-y-4">

        <div>
            <label for="memberId" class="block text-sm font-medium text-gray-700">아이디</label>
            <input type="text" id="memberId" name="memberId" 
                   value="${memberInfo.memberId}" readonly
                   class="w-full px-4 py-2 mt-1 border rounded-lg bg-gray-100 cursor-not-allowed">
        </div>

        <div>
            <label for="memberName" class="block text-sm font-medium text-gray-700">이름</label>
            <input type="text" id="memberName" name="memberName"
                   value="${memberInfo.memberName}" readonly
                   class="w-full px-4 py-2 mt-1 border rounded-lg bg-gray-100 cursor-not-allowed">
        </div>

        <div>
		    <label for="memberEmail" class="block text-sm font-medium text-gray-700">이메일</label>
		
		    <div class="flex gap-2 items-center">
		
		        <!-- 이메일 아이디 -->
		        <input type="text" id="emailLocal" name="emailLocal"
		               value="${memberInfo.memberEmail.substring(0, memberInfo.memberEmail.indexOf('@'))}"
		               class="w-1/3 px-3 py-2 border border-gray-300 rounded-lg focus:ring-indigo-600 focus:border-indigo-600"
		               placeholder="example">
		
		        <span class="text-gray-700">@</span>
		
		        <!-- 이메일 도메인 -->
		        <input type="text" id="emailDomain" name="emailDomain"
		               value="${memberInfo.memberEmail.substring(memberInfo.memberEmail.indexOf('@') + 1)}"
		               class="w-1/3 px-3 py-2 border border-gray-300 rounded-lg focus:ring-indigo-600 focus:border-indigo-600"
		               placeholder="domain.com">
		
		        <!-- 도메인 선택 -->
		        <select id="emailSelect" name="emailSelect"
		                class="w-1/3 px-3 py-2 border border-gray-300 rounded-lg focus:ring-indigo-600 focus:border-indigo-600"
		                onchange="email_check()">
		            <option value="direct">직접입력</option>
		            <option value="naver.com">naver.com</option>
		            <option value="hanmail.net">hanmail.net</option>
		            <option value="daum.net">daum.net</option>
		            <option value="nate.com">nate.com</option>
		            <option value="samsung.com">samsung.com</option>
		            <option value="gmail.com">gmail.com</option>
		        </select>
		    </div>
		    <c:if test="${param.errorType == 'email_miss'}">
			    <div class="text-red-500 text-sm font-medium mt-1">
			        ${param.errorMessage}
			    </div>
			</c:if>

		</div>

        <div>
            <label for="deptId" class="block text-sm font-medium text-gray-700">학과</label>
            <select id="deptId" name="deptId"
                    class="w-full px-4 py-2 mt-1 border border-gray-300 rounded-lg focus:ring-indigo-500 focus:border-indigo-500">
                <c:forEach var="dept" items="${departments}">
                    <option value="${dept.deptId}" ${memberInfo.deptId == dept.deptId ? 'selected' : ''}>
                        ${dept.deptName}
                    </option>
                </c:forEach>
            </select>
        </div>

        <div>
            <label for="memberPassword" class="block text-sm font-medium text-gray-700">새 비밀번호</label>
            <input type="password" id="memberPassword" name="memberPassword"
                   placeholder="변경할 비밀번호 (비워두면 변경 안됨)"
                   class="w-full px-4 py-2 mt-1 border border-gray-300 rounded-lg focus:ring-indigo-500 focus:border-indigo-500">
        </div>

        <button type="submit"
                class="w-full py-3 bg-indigo-600 text-white font-semibold rounded-lg hover:bg-indigo-700 transition">
            정보 수정
        </button>

    </form>

    <!-- 회원 탈퇴 버튼 -->
    <form action="${pageContext.request.contextPath}/member/delete" method="POST"
          onsubmit="return confirm('정말 탈퇴하시겠습니까?');">

        <button type="submit"
                class="w-full mt-2 py-3 bg-red-600 text-white font-semibold rounded-lg hover:bg-red-700 transition">
            회원 탈퇴
        </button>

    </form>
    </div>

    <%-- 자바스크립트 코드 블록 --%>
    <script type="text/javascript">
        // HTML 문서의 모든 요소가 로드된 후, 중괄호 안의 코드를 실행하라는 jQuery 문법.
        $(document).ready(function() {
            // $('#deptId'): id가 'deptId'인 HTML 요소를 선택.
            // .select2(): 선택된 요소에 Select2 라이브러리의 기능을 적용하라는 명령.
            $('#deptId').select2();
        });
    </script>
    
    <script>
    	function email_check() {
	        const select = document.getElementById("emailSelect");
	        const domain = document.getElementById("emailDomain");
	
	        if (select.value === "direct") {
	            domain.readOnly = false;
	            domain.value = "";
	            domain.focus();
	        } else {
	            domain.readOnly = true;
	            domain.value = select.value;
	        }
	    }
	</script>
</body>
</html>
