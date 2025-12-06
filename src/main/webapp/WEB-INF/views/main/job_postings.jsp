<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div id="job-postings-content" class="content-section">
    <h2>🏢 자격증 기반 채용 공고 검색</h2>
    
    <div id="job-search-input-area" class="search-input-group">
        <input type="text" id="job-search-input" placeholder="검색할 자격증을 입력하여 관련 직무를 검색하세요">
        <button type="button" id="job-search-button">🔍 직무 검색</button>
    </div>
    
    <%-- 크롤링 결과가 동적으로 표시될 영역 --%>
    <div id="job-results-area" class="internal-content">
        <%-- 초기에는 비워둠 --%>
    </div>
</div>
