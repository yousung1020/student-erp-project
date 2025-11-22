<%--
  Created by IntelliJ IDEA.
  User: shootingstar
  Date: 25. 11. 21.
  Time: 오후 2:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>로그인 페이지</title>
</head>
<body>
<div>
    <h1>로그인 페이지</h1>

    <form method="POST" action="${pageContext.request.contextPath}/member/login">
        <%-- 로그인 실패 시 컨트롤러가 보낸 에러 메세지 표시 --%>
        <c:if test="${not empty loginError}">
            <p>${loginError}</p>
        </c:if>

        <input type="text" name="memberId" placeholder="아이디" required />
            <input type="password" name="memberPassword" placeholder="패스워드" required />
        <button type="submit">로그인</button>
    </form>
</div>
</body>
</html>
