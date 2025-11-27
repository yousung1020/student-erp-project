<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="my-certs-list" class="internal-content">
    <p style="color:#888;">"${loginMember.deptName}"에 필요한 자격증을 확인하고 관리하세요.</p>

    <div class="cert-card-container">
        <c:choose>
            <c:when test="${not empty myCertList}">
                <c:forEach var="cert" items="${myCertList}">
                    <div class="cert-card">
                        <h4>${cert.certName}</h4>
                        <p>${cert.certSummary}</p>
                        <div class="card-footer">
                            <p style="margin-bottom:5px;">취득 일자 : ${cert.certDate}</p>
                        </div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <p style="text-align: center; color:#666; padding: 30px;">
                    등록된 자격증 정보가 없습니다. 자격증을 추가하세요.
                </p>
            </c:otherwise>
        </c:choose>
    </div>
</div>