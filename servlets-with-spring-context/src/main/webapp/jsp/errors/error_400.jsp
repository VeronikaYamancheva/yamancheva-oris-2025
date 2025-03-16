<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<t:errorLayout code="400" img="error-400-img.jpg" message="bad request">
    <div class="redirect-link">You can go to <a href="<c:url value="/"/> ">main page</a>.</div>
</t:errorLayout>
