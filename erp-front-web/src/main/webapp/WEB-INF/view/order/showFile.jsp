<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<head>
    <title>显示文件列表</title>
    <link rel="stylesheet" href="/static/bootstrap/css/bootstrap.css">
    <link rel="stylesheet" href="/static/bootstrap/css/font-awesome.min.css">
</head>
<body>

<table class="table table-hover">


    <thead>
    <tr>
        <th>选择</th>
        <th>文件名</th>
        <th>大小</th>
        <th>下载</th>
    </tr>

    </thead>
    <tbody>
    <c:forEach items="${nameList}" var="name">
        <tr>
            <td><input type="checkbox"></td>
            <td> <a href="/order/download/${name}">${name}</a></td>
            <td></td>
            <td><a href="javascript:;" rel="${name}"> <i class="fa fa-download" aria-hidden="true"></i> </a></td>
        </tr>

    </c:forEach>
    </tbody>
</table>
<script src="/static/plugins/jQuery/jquery-2.2.3.min.js"></script>
<script src="/static/plugins/jQuery/jquery.validate.js"></script>
<script src="/static/bootstrap/js/bootstrap.min.js"></script>
<script src="/static/plugins/layer/layer.js"></script>
<script>

</script>

</body>
</html>
