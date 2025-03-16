<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<t:mainLayout title="Dentists" css="crud_read_default.css">
    <main>
        <div class="list-header">Наши врачи:</div>
        <div class="list-container vertical-container">
            <c:forEach var="dentist" items="${dentists}">
                <div class="item-container horizontal-container">
                    <img class="item-image" src="<c:url value="/img/dentist-img.png"/> ">
                    <div class="description-container">
                        <div class="item-name">${dentist.id}. ${dentist.firstName} ${dentist.lastName}</div>
                        <div class="item-desc">Email: ${dentist.email}</div>
                    </div>
                </div>
            </c:forEach>
        </div>
        <div class="central-horizontal-container">
            <c:if test="${currentPage > 1}">
                <a class="crud-link" href="?page=${currentPage - 1}"><span>Назад</span></a>
            </c:if>

            <c:if test="${currentPage < totalPages}">
                <a class="crud-link" href="?page=${currentPage + 1}"><span>Вперед</span></a>
            </c:if>
        </div>

        <p class="rewind-text">Страница ${currentPage} из ${totalPages}</p>

        <c:if test="${isAdmin != null && isAdmin}">
            <div class="admin-panel vertical-container">
                <div class="rewind-text">Ты, кстати, админ, хочешь изменить список процедур?</div>
                <div class="central-horizontal-container">
                    <a class="crud-link" href="<c:url value="/dentists/create"/> "><span>Создать</span></a>
                    <a class="crud-link" href="<c:url value="/dentists/update"/> "><span>Обновить</span></a>
                    <a class="crud-link" href="<c:url value="/dentists/delete"/> "><span>Удалить</span></a>
                </div>
            </div>
        </c:if>
    </main>
</t:mainLayout>
