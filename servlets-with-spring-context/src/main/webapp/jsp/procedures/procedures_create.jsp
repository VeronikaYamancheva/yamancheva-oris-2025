<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:mainLayout title="Procedure creation" css="form_default.css">
    <main>
        <div class="form-container">
            <div class="form-header">Создать услугу:</div>
            <form id="crud-form" method="post" action="<c:url value="/procedures/create"/> ">
                <div class="form-item vertical-container">
                    <label class="input-label" for="name">Название:</label>
                    <input type="text" id="name" name="name">
                </div>
                <div class="form-item vertical-container">
                    <label class="input-label" for="description">Описание:</label>
                    <input type="text" id="description" name="description">
                </div>
                <button type="submit" id="submit-button">Создать</button>
            </form>
            <c:if test="${errors != null}">
                <div class="server-valid-errors">${errors}</div>
            </c:if>
        </div>
    </main>
</t:mainLayout>

