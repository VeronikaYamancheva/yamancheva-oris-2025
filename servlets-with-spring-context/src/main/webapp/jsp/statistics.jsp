<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<t:mainLayout title="Statistics" css="crud_read.css">
    <main>
        <div class="list-header">Все клиенты:</div>
        <div class="list-container vertical-container">
            <c:forEach var="client" items="${clientsList}">
                <div class="item-container horizontal-container">
                    <img class="item-image" src="<c:url value="/img/procedure-img.png"/> ">
                    <div class="description-container">
                        <div class="item-name">
                            <c:if test="${client.firstName == null}">---</c:if>
                            <c:if test="${client.firstName != null}">${client.firstName}</c:if>

                            <c:if test="${client.lastName == null}">---</c:if>
                            <c:if test="${client.lastName != null}">${client.firstName}</c:if>
                        </div>
                        <div class="item-desc">Email: ${client.email}</div>
                        <div class="item-desc">Nickname: ${client.nickname}</div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </main>
</t:mainLayout>