<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:mainLayout title="Procedure update" css="form_default.css">
    <main>
        <div class="form-container">
            <div class="form-header">Обновить услугу:</div>

            <form id="crud-form" method="post" action="<c:url value="/procedures/update"/>">
                <div class="form-item vertical-container">
                    <label class="input-label" for="procedureId">Id:</label>
                    <input type="number" id="procedureId" name="procedureId">
                </div>
                <div class="form-item vertical-container">
                    <label class="input-label" for="name">Название:</label>
                    <input type="text" id="name" name="name">
                </div>
                <div class="form-item vertical-container">
                    <label class="input-label" for="description">Описание:</label>
                    <input type="text" id="description" name="description">
                </div>
                <button type="submit" id="submit-button">Обновить</button>
            </form>
            <c:if test="${errors != null}">
                <div class="server-valid-errors">${errors}</div>
            </c:if>
        </div>
    </main>
</t:mainLayout>


