<%@ page contentType="text/html;charset=UTF-8" language="java"  pageEncoding="UTF-8"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <p align="center">
        <h1>你上传的文件</h1>
        <table>
            <tr>
                <td>文件名</td>
                <td>下载</td>
            </tr>
            <c:forEach items="${fileList}" var="file">
                <tr>
                    <td>${file.fileName}</td>
                    <td><input type="button" value="下载" onclick="location='' + ${file.url}"></td>
                </tr>
            </c:forEach>
        </table><br>
        <input type="button" value="查询文件" onclick="location.href='/file/list.do'">&nbsp&nbsp&nbsp&nbsp
        <input type="button" value="上传文件" onclick="location.href='upload.jsp'">


    </p>


</head>
<body>

</body>
</html>
