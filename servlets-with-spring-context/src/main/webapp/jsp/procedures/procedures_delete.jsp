<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:mainLayout title="Procedure delete" css="form_default.css">
    <main>
        <div class="form-container">
            <div class="form-header">Удалить услугу:</div>
            <form id="crud-form" method="post" action="<c:url value="/procedures/delete"/> ">
                <div class="form-item vertical-container">
                    <label class="input-label" for="procedureId">Id:</label>
                    <input type="number" id="procedureId" name="procedureId">
                </div>
                <button type="submit" id="submit-button">Удалить</button>
            </form>
            <c:if test="${errors != null}">
                <div class="server-valid-errors">${errors}</div>
            </c:if>
        </div>
    </main>
</t:mainLayout>
