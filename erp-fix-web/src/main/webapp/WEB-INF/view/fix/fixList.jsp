<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>车管家ERP-维修列表</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <%@ include file="../include/css.jsp" %>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">
    <%@ include file="../include/header.jsp" %>
    <jsp:include page="../include/sider.jsp">
        <jsp:param name="menu" value="fix"/>
    </jsp:include>

    <!-- 右侧内容部分 -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                维修任务列表
            </h1>
        </section>

        <!-- Main content -->
        <section class="content">

            <!-- Default box -->
            <div class="box">
                <c:forEach items="${fixOrderList}" var="fixOrder">
                    <div class="panel panel-info">
                        <!-- Default panel contents -->
                        <div class="panel-heading">
                            <a href="/fix/${fixOrder.orderId}/detail">订单号：${fixOrder.orderId}</a> - ${fixOrder.carType} - ${fixOrder.orderType}
                            <c:if test="${fixOrder.state == '2'}">
                                <button rel="${fixOrder.orderId}" class="btn btn-success btn-sm pull-right receiveBtn">任务领取</button>
                            </c:if>
                        </div>
                        <!-- List group -->
                        <ul class="list-group">
                            <c:forEach items="${fixOrder.fixPartsList}" var="parts">
                                <li class="list-group-item">${parts.partsName} x ${parts.num}</li>
                            </c:forEach>
                        </ul>
                    </div>
                </c:forEach>
                <!-- /.box -->
            </div>
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

    <%@ include file="../include/footer.jsp" %>

</div>
<!-- ./wrapper -->
<%@ include file="../include/js.jsp" %>
<script>
    $(function(){
        $(".receiveBtn").click(function() {
            var orderId = $(this).attr("rel");
            layer.confirm("确定接收该任务么？", function(){
                $.get("/fix/" + orderId + "/accept").done(function (res) {
                    if(res.state=="success"){
                        layer.msg("接单成功",{icon:1,time:1000},function () {
                           window.location.reload();
                        });
                    }else{
                        layer.msg(res.result);
                    }
                }).error(function () {
                    layer.msg("系统异常");
                });
            })
        })
    })
</script>
</body>
</html>
