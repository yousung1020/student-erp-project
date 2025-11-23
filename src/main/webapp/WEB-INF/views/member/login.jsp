<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>로그인</title>
    <!-- Tailwind CSS CDN 로드 -->
    <script src="https://cdn.tailwindcss.com"></script>
    <style>
        body {
            font-family: 'Inter', sans-serif;
            background-color: #f4f7f9;
        }
        /* 오류 메시지 스타일 */
        .error-message {
            color: #ef4444; /* Tailwind red-500 */
            font-size: 0.875rem;
            font-weight: 500;
            margin-top: 8px;
            padding: 8px;
            background-color: #fef2f2; /* red-50 */
            border: 1px solid #fca5a5; /* red-300 */
            border-radius: 0.5rem;
        }
    </style>
</head>
<body class="min-h-screen flex items-center justify-center">

    <div class="w-full max-w-sm p-8 space-y-6 bg-white shadow-2xl rounded-xl border border-gray-200 m-4">
        <h2 class="text-3xl font-extrabold text-center text-indigo-700">로그인</h2>
        <p class="text-center text-gray-500">정보 확인 후 접속해 주세요.</p>

        <!-- 서버-사이드 로그인 실패 메시지 표시 (Controller에서 설정된 경우) -->
        <% 
            String loginError = (String) request.getAttribute("loginError");
            if (loginError != null) {
        %>
            <div class="error-message">
                ${loginError}
            </div>
        <%
            }
        %>

        <form action="${pageContext.request.contextPath}/member/login" method="post" class="space-y-6">
            
            <!-- ID (학번) -->
            <div>
                <label for="studentId" class="block text-sm font-semibold text-gray-700 mb-1">학번 (ID)</label>
                <input type="text" id="studentId" name="studentId" required 
                       class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-indigo-600 focus:border-indigo-600 transition duration-150"
                       placeholder="학번을 입력하세요"
                       value="${param.studentId}">
            </div>

            <!-- 비밀번호 -->
            <div>
                <label for="password" class="block text-sm font-semibold text-gray-700 mb-1">비밀번호</label>
                <input type="password" id="password" name="password" required
                       class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-indigo-600 focus:border-indigo-600 transition duration-150"
                       placeholder="비밀번호를 입력하세요">
                <!-- 로그인 실패 시 비밀번호는 비워두는 것이 일반적입니다. -->
            </div>
            
            <!-- 로그인 버튼 -->
            <button type="submit"
                    class="w-full py-3 px-4 bg-indigo-600 text-white font-bold rounded-lg shadow-lg hover:bg-indigo-700 transition duration-200 focus:outline-none focus:ring-4 focus:ring-indigo-500 focus:ring-opacity-50 transform hover:scale-[1.01]">
                로그인
            </button>
        </form>
        
        <div class="text-center text-sm text-gray-500 pt-6 border-t border-gray-100">
            계정이 없으신가요? 
            <a href="${pageContext.request.contextPath}/member/signup" class="text-indigo-600 hover:text-indigo-500 font-bold">
                회원가입
            </a>
        </div>
    </div>

</body>
</html>