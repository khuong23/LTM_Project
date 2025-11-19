<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<div align="center">
    <h1>Login Page</h1>
    <form action="CheckLogin" method="post">
        Username: <input type="text" name="username" required/><br/>
        Password: <input type="password" name="password" required/><br/>
        <input type="submit" value="Login"/>
    </form>

    <%
        String error = request.getParameter("error");
        if (error != null) {
            if (error.equals("1")) {
                out.println("<p style='color:red;'>Invalid username or password. Please try again.</p>");
            } else if (error.equals("2")) {
                out.println("<p style='color:red;'>Database error. Please try again later.</p>");
            }
        }
    %>
</div>
</body>
</html>
