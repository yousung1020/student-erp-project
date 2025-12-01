$(document).ready(function() {
    
    function executeSearch() {
        let keyword = $('#cert-search-input').val();
        let resultsContainer = $('#search-results-list');

        if (keyword.length > 0) {
            $.ajax({
                url: contextPath + '/api/certificates/search',
                type: 'GET',
                data: { certKeyword: keyword },
                dataType: 'json',
                success: function(data) {
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
                                
                                        <a href="#" class="detail-button add-my-cert-button" 
                                           style="background-color: #1a73e8;"
                                           data-cert-id="${cert.certId}"
                                           data-cert-name="${cert.certName}">내 자격증에 추가</a>
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

    // '검색' 버튼 클릭 및 엔터 키 이벤트
    $(document).on('click', '#cert-search-button', executeSearch);
    $('#cert-search-input').on('keyup', function(event) {
        if (event.key === 'Enter' || event.keyCode === 13) {
            executeSearch();
        }
    });

    // 다른 탭 클릭 시 검색 결과 초기화
    $('label[for="sub-tab-my-certs"], label[for="sub-tab-add-cert"]').on('click', function() {
        $('#search-results-list').empty();
        $('#cert-search-input').val('');
    });

    // "내 자격증에 추가" 버튼 클릭 이벤트 핸들러
    $(document).on('click', '.add-my-cert-button', function(e) {
        e.preventDefault();

        let certId = $(this).data('cert-id');
        let certName = $(this).data('cert-name');

        // '자격증 추가' 탭으로 전환
        $('#sub-tab-add-cert').prop('checked', true);

        // Select2 드롭다운의 값을 변경하고, 화면에 반영되도록 'change' 이벤트를 발생시킴
        $('#add-cert-id').val(certId).trigger('change');
        
        // (선택사항) 폼으로 스크롤
        document.getElementById('add-cert-area').scrollIntoView({ behavior: 'smooth' });
    });
});
