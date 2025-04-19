<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:mainLayout title="Sign out" css="sign.css">
    <div class="sign-container">
        <div class="horizontal-container">
            <img id="logout-img" src="<c:url value="/img/sign-out-img.png"/>" alt="some-img"/>
            <div class="vertical-container">
                <form id="logonForm" action="<c:url value="/sign_out"/>" method="post">
                    <div class="sign-header">Выход</div>

                    <div class="sign-text">
                        Вы действительно хотите выйти с самого лучшего сайта на свете???
                    </div>
                    <button id="submit-button" type="submit">Выйти</button>
                </form>
                <h3 class="sign-redirect">Нет, хочу на <a href="<c:url value="/"/>">главную страницу</a> </h3>
            </div>
        </div>
    </div>
</t:mainLayout>