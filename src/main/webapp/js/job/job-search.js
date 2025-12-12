$(document).ready(function() {
    
    // 검색 실행 로직을 별도의 함수로 분리
    function executeSearch(pageNo) {
        let keyword = $('#job-search-select').val();
        let resultsContainer = $('#job-results-area');

        if (!keyword) {
            resultsContainer.empty().append('<p>검색할 자격증을 선택해주세요.</p>');
            return;
        }

        // 로딩 메시지 표시
        resultsContainer.html('<p>검색 중...</p>');

        $.ajax({
            url: contextPath + '/api/job/search',
            type: 'GET',
            data: { 
                jobSearchKeyword: keyword,
                pageNo: pageNo
            },
            dataType: 'json',
            success: function(response) {
                resultsContainer.empty();

                if (response && response.jobList && response.jobList.length > 0) {
                    let resultHtml = `<p style="margin-bottom: 20px;">'${keyword}' 검색 결과 | 총 ${response.totalJobPostings}건의 채용 공고를 찾았습니다.</p>`;
                    resultHtml += '<div class="job-card-container">';

                    $.each(response.jobList, function(index, job) {
                        resultHtml += `
                            <a href="${job.url}" target="_blank" title="새 창에서 열기" class="job-card">
                                <h4>${job.title}</h4>
                                <p>${job.companyName}</p>
                                <div class="card-footer">
                                    <p>${job.field || ''}</p>
                                </div>
                            </a>
                        `;
                    });

                    resultHtml += '</div>';
                    
                    // 페이지네이션 HTML 생성
                    let paginationHtml = '<div class="pagination">';
                    if (response.currentPage > 1) {
                        paginationHtml += `<a href="#" class="page-link" data-page-no="${response.currentPage - 1}">이전</a>`;
                    }
                    paginationHtml += `
                        <form id="page-jump-form" style="display: inline-block;">
                            <span>
                                <input type="number" id="pageInput" value="${response.currentPage}" min="1" max="${response.totalPages}" style="width: 60px; text-align: center;">
                                / ${response.totalPages}
                            </span>
                            <button type="submit" id="jumpToPageBtn">이동</button>
                        </form>
                    `;
                    if (response.currentPage < response.totalPages) {
                        paginationHtml += `<a href="#" class="page-link" data-page-no="${response.currentPage + 1}">다음</a>`;
                    }
                    paginationHtml += '</div>';

                    resultsContainer.append(resultHtml).append(paginationHtml);

                } else {
                    resultsContainer.append('<p>검색 결과가 없습니다.</p>');
                }
            },
            error: function() {
                resultsContainer.empty().append('<p>검색 중 오류가 발생했습니다.</p>');
            }
        });
    }

    // '직무 검색' 버튼 클릭 시 항상 1페이지로 검색 실행
    $(document).on('click', '#job-search-button', function() {
        executeSearch(1);
    });

    // '이전/다음' 링크 클릭 시 해당 페이지로 검색 실행
    $(document).on('click', '.page-link', function(e) {
        e.preventDefault();
        let pageNo = $(this).data('page-no');
        executeSearch(pageNo);
    });

    // '이동' 버튼 클릭 또는 페이지 입력창에서 엔터 시 해당 페이지로 검색 실행
    $(document).on('submit', '#page-jump-form', function(e) {
        e.preventDefault();
        let pageNo = $('#pageInput').val();
        executeSearch(pageNo);
    });
});