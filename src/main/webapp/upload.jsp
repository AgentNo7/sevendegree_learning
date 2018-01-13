<%--
  Created by IntelliJ IDEA.
  User: aqiod
  Date: 2018/1/13
  Time: 18:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"  pageEncoding="UTF-8"  %>
<html>
<head>
    <h2>文件上传</h2>
    <form name="login" action="/file/upload.do" method="post" enctype="multipart/form-data">
        <input type="file" name="upload_file">
        <input type="submit" value="上传">
    </form>

    <a href="main.jsp">返回main页面</a>

</head>
<body>

</body>
</html>
