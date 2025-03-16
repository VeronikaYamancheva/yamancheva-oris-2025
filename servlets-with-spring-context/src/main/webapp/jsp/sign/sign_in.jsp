<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:mainLayout title="Sign In" css="sign.css" js="sign_in.js">
    <div class="sign-container">
        <div class="horizontal-container">
            <img id="logon-img" src="<c:url value="/img/sign-in-img.jpg"/>" alt="some-img"/>
            <div class="vertical-container">
                <form id="logonForm" action="<c:url value="/sign_in"/>" method="post">
                    <div class="sign-header">Вход</div>
                    <div class="form-item">
                        <label for="nickname" class="input-label">Nickname:</label>
                        <input type="text" id="nickname" name="nickname">
                    </div>
                    <div class="form-item">
                        <label for="password" class="input-label">Пароль:</label>
                        <input type="password" id="password" name="password">
                    </div>
                    <button id="submit-button" type="submit">Войти</button>
                </form>
                <h3 class="sign-redirect">Нету аккаунта? <a href="<c:url value="/sign_up"/>">Зарегистрироваться</a> </h3>
            </div>
        </div>
    </div>
    <c:if test="${errors != null}">
        <div class="server-valid-errors">${errors}</div>
    </c:if>
</t:mainLayout>
