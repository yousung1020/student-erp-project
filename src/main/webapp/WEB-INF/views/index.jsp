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
    <div id="job-postings-content" class="content-section">
    	<h2>🏢 자격증 기반 채용 공고 검색</h2>
    
    	<div id="job-search-input-area" class="search-input-group">
        	<input type="text" placeholder="보유 자격증을 입력하여 관련 직무를 검색하세요 (예: 정보처리기사, AWS SA)">
        	<button id="search-job-btn">🔍 직무 검색</button>
    	</div>
    
    	<div id="job-results-area" class="internal-content">
        	<p style="margin-bottom: 20px;">'정보처리기사' 관련 직무 | 총 15건의 채용 공고를 찾았습니다.</p>
        
        	<div id="recommended-role">
            	<h3 style="color: #1a73e8; margin-bottom: 5px;">추천 직무: 소프트웨어 개발자 (백엔드)</h3>
            	<p style="font-size: 14px; color: #666; margin-bottom: 15px;">정보시스템 구축 및 데이터 관리에 대한 이해가 깊어, 서버/데이터베이스 연동 업무에 적합합니다.</p>
        	</div>
        
        	<div class="job-list-container">
            
            	<div class="job-card">
                	<span class="job-card-tag">신입/경력</span>
                	<h4>(주)퓨처테크 백엔드 개발자 채용</h4>
                	<p>#Spring #Java #MySQL. 인공지능 기반 플랫폼 개발 참여.</p>
                	<div class="card-footer">
                    	<p>마감일: 2025.12.31</p>
                    	<p style="margin-bottom: 10px;">근무지: 서울 강남</p>
                    	<a href="#" class="detail-button" style="background-color: #007bff;">공고 상세 보기</a>
                	</div>
            	</div>
            
            	<div class="job-card">
                	<span class="job-card-tag" style="background-color: #fff3cd; color: #664d03;">경력직</span>
                	<h4>한국데이터 솔루션 정보시스템 구축 전문가</h4>
                	<p>대규모 정보시스템 설계 및 통합 테스트 경력자 모집.</p>
                	<div class="card-footer">
                    	<p>마감일: 채용 시 마감</p>
                    	<p style="margin-bottom: 10px;">근무지: 경기도 판교</p>
                    	<a href="#" class="detail-button" style="background-color: #007bff;">공고 상세 보기</a>
                	</div>
            	</div>
            
            </div>
    	</div>
	</div>
  </section>