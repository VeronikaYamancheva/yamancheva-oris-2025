<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<t:errorLayout code="500" img="error-500-img.jpg" message="internal server error">
    <div class="redirect-link">You can go to <a href="<c:url value="/"/> ">main page</a>.</div>
</t:errorLayout>
