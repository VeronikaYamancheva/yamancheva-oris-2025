<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:mainLayout title="Dentist creation" css="form_default.css">
    <main>
        <div class="form-container">
            <div class="form-header">Создать стоматолога:</div>
            <form id="crud-form" method="post" action="<c:url value="/dentists/create"/> ">
                <div class="form-item vertical-container">
                    <label class="input-label" for="firstName">Имя:</label>
                    <input type="text" id="firstName" name="firstName">
                </div>
                <div class="form-item vertical-container">
                    <label class="input-label" for="lastName">Фамилия:</label>
                    <input type="text" id="lastName" name="lastName">
                </div>
                <div class="form-item vertical-container">
                    <label class="input-label" for="email">Email:</label>
                    <input type="email" id="email" name="email">
                </div>
                <button type="submit" id="submit-button">Создать</button>
            </form>
            <c:if test="${errors != null}">
                <div class="server-valid-errors">${errors}</div>
            </c:if>
        </div>
    </main>
</t:mainLayout>

