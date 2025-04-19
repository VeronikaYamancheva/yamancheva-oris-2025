<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:mainLayout title="Dentist delete" css="form_default.css">
    <main>
        <div class="form-container">
            <div class="form-header">Удалить стоматолога:</div>
            <form id="crud-form" method="post" action="<c:url value="/dentists/delete"/> ">
                <div class="form-item vertical-container">
                    <label class="input-label" for="dentistId">Id:</label>
                    <input type="number" id="dentistId" name="dentistId">
                </div>
                <button type="submit" id="submit-button">Удалить</button>
            </form>
            <c:if test="${errors != null}">
                <div class="server-valid-errors">${errors}</div>
            </c:if>
        </div>
    </main>
</t:mainLayout>

