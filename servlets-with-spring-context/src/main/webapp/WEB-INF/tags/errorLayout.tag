<%@ tag description="Error page default layout" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%@ attribute name="code" %>
<%@ attribute name="img" %>
<%@ attribute name="message" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Error ${code}</title>
    <link rel="stylesheet" href="<c:url value="/styles/error.css"/>">
</head>
<body>
<div class="horizontal-container">
    <img src="<c:url value="/img/${img}"/> ">
    <div class="vertical-container">
        <div class="error-code">${code}</div>
        <div class="error-message">${message}</div>
        <jsp:doBody/>
    </div>
</div>
</body>
</html>