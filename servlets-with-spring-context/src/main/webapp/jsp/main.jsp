<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>

<t:mainLayout title="Main Page" css="mainPage.css" js="main.js">
    <div id="special-offers">
        <div class="custom-h1">Our special offers</div>
        <div class="text">Using this slider, you can learn more about our special offers. Still have questions? Contact us.</div>
        <div class="slider-container">
            <div><button class="prev-button">&lt</button></div>
            <div class="slider">
                <img src="<c:url value="/img/special_offers/offer_1.png"/>" alt="offer_1"/>
                <img src="<c:url value="/img/special_offers/offer_2.png"/>" alt="offer_2"/>
                <img src="<c:url value="/img/special_offers/offer_3.png"/>" alt="offer_3"/>
            </div>
            <div><button class="next-button">&gt</button></div>
        </div>
    </div>

    <div id="services-block">
        <div class="custom-h1">About our services</div>
        <div class="text">Here will be information about our services.</div>
    </div>

    <div id="employees-block">
        <div class="custom-h1">About our employees</div>
        <div class="text">Here will be information about our dentists.</div>
    </div>

    <div id="about-clinic">
        <div class="custom-h1">About us</div>
        <div class="text">Some text about our clinic.</div>

        <div id="location">
            <div class="custom-h2">Location</div>
            <div class="text">Our clinic is located at: Russia, Republic of Tatarstan, Kazan, Kremlevskaya 35</div>
            <div class="map">
                <jsp:include page="include/map.jsp"/>
            </div>
        </div>
    </div>
</t:mainLayout>

