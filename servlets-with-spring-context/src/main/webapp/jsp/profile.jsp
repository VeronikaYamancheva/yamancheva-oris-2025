<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:mainLayout title="Profile" css="profile.css" js="profile.js">
    <main>
        <div class="horizontal-container">
            <div id="profile-container" class="vertical-container">
                <div class="form-header">Профиль:</div>

                <form id="profileForm" method="post" action="<c:url value="/profile"/>">
                    <div class="form-item">
                        <div class="form-attr">Имя:</div>
                        <span class="text input-label">${client.firstName}</span>
                        <input type="text" name="firstName" value="${client.firstName}" class="input"
                               style="display:none;"/>
                    </div>
                    <div class="form-item">
                        <div class="form-attr">Фамилия:</div>
                        <span class="text input-label">${client.lastName}</span>
                        <input type="text" name="lastName" value="${client.lastName}" class="input"
                               style="display:none;"/>
                    </div>
                    <div class="form-item">
                        <div class="form-attr">Email:</div>
                        <span class="input-label">${client.email}</span>
                    </div>
                    <div class="form-item">
                        <div class="form-attr">NickName:</div>
                        <span class="input-label">${client.nickname}</span>
                    </div>

                    <button type="button" id="editButton" onclick="profileEdit()">Редактировать</button>
                </form>
                <div class="server-valid-errors">${errors != null ? errors : ''}</div>
                <a class="redirect-link" href="<c:url value="/sign_out"/> ">Выйти из профиля</a>
            </div>
            <div id="client-appointments" class="vertical-container">
                <div class="form-header">Мои записи:</div>
                <c:forEach var="appointmentMap" items="${appointments}">
                    <c:forEach var="entry" items="${appointmentMap}">
                        <div class="appointment-container">
                            <div class="appointment-date">Дата: ${entry.key.date}</div>
                            <div class="appointment-time">Время: ${entry.key.time}</div>
                            <c:forEach var="procedure" items="${entry.value}">
                                <li>${procedure.name}</li>
                            </c:forEach>
                        </div>
                    </c:forEach>
                </c:forEach>
                <a class="redirect-link" href="<c:url value="/create_appointment"/> ">Создать новую запись</a>
            </div>
        </div>
    </main>
</t:mainLayout>
