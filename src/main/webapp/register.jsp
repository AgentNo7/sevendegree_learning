<%--
  Created by IntelliJ IDEA.
  User: aqiod
  Date: 2018/1/13
  Time: 18:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"  pageEncoding="UTF-8"  %>
<html>
<head>
    <h2>注册页面</h2>
    <form name="login" action="/user/register.do" method="post">
        用户名;<input type="text" name="username" > <br>
        邮箱：<input type="email" name="email" > <br>
        手机：<input type="phone" name="phone" ><br>
        密码问题：<input type="question" name="question" ><br>
        密码答案：<input type="answer" name="answer" ><br>
        密码：<input type="password" name="password"><br>
        <input type="submit" value="注册">
    </form>
    </p>
    <br>


    <h3><a href="index.jsp">返回登录</a></h3>
</head>
<body>

</body>
</html>
