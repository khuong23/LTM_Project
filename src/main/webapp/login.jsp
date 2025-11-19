<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*, javax.servlet.*, javax.servlet.http.*, java.io.*" %>
<html>
<head>
    <title>Login</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            overflow: hidden;
        }

        #video-background {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            object-fit: cover;
            z-index: -1;
        }

        .login-container {
            background-color: rgba(255, 255, 255, 0.95);
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.3);
            width: 300px;
            backdrop-filter: blur(10px);
            z-index: 1;
        }

        h1 {
            text-align: center;
            color: #0066cc;
        }

        input[type="text"], input[type="password"] {
            width: 100%;
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }

        input[type="submit"] {
            width: 100%;
            padding: 10px;
            background-color: #0066cc;
            border: none;
            border-radius: 4px;
            color: white;
            font-size: 16px;
            cursor: pointer;
        }

        input[type="submit"]:hover {
            background-color: #0052a3;
        }

        .error-message {
            color: red;
            font-size: 14px;
            text-align: center;
        }
    </style>
</head>
<body>
    <video id="video-background" autoplay muted loop>
        <source src="${pageContext.request.contextPath}/resources/Image/login-bg.mp4" type="video/mp4">
    </video>

    <div class="login-container">
        <h1>Login</h1>
        <form action="CheckLogin" method="post">
            Username: <input type="text" name="username" required/><br/>
            Password: <input type="password" name="password" required/><br/>
            <input type="submit" value="Login"/>
        </form>

        <%
            String error = request.getParameter("error");
            if (error != null) {
                if (error.equals("1")) {
                    out.println("<p class='error-message'>Invalid username or password. Please try again.</p>");
                } else if (error.equals("2")) {
                    out.println("<p class='error-message'>Database connection error. Please try again later.</p>");
                }
            }
        %>
    </div>
</body>
</html>