<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="content-recommend-certs" class="internal-content">
	<%-- majorInfo -> info , infoError -> error로 바꿈으로써 가독성 향상 및 간결화 --%>
    <c:set var="info" value="${majorInfo}" />
    <c:set var="error" value="${infoError}" />

    <p style="color:#888;">${loginMember.deptName}에 필요한 직업 및 자격증 추천 정보입니다.</p>
    
    <div class="recommend-area">
        <c:choose>
            <c:when test="${not empty error}">
                <p style="text-align: center; color: red; padding: 30px;">
                    추천 정보를 불러오는 중 오류가 발생했습니다: **${error}**
                </p>
            </c:when>
            <c:when test="${not empty info}">
                
                <h3>📚 ${info.majorName} 학과 관련 정보</h3>
                <hr>
                
                <h4>💼 직업 추천: ${info.majorName} 관련 직업</h4>
                <div class="info-list">
                    <c:choose>
                        <c:when test="${not empty info.relatedJobs && info.relatedJobs != '정보 없음'}">
                            <ul>
                                <c:forTokens var="job" items="${info.relatedJobs}" delims=",">
                                    <li>${job}</li>
                                </c:forTokens>
                            </ul>
                        </c:when>
                        <c:otherwise>
                            <p style="color:#999;">관련 직업 정보가 없습니다.</p>
                        </c:otherwise>
                    </c:choose>
                </div>
                
                <h4 style="margin-top: 25px;">🏅 자격증 추천: 취득을 고려할 만한 자격증</h4>
                <div class="info-list">
                    <c:choose>
                        <c:when test="${not empty info.qualifications && info.qualifications != '정보 없음'}">
                            <ul>
                                <c:forTokens var="qualification" items="${info.qualifications}" delims=",">
                                    <li>${qualification}</li>
                                </c:forTokens>
                            </ul>
                        </c:when>
                        <c:otherwise>
                            <p style="color:#999;">추천 자격증 정보가 없습니다.</p>
                        </c:otherwise>
                    </c:choose>
                </div>
                
            </c:when>
            <c:otherwise>
                <p style="text-align: center; color:#666; padding: 30px;">
                    학과 관련 추천 정보를 불러올 수 없습니다. (데이터 없음)
                </p>
            </c:otherwise>
        </c:choose>
    </div>
</div>