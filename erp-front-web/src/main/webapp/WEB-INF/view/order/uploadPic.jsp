<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<head>
    <title>订单图片上传</title>
    <link rel="stylesheet" href="/static/bootstrap/css/bootstrap.css">
    <link rel="stylesheet" href="/static/bootstrap/css/font-awesome.min.css">

</head>
<body>
<form id="uploadForm" action="/order/img/upload" method="post" enctype="multipart/form-data">

    <tr>请选择文件：</tr>
    <input type="hidden" name="currName" >
    <input type="file" name="file" id="file">


</form>
<button id="btn">提交</button>
<script src="/static/plugins/jQuery/jquery-2.2.3.min.js"></script>
<script src="/static/plugins/jQuery/jquery.validate.js"></script>
<script src="/static/bootstrap/js/bootstrap.min.js"></script>
<script src="/static/plugins/layer/layer.js"></script>
<script>

    $(function () {

        var message ="${message}";
        if (message) {

            layer.msg(message, {icon: 1, time: 1000}, function () {
                //window.location.reload();
            });
        }

        $("#btn").click(function () {
            if ($("#file").val()) {
                $("#uploadForm").submit();
            } else {
                layer.msg("您未选择任何文件");
            }
        });

    });


</script>

</body>
</html>
