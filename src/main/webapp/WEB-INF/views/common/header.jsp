<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title> 학과별 자격증 & 취업 정보 </title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
  
  <div id="top-footer-placeholder">
    <div id="top-footer-content">
        <p>개발팀: Team CHK</p>
    </div>
  </div>
  <header>
    <section id="top">
      <div id="app-header">
        <div id="app-header-title">
            학과별 자격증 & 취업 정보
        </div>
        <div id="app-user-info">
            <c:choose>
                <%-- 로그인 상태일 때 --%>
                <c:when test="${not empty loginMember && not empty sessionScope.loggedMemberId}">
                    <span>
                        <a href="${pageContext.request.contextPath}/member/my-page">
                            ${loginMember.memberName}님
                        </a>
                    </span>
                    <span>
                        <a href="${pageContext.request.contextPath}/member/my-page">
                            ${loginMember.deptName}
                        </a>
                    </span>
                    <span>
                        <a href="${pageContext.request.contextPath}/member/logout">로그아웃</a>
                    </span>
                </c:when>
                <%-- 로그아웃 상태일 때 --%>
                <c:otherwise>
                    <span>
                        <a href="${pageContext.request.contextPath}/member/login">로그인</a>
                    </span>

                    <a href="${pageContext.request.contextPath}/member/signup">회원가입</a>
                </c:otherwise>
            </c:choose>
        </div>
      </div>
      <div class="clear"></div>
     </section>
  </header> 
