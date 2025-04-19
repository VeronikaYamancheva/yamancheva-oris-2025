<%@ tag description="Default menu" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%@ attribute name="title" %>
<%@ attribute name="css" %>
<%@ attribute name="js" %>

<!DOCTYPE html>
<html>
<head>
    <title>${title}</title>
    <link rel="stylesheet" href="<c:url value="/styles/main.css"/> ">
    <link rel="stylesheet" href="<c:url value="/styles/${css}"/> ">
</head>
<body>
<div>
    <header>
        <img id="logo-img" src="<c:url value="/img/logo.jpg"/>" alt="logo">
        <div class="header-navigation">
            <a id="name" href="<c:url value="/"/> " title="go to main page">Tooth Fairy's Workshop</a>
            <div class="horizontal-menu">
                <nav>
                    <a class="nav_item" href="<c:url value="/"/>#about-clinic"><span>About us</span></a>
                    <a class="nav_item" href="<c:url value="/procedures"/> "><span>Procedures</span></a>
                    <a class="nav_item" href="<c:url value="/dentists"/> "><span>Dentists</span></a>
                    <c:if test="${sessionScope.client == null}">
                        <a id="sign-in-ref" href="<c:url value="/sign_in"/>"><span>Sign in</span></a>
                    </c:if>
                    <c:if test="${sessionScope.client != null}">
                        <a class="nav_item" id="client-login"
                           href="<c:url value="/profile"/>">${sessionScope.client.nickname}</a>
                    </c:if>
                </nav>
            </div>
        </div>
    </header>
    <div class="body">
        <jsp:doBody/>
    </div>
</div>
<script src="<c:url value="/js/${js}"/> "></script>
</body>
</html>