<%@ page contentType="text/html;charset=UTF-8" language="java"  pageEncoding="UTF-8"  %>
<html>
<body>
<p align="center">
<h2>登录页面</h2>
<form name="login" action="/user/login2.do" method="post">
    用户名：<input type="text" name="username" > <br>
    密码：<input type="password" name="password" ><br>
    <input type="submit" value="登录">
</form>
</p>
<br>


<h3><a href="register.jsp">注册</a></h3>
<p>这个页面用于测试后端接口中的登陆和文件上传，除此之外接口还有很多</p>
<p>关于登录：这是一个单点登录的实例，用redis在服务器端存储登录信息，并用cookie在本地作为登录验证，清除cookie就需要重新登录。</p>
<p>用户有权限设置，虽然可以注册但是注册的用户没有上传权限，可以用test-test这个账号进行测试</p>
<p>作为展示只做了四个jsp页面，index.jsp(本页面),main.jsp,list.jsp,upload.jsp,可以直接跳转，不是需要调用服务端跳转的隐藏页面</p>
<p>网页仅能通过https访问，因为。。</p>

</body>
</html>
