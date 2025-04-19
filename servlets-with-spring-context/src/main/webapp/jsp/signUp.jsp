<%--
  Created by IntelliJ IDEA.
  User: v_yamancheva
  Date: 06.12.2024
  Time: 15:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign Up</title>
</head>
<body>
<h1>Sign Up</h1>
<form method="post">
    <label for="email">Email:</label>
    <input type="text" id="email" name="email">
    <label for="nickname">Nickname:</label>
    <input type="text" id="nickname" name="nickname">
    <label for="password">Password:</label>
    <input type="password" id="password" name="password">
    <label for="cpassword">Confirm password:</label>
    <input type="password" id="cpassword" name="cpassword">
    <button type="submit" id="submit-button">Sign Up</button>
</form>
<h3>Already have account? <a>Sign In</a></h3>
</body>
</html>
