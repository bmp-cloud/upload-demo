<%--
  Created by IntelliJ IDEA.
  User: yoojehwan
  Date: 10/28/24
  Time: 11:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>파일 업로드 예제</title>
</head>
<body>
<h1>파일 업로드</h1>
<form action="uploader" method="post" enctype="multipart/form-data">
    <input type="file" name="file"><br><br>
    <input type="submit" value="업로드">
</form>
</body>
</html>
