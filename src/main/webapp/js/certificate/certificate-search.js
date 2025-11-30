$(document).ready(function() {
    
    function executeSearch() {
        let keyword = $('#cert-search-input').val();
        let resultsContainer = $('#search-results-list');

        if (keyword.length > 0) {
            console.log("Searching for keyword:", keyword);

            $.ajax({
                url: contextPath + '/api/certificates/search',
                type: 'GET',
                data: { certKeyword: keyword },
                dataType: 'json',
                success: function(data) {
                    console.log("Data received:", data);
                    resultsContainer.empty();

                    if (data.length > 0) {
                        let resultHtml = `<p style="margin-bottom: 20px;">'${keyword}' 검색 결과 | 총 ${data.length}건의 자격증을 찾았습니다.</p>`;
                        resultHtml += '<div class="cert-card-container">';

                        $.each(data, function(index, cert) {
                            let summary = cert.certSummary ? cert.certSummary : (cert.certTrend ? cert.certTrend : '요약 정보가 없습니다.');
                            
                            resultHtml += `
                                <div class="cert-card">
                                    <h4>${cert.certName}</h4>
                                    <p>${summary}</p>
                                    <div class="card-footer">
                                        <a href="#" class="detail-button" style="background-color: #1a73e8;">내 자격증에 추가</a>
                                    </div>
                                </div>
                            `;
                        });

                        resultHtml += '</div>';
                        resultsContainer.append(resultHtml);
                    } else {
                        resultsContainer.append('<p>검색 결과가 없습니다.</p>');
                    }
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    console.error("AJAX Error:", textStatus, errorThrown);
                    resultsContainer.empty().append('<p>검색 중 오류가 발생했습니다.</p>');
                }
            });
        }
    }

    // '검색' 버튼 클릭 시 검색 실행
    $(document).on('click', '#cert-search-button', function() {
        executeSearch();
    });

    // 검색 input에서 엔터 키를 눌렀을 때 검색 실행
    $('#cert-search-input').on('keyup', function(event) {
        if (event.key === 'Enter' || event.keyCode === 13) {
            executeSearch();
        }
    });

    // --- 다른 탭을 클릭했을 때, 검색 결과를 지우는 로직 추가 ---
    $('label[for="sub-tab-my-certs"], label[for="sub-tab-add-cert"]').on('click', function() {
        $('#search-results-list').empty(); // 결과 목록 비우기
        $('#cert-search-input').val(''); // 검색창 입력값 비우기
    });
});
