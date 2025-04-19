<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<t:errorLayout code="404" img="error-404-img.jpg" message="page not found">
    <div class="redirect-link">You can go to <a href="<c:url value="/"/> ">main page</a>.</div>
</t:errorLayout>
