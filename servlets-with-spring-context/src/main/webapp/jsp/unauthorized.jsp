<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<t:mainLayout title="Unauthorized" css="unauthorized.css">
    <div class="un-auth-container">
        <div class="horizontal-container">
            <img id="un-auth-img" src="<c:url value="/img/unauthorized-img.png"/>" alt="some-img"/>
            <div class="vertical-container">
                <div class="message">
                    Плаки-плаки, это страничка только для авторизованных пользователей.
                </div>
                <h3 class="auth-redirect">Хочу <a href="<c:url value="/sign_in"/>">авторизоваться</a> </h3>
                <h3 class="auth-redirect">Ну и ладно, хочу на <a href="<c:url value="/"/>">главную страницу</a> </h3>
            </div>
        </div>
    </div>
</t:mainLayout>