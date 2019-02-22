<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>车管家ERP-质检列表</title>
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
                质检任务列表
            </h1>
        </section>

        <!-- Main content -->
        <section class="content">
            <!-- Default box -->
            <div class="box">
                <c:forEach items="${checkOrderList}" var="checkOrder">
                    <div class="panel panel-info">
                        <!-- Default panel contents -->
                        <div class="panel-heading">
                            <a href="/fix/${checkOrder.orderId}/checkDetail">订单号：${checkOrder.orderId}</a> - ${checkOrder.carType} - ${checkOrder.orderType}
                            <shiro:hasRole name="car:check">
                            <c:if test="${checkOrder.state == '4'}">
                                <button rel="${checkOrder.orderId}" class="btn btn-success btn-sm pull-right receiveBtn">任务领取</button>
                            </c:if>
                            </shiro:hasRole>
                        </div>
                        <!-- List group -->
                        <ul class="list-group">
                            <c:forEach items="${checkOrder.fixPartsList}" var="parts">
                                <li class="list-group-item">${parts.partsName} * ${parts.num}</li>
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
            layer.confirm("确定领取该质检任务么？", function(){
                $.get("/fix/" + orderId + "/check").done(function (res) {
                    if(res.state=="success"){
                        layer.msg("领取成功",{icon:1,time:1000},function () {
                            history.go(0);
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
