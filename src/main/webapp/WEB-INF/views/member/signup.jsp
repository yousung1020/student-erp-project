<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>학생 회원가입</title>
    <!-- Tailwind CSS CDN 로드 -->
    <script src="https://cdn.tailwindcss.com"></script>
    <style>
        body {
            font-family: 'Inter', sans-serif;
            background-color: #f4f7f9;
        }
        /* 학과 자동 완성 목록 스타일 */
        #department-suggestions {
            max-height: 200px;
            overflow-y: auto;
            border: 1px solid #ccc;
            border-top: none;
            z-index: 10;
        }
        .suggestion-item {
            padding: 8px 12px;
            cursor: pointer;
            transition: background-color 0.15s;
        }
        .suggestion-item:hover {
            background-color: #f1f5f9; /* Tailwind gray-100 */
        }
        .suggestion-item.selected {
            background-color: #e0f2f7; /* Tailwind cyan-100 */
            font-weight: 600;
        }
        /* 오류 메시지 스타일 (빨간색) */
        .error-message {
            color: #ef4444; /* Tailwind red-500 */
            font-size: 0.875rem; /* text-sm */
            font-weight: 500; /* font-medium */
            margin-top: 4px;
        }
    </style>
    <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
</head>
<body class="min-h-screen flex items-center justify-center">

    <div class="w-full max-w-lg p-8 space-y-6 bg-white shadow-2xl rounded-xl border border-gray-200 m-4">
        <h2 class="text-3xl font-extrabold text-center text-indigo-700">학생 회원가입</h2>
        <p class="text-center text-gray-500">학과별 자격증 및 취업 정보를 이용해보세요.</p>

        <!-- 서버-사이드 일반 오류 메시지 (DB 오류 등, ID/학과 오류가 아닐 때만 표시) -->
        <% 
            String errorType = (String) request.getAttribute("errorType");
            String errorMessage = (String) request.getAttribute("errorMessage");
            
            // ID, 학과 오류가 아닌 경우에만 최상단에 표시
            if (errorType != null && !errorType.equals("ID_DUPLICATION") && !errorType.equals("DEPARTMENT_ERROR")) {
        %>
            <div class="p-3 bg-red-100 border border-red-400 text-red-700 rounded-lg text-sm font-medium">
                <%= errorMessage %>
            </div>
        <%
            }
        %>

        <form action="${pageContext.request.contextPath}/member/signup" method="post" class="space-y-6" id="signupForm">
            
            <!-- ID -->
            <div>
                <label for="studentId" class="block text-sm font-semibold text-gray-700 mb-1">아이디 (ID)</label>
                <input type="text" id="studentId" name="memberId" required 
                       class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-indigo-600 focus:border-indigo-600 transition duration-150"
                       placeholder="예: abcd12"
                       value="${param.studentId}">
                
                <!-- 🟥 ID 중복 오류 표시 (서버에서 받은 경우) -->
                <% if (errorType != null && errorType.equals("ID_DUPLICATION")) { %>
                    <div class="error-message" id="id-error">
                        <%= errorMessage %>
                    </div>
                <% } else { %>
                    <div class="error-message" id="id-error"></div>
                <% } %>
            </div>

            <!-- 비밀번호 (보안상 유지하지 않음) -->
            <!-- 비밀번호 필드는 보안 및 브라우저 기본 동작 때문에 값을 유지하지 않는 것이 일반적입니다. -->
            <div>
                <label for="password" class="block text-sm font-semibold text-gray-700 mb-1">비밀번호</label>
                <input type="password" id="password" name="memberPassword" required
                       class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-indigo-600 focus:border-indigo-600 transition duration-150"
                       placeholder="비밀번호를 입력하세요">
            </div>
            
            <!-- 비밀번호 확인 (보안상 유지하지 않음) -->
            <div>
                <label for="passwordConfirm" class="block text-sm font-semibold text-gray-700 mb-1">비밀번호 확인</label>
                <input type="password" id="passwordConfirm" name="passwordConfirm" required
                       class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-indigo-600 focus:border-indigo-600 transition duration-150"
                       placeholder="다시 한번 입력하세요">
                <!-- 🟥 비밀번호 불일치 오류 표시 (JS로 제어) -->
                <div class="error-message" id="password-confirm-error"></div>
            </div>

            <!-- 이름 -->
            <div>
                <label for="name" class="block text-sm font-semibold text-gray-700 mb-1">이름</label>
                <input type="text" id="name" name="memberName" required
                       class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-indigo-600 focus:border-indigo-600 transition duration-150"
                       placeholder="이름을 입력하세요"
                       value="${param.name}">
            </div>

            <!-- Email -->
            <div>
                <label for="memberEmail" class="block text-sm font-semibold text-gray-700 mb-1">이메일</label>
                <input type="email" id="memberEmail" name="memberEmail" required
                       class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-indigo-600 focus:border-indigo-600 transition duration-150"
                       placeholder="예: example@university.ac.kr"
                       value="${param.memberEmail}">
            </div>

            <!-- 학과 입력 필드 (동적 검색) -->
            <div>
                <label for="deptId">학과</label>
                <%-- select 태그: 드롭다운 목록의 전체적인 틀. name="deptId"로 지정하여, 폼 제출 시 선택된 option의 value가 이 이름으로 전송됨. --%>
                <select id="deptId" name="deptId" style="width: 100%; padding: 10px;">
                    <c:forEach var="dept" items="${departments}">
                        <%-- option 태그: 드롭다운의 각 항목. value에는 학과 ID, 보이는 텍스트는 학과 이름으로 설정. --%>
                        <%-- 현재 회원의 학과와 목록의 학과가 같으면 'selected' 속성을 출력 --%>
                        <option value="${dept.deptId}">
                            ${dept.deptName}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <!-- 제출 버튼 -->
            <button type="submit" id="submitButton"
                    class="w-full py-3 px-4 bg-indigo-600 text-white font-bold rounded-lg shadow-lg hover:bg-indigo-700 transition duration-200 focus:outline-none focus:ring-4 focus:ring-indigo-500 focus:ring-opacity-50 transform hover:scale-[1.01]">
                가입 완료 및 정보 등록
            </button>
        </form>
        
        <div class="text-center text-sm text-gray-500 pt-6 border-t border-gray-100">
            이미 계정이 있으신가요? 
            <a href="${pageContext.request.contextPath}/member/login" class="text-indigo-600 hover:text-indigo-500 font-bold">로그인</a>
        </div>
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

</body>
</html>