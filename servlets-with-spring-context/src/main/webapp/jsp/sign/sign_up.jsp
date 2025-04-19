<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:mainLayout title="Sign up" css="sign.css" js="sign_up.js">
    <div class="sign-container">
        <div class="horizontal-container">
            <img id="login-img" src="<c:url value="/img/sign-up-img.jpg" />" alt="sign up img"/>
            <div class="vertical-container">
                <div class="sign-header">Регистрация</div>

                <form id="loginForm" action="<c:url value="/sign_up"/>" method="post">
                    <div class="form-item">
                        <label for="email" class="input-label">Email:</label>
                        <input type="email" id="email" name="email" value="${email != null ? email : ''}"/>
                    </div>
                    <div class="form-item">
                        <label for="nickname" class="input-label">NickName:</label>
                        <input type="text" id="nickname" name="nickname" value="${nickname != null ? nickname : ''}"/>
                    </div>
                    <div class="form-item">
                        <label for="password" class="input-label">Пароль:</label>
                        <input type="password" id="password" name="password" value="${password != null ? password : ''}"/>
                    </div>
                    <div class="form-item">
                        <label for="cpassword" class="input-label">Подтверждающий пароль:</label>
                        <input type="password" id="cpassword" name="cpassword" value="${cpassword != null ? cpassword : ''}"/>
                    </div>
                    <div class="form-item">
                        <label for="adminCode" class="input-label">Код администратора:</label>
                        <input type="password" id="adminCode" name="adminCode" value="${adminCode != null ? adminCode : ''}"/>
                    </div>
                    <button id="submit-button" type="submit">Зарегистрироваться</button>
                </form>
                <h3 class="sign-redirect">Уже есть аккаунт? <a href="<c:url value="/sign_in"/> ">Войти</a> </h3>
            </div>
        </div>
    </div>
    <c:if test="${errors != null}">
        <div class="server-valid-errors">${errors}</div>
    </c:if>
</t:mainLayout>