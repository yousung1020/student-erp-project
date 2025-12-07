<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<style>
    /* 카드 컨테이너를 2열 Flexbox 레이아웃으로 설정 */
    .job-card-container {
        display: flex;
        flex-wrap: wrap; /* 카드가 넘치면 다음 줄로 이동 */
        gap: 20px; /* 카드 사이의 간격 */
    }
    /* 각 카드의 너비를 50%에서 간격의 절반만큼 빼서 정확히 2열로 만듦 */
    .job-card-container .job-card {
        width: calc(50% - 10px);
        box-sizing: border-box; /* 패딩과 테두리를 너비에 포함 */
    }
    /* 페이지네이션 UI 스타일 */
    .pagination {
        text-align: center;
        margin-top: 20px;
    }
    .pagination a, .pagination span, .pagination input, .pagination button {
        display: inline-block;
        margin: 0 5px;
        padding: 5px 10px;
        border: 1px solid #ddd;
        border-radius: 4px;
        vertical-align: middle;
    }
    .pagination a {
        text-decoration: none;
        color: #007bff;
    }
    .pagination a:hover {
        background-color: #f0f0f0;
    }
    .pagination span {
        border: none;
    }
</style>

<div id="job-postings-content" class="content-section">
    <h2>🏢 자격증 기반 채용 공고 검색</h2>
    
    <div class="search-input-group">
        <select id="job-search-select" style="width: calc(100% - 100px);">
            <option value="">검색할 자격증을 선택하세요</option>
            <c:forEach var="cert" items="${certificates}">
                <option value="${cert.certName}" ${cert.certName == searchKeyword ? 'selected' : ''}>${cert.certName}</option>
            </c:forEach>
        </select>
        <button type="button" id="job-search-button">🔍 직무 검색</button>
    </div>
    
    <div id="job-results-area" class="internal-content">
        <c:if test="${not empty jobSearchResult and not empty jobSearchResult.jobList}">
            <p style="margin-bottom: 20px;">'${searchKeyword}' 검색 결과 | 총 ${jobSearchResult.totalJobPostings}건의 채용 공고를 찾았습니다.</p>
            
            <div class="job-card-container">
                <c:forEach var="job" items="${jobSearchResult.jobList}">
                    <div class="job-card">
                        <h4><a href="${job.url}" target="_blank" title="새 창에서 열기">${job.title}</a></h4>
                        <p>${job.companyName}</p>
                        <div class="card-footer">
                            <p>${job.field}</p>
                        </div>
                    </div>
                </c:forEach>
            </div>
            
            <div class="pagination">
                <c:if test="${jobSearchResult.currentPage > 1}">
                    <a href="#" class="page-link" data-page-no="${jobSearchResult.currentPage - 1}">이전</a>
                </c:if>

                <form id="page-jump-form" style="display: inline-block;">
                    <span>
                        <input type="number" id="pageInput" value="${jobSearchResult.currentPage}" min="1" max="${jobSearchResult.totalPages}" style="width: 60px; text-align: center;">
                        / ${jobSearchResult.totalPages}
                    </span>
                    <button type="submit" id="jumpToPageBtn">이동</button>
                </form>

                <c:if test="${jobSearchResult.currentPage < jobSearchResult.totalPages}">
                    <a href="#" class="page-link" data-page-no="${jobSearchResult.currentPage + 1}">다음</a>
                </c:if>
            </div>
        </c:if>
        
        <c:if test="${empty jobSearchResult.jobList && not empty searchKeyword}">
            <p style="text-align: center;">'${searchKeyword}'에 대한 채용 공고를 찾을 수 없습니다.</p>
        </c:if>
    </div>
</div>

<script>
    var contextPath = "${pageContext.request.contextPath}";
</script>
<script src="${pageContext.request.contextPath}/js/job/job-search.js"></script>

<script type="text/javascript">
    $(document).ready(function() {
        $('#job-search-select').select2({
            placeholder: "자격증을 선택하거나 검색하세요",
            allowClear: true
        });
    });
</script>