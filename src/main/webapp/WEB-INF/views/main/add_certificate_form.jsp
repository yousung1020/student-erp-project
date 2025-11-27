<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- "자격증 추가" 폼 섹션 --%>
<div id="add-cert-area" class="internal-content">
    <form action="${pageContext.request.contextPath}/certificate/add" method="POST">
        <h3>새 자격증 추가</h3>
        <div class="form-group">
            <label for="certName">자격증 이름</label>
            <input type="text" id="certName" name="certName" required>
        </div>
        <div class="form-group">
            <label for="acquisitionDate">취득일</label>
            <input type="date" id="acquisitionDate" name="acquisitionDate">
        </div>
        <div class="form-group">
            <label for="status">상태</label>
            <select id="status" name="status">
                <option value="취득">취득</option>
                <option value="준비">준비</option>
            </select>
        </div>
        <button type="submit">추가하기</button>
    </form>
</div>
