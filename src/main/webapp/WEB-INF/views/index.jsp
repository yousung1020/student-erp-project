<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <style>
        /* 모든 탭 컨텐츠를 기본적으로 숨김 */
        #cert-mgmt-sub-content .internal-content {
            display: none;
        }
        /* 체크된 라디오 버튼에 연결된 탭 컨텐츠만 보여줌 */
        #sub-tab-my-certs:checked ~ #cert-mgmt-sub-content #my-certs-list {
            display: block;
        }
        #sub-tab-search-certs:checked ~ #cert-mgmt-sub-content #cert-search-area {
            display: block;
        }
        #sub-tab-add-cert:checked ~ #cert-mgmt-sub-content #add-cert-area {
            display: block;
        }
    </style>
</head>
<jsp:include page="/WEB-INF/views/common/header.jsp" />

  <section id="main">
    <input type="radio" name="main-tab" id="tab-cert-mgmt" class="tab-radio" checked>
    <input type="radio" name="main-tab" id="tab-job-postings" class="tab-radio">
    
    <div class="tab-group">
        <label for="tab-cert-mgmt" class="tab-label">📄 자격증 관리</label>
        <label for="tab-job-postings" class="tab-label">🏢 채용 공고</label>
    </div>

    <div class="clear"></div>

    <div id="cert-mgmt-content" class="content-section">
        
        <input type="radio" name="sub-tab" id="sub-tab-my-certs" class="tab-radio" checked>
        <input type="radio" name="sub-tab" id="sub-tab-search-certs" class="tab-radio">
        <input type="radio" name="sub-tab" id="sub-tab-add-cert" class="tab-radio">
		<input type="radio" name="sub-tab" id="sub-tab-recommend-certs" class="tab-radio">
        <div id="cert-mgmt-sub-content">
        
            <div class="sub-tab-group">
                <label for="sub-tab-my-certs" class="sub-tab-label">내 자격증</label>
                <label for="sub-tab-search-certs" class="sub-tab-label">자격증 검색</label>
                <label for="sub-tab-recommend-certs" class="sub-tab-label">추천 자격증</label>
                <label for="sub-tab-add-cert" class="sub-tab-label">+ 자격증 추가</label>
            </div>
            <c:choose>
            	<c:when test="${not empty loginMember}">
                    <jsp:include page="/WEB-INF/views/main/my_certificates.jsp" />
                    <jsp:include page="/WEB-INF/views/main/certificate_search.jsp" />
                    <jsp:include page="/WEB-INF/views/main/recommend_certificates.jsp"/>
                    <jsp:include page="/WEB-INF/views/main/add_certificate_form.jsp" />

				</c:when>
            	<c:otherwise>
            		<p style="text-align: center; color:#666; padding: 30px;">
                    	로그인을 하지 않은 상태입니다. 로그인해주세요.
                    </p>
            	</c:otherwise>
			</c:choose>
        </div>
    </div>
        
    <jsp:include page="/WEB-INF/views/main/job_postings.jsp" />
  </section> 

<jsp:include page="/WEB-INF/views/common/footer.jsp" />