<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:mainLayout title="New Appointment" css="form_default.css">
    <main>
        <div class="form-container">
            <div class="form-header">Новая запись:</div>
            <form id="crud-form" method="post" action="<c:url value="/create_appointment"/>">
                <div class="form-item vertical-container vertical-container">
                    <label class="input-label" for="firstName">Имя:</label>
                    <input type="text" id="firstName" name="firstName"
                           value="${client.firstName != null ? client.firstName : ''}" required>
                </div>
                <div class="form-item vertical-container">
                    <label class="input-label" for="lastName">Фамилия:</label>
                    <input type="text" id="lastName" name="lastName"
                           value="${client.lastName != null ? client.lastName : ''}" required>
                </div>
                <div class="form-item vertical-container">
                    <label class="input-label" for="email">Электронная почта:</label>
                    <input type="email" id="email" name="email" value="${client.email}" required>
                </div>

                <div class="form-item">
                    <label class="input-label" for="dentistId">Стоматолог:</label>
                    <select id="dentistId" name="dentistId" required>
                        <option value="" disabled selected>Выберите стоматолога</option>
                        <c:forEach var="dentist" items="${dentists}">
                            <option value="${dentist.id}">${dentist.firstName} ${dentist.lastName}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-item">
                    <label class="input-label" for="appointmentDate">Дата приема:</label>
                    <input type="date" id="appointmentDate" name="appointmentDate" required>
                </div>

                <div class="form-item">
                    <label class="input-label" for="appointmentTime">Дата приема:</label>
                    <input type="time" id="appointmentTime" name="appointmentTime" required>
                </div>

                <div class="">
                    <label class="input-label">Выберите процедуры:</label><br>
                    <c:forEach var="procedure" items="${procedures}">
                        <div class="checkbox-container form-item">
                            <input type="checkbox" name="procedures" value="${procedure.id}"
                                   id="procedure${procedure.id}">
                            <label for="procedure${procedure.id}">${procedure.name}</label>
                        </div>
                    </c:forEach>
                </div>

                <button type="submit" id="submit-button">Записаться</button>
            </form>
            <c:if test="${errors != null}">
                <div class="server-valid-errors">${errors}</div>
            </c:if>
        </div>
    </main>
</t:mainLayout>

