<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
            
            <!-- ID (학번) -->
            <div>
                <label for="studentId" class="block text-sm font-semibold text-gray-700 mb-1">학번 (ID)</label>
                <input type="text" id="studentId" name="studentId" required 
                       class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-indigo-600 focus:border-indigo-600 transition duration-150"
                       placeholder="예: 20241234"
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
                <input type="password" id="password" name="password" required
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
                <input type="text" id="name" name="name" required
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
            <div class="relative">
                <label for="departmentName" class="block text-sm font-semibold text-gray-700 mb-1">학과 (검색)</label>
                <!-- 사용자가 검색할 입력 필드 -->
                <input type="text" id="departmentName" required
                       class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-indigo-600 focus:border-indigo-600 transition duration-150"
                       placeholder="학과를 검색하거나 입력하세요"
                       value="${param.departmentName}">
                
                <!-- 선택된 학과 ID를 서버에 제출하기 위한 숨겨진 필드 -->
                <input type="hidden" id="departmentId" name="department" required value="${param.department}">
                
                <!-- 자동 완성 제안 목록이 표시될 영역 -->
                <div id="department-suggestions" class="absolute w-full bg-white shadow-xl rounded-b-lg border-gray-300" style="display: none;">
                    <!-- JavaScript로 목록이 여기에 추가됩니다. -->
                </div>
                <!-- 🟥 학과 선택 오류 표시 (서버에서 받은 경우) -->
                <% if (errorType != null && errorType.equals("DEPARTMENT_ERROR")) { %>
                    <div class="error-message">
                        <%= errorMessage %>
                    </div>
                <% } %>
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

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const deptInput = document.getElementById('departmentName');
            const deptIdHidden = document.getElementById('departmentId');
            const suggestionsContainer = document.getElementById('department-suggestions');
            const form = document.getElementById('signupForm');
            let allDepartments = [];
            let currentFocus = -1;

            const passwordInput = document.getElementById('password');
            const passwordConfirmInput = document.getElementById('passwordConfirm');
            const passwordConfirmErrorDiv = document.getElementById('password-confirm-error');

            // --- 1. Client-Side Password Validation (비밀번호 불일치 체크) ---
            function validatePasswords() {
                // 두 필드가 모두 값이 있을 때만 비교
                if (passwordInput.value && passwordConfirmInput.value) {
                    if (passwordInput.value !== passwordConfirmInput.value) {
                        passwordConfirmErrorDiv.textContent = "비밀번호가 일치하지 않습니다. 다시 확인해주세요.";
                        return false;
                    } else {
                        passwordConfirmErrorDiv.textContent = "";
                        return true;
                    }
                }
                // 두 입력 필드가 모두 채워지지 않았다면, 오류 메시지를 표시하지 않고 통과
                passwordConfirmErrorDiv.textContent = "";
                return true;
            }

            passwordInput.addEventListener('input', validatePasswords);
            passwordConfirmInput.addEventListener('input', validatePasswords);

            // --- 2. Form Submission Validation (폼 제출 시 유효성 최종 확인) ---
            form.addEventListener('submit', (e) => {
                // 1) 비밀번호 일치 여부 최종 확인
                if (!validatePasswords()) {
                    e.preventDefault();
                    passwordConfirmInput.focus();
                    return;
                }
                
                // 2) 학과 선택 여부 확인
                // deptInput.value는 사용자가 눈으로 본 학과 이름 (예: "컴퓨터공학과")
                // deptIdHidden.value는 DB에 저장될 학과 ID (예: 1, 2, 3...)
                if (deptInput.value && !deptIdHidden.value) {
                    e.preventDefault();
                    // Custom Alert 대신 오류 메시지를 표시하도록 변경
                    alert("학과를 목록에서 정확하게 선택해주세요.");
                    deptInput.focus();
                    return;
                }

                // 비밀번호가 일치하고 학과 ID가 설정되었으면 서버로 제출
            });
            
            // --- 3. Department AJAX and Filtering Logic (학과 드롭다운/필터링) ---
            
            async function fetchDepartments() {
                try {
                    const response = await fetch('${pageContext.request.contextPath}/api/departments');
                    if (!response.ok) {
                        throw new Error('Failed to fetch departments: ' + response.statusText);
                    }
                    allDepartments = await response.json();
                    console.log('Departments loaded:', allDepartments);
                } catch (error) {
                    console.error("학과 데이터를 가져오는 중 오류 발생:", error);
                }
            }

            function filterAndDisplaySuggestions() {
                const filter = deptInput.value.toUpperCase();
                suggestionsContainer.innerHTML = '';
                currentFocus = -1;

                if (!filter) {
                    suggestionsContainer.style.display = 'none';
                    return;
                }

                let matchCount = 0;
                allDepartments.forEach((dept) => {
                    if (dept.deptName.toUpperCase().includes(filter)) {
                        if (matchCount < 10) {
                            const item = document.createElement('div');
                            item.innerHTML = dept.deptName;
                            item.classList.add('suggestion-item', 'hover:bg-gray-100', 'text-gray-700', 'border-b', 'border-gray-100');
                            item.setAttribute('data-dept-id', dept.deptId);
                            item.setAttribute('data-dept-name', dept.deptName);

                            item.addEventListener('click', () => {
                                selectSuggestion(item);
                            });

                            suggestionsContainer.appendChild(item);
                            matchCount++;
                        }
                    }
                });

                suggestionsContainer.style.display = suggestionsContainer.children.length > 0 ? 'block' : 'none';
            }

            function selectSuggestion(item) {
                const deptId = item.getAttribute('data-dept-id');
                const deptName = item.getAttribute('data-dept-name');

                deptInput.value = deptName;
                deptIdHidden.value = deptId;
                
                suggestionsContainer.style.display = 'none';
            }

            deptInput.addEventListener('input', () => {
                // 사용자가 텍스트를 입력할 때, 숨겨진 ID 값 초기화
                deptIdHidden.value = '';
                filterAndDisplaySuggestions();
            });

            deptInput.addEventListener('blur', () => {
                setTimeout(() => {
                    suggestionsContainer.style.display = 'none';
                }, 150);
            });
            
            deptInput.addEventListener('focus', () => {
                if (deptInput.value) {
                   filterAndDisplaySuggestions();
                }
            });
            
            // Focus 아웃 시, 검색 필드에 입력된 텍스트가 DB에 등록된 학과 이름과 일치하는지 확인
            // (이 로직은 복잡해지므로, 일단 사용자가 목록에서 선택하도록 강제하는 것으로 유지합니다.)

            // 키보드 탐색 기능 (이전 코드 유지)
            deptInput.addEventListener("keydown", function(e) {
                let x = suggestionsContainer.getElementsByClassName("suggestion-item");
                if (e.key === "ArrowDown") {
                    currentFocus++;
                    addActive(x);
                } else if (e.key === "ArrowUp") {
                    currentFocus--;
                    addActive(x);
                } else if (e.key === "Enter") {
                    e.preventDefault();
                    if (currentFocus > -1) {
                        if (x) x[currentFocus].click();
                    }
                }
            });

            function removeActive(x) {
                for (let i = 0; i < x.length; i++) {
                    x[i].classList.remove("selected", "bg-cyan-100", "font-semibold");
                }
            }

            function addActive(x) {
                if (!x || x.length === 0) return false;
                removeActive(x);
                if (currentFocus >= x.length) currentFocus = 0;
                if (currentFocus < 0) currentFocus = (x.length - 1);
                x[currentFocus].classList.add("selected", "bg-cyan-100", "font-semibold");
                x[currentFocus].scrollIntoView({ block: "nearest", behavior: "smooth" });
            }

            fetchDepartments();
        });
    </script>

</body>
</html>