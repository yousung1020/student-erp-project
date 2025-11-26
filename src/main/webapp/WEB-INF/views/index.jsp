<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

        <div id="cert-mgmt-sub-content">
        
            <div class="sub-tab-group">
                <label for="sub-tab-my-certs" class="sub-tab-label">내 자격증</label>
                <label for="sub-tab-search-certs" class="sub-tab-label">자격증 검색</label>
                <button class="add-cert-btn">+ 자격증 추가</button>
            </div>
            <c:choose>
            	<c:when test="${not empty loginMember}">
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

            	<div id="cert-search-area" class="internal-content">
                	<div class="search-input-group">
                    	<input type="text" placeholder="자격증 이름을 검색하세요 (예: 정보처리기사, ADsP)">
                    	<button>🔍 검색</button>
                	</div>
                
                	<div id="search-results-list">
                    	<p style="margin-bottom: 20px;">'정보' 검색 결과 (임시) | 총 2건의 자격증을 찾았습니다.</p>
                    
                    	<div class="cert-card-container">
                        	<div class="cert-card">
                            	<span class="search-result-tag">국가기술</span>
                            	<h4>정보처리기사</h4>
                            	<p>컴퓨터 시스템 개발 및 운용에 필요한 전문 지식을 검증합니다.</p>
                            	<div class="card-footer">
                                	<a href="#" class="detail-button" style="background-color: #1a73e8;">내 자격증에 추가</a>
                            	</div>
                        	</div>

                        	<div class="cert-card">
                            	<span class="search-result-tag">국가기술</span>
                            	<h4>정보보안기사</h4>
                            	<p>시스템 및 네트워크 보안 전문가로서의 능력을 인증합니다.</p>
                            	<div class="card-footer">
                                	<a href="#" class="detail-button" style="background-color: #1a73e8;">내 자격증에 추가</a>
                            	</div>
                        	</div>
                    	</div>
                	</div>
            	</div>
				</c:when>
            	<c:otherwise>
            		<p style="text-align: center; color:#666; padding: 30px;">
                    	로그인을 하지 않은 상태입니다. 로그인해주세요.
                    </p>
            	</c:otherwise>
			</c:choose>
        </div> </div> <div id="job-postings-content" class="content-section">
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

<jsp:include page="/WEB-INF/views/common/footer.jsp" />