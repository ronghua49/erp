<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>车管家ERP-配件流水</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <%@ include file="../include/css.jsp" %>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">
    <%@ include file="../include/header.jsp" %>
    <jsp:include page="../include/sider.jsp">
        <jsp:param name="menu" value="order"/>
    </jsp:include>

    <!-- 右侧内容部分 -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                配件流水
            </h1>
        </section>

        <!-- Main content -->
        <section class="content">

            <div class="box no-border">
                <div class="box-body">
                    <form class="form-inline">
                        <input type="text" class="form-control" name="streamId" placeholder="订单号">
                        <input type="text" class="form-control" name="partsNo" placeholder="配件编码">
                        <input type="hidden" name="startTime" id="startTime"  value="${param.startTime}">
                        <input type="hidden" name="endTime" id="endTime"  value="${param.endTime}">
                        <input type="text" class="form-control" id="time" placeholder="下单日期选择">
                        <button class="btn btn-default">搜索</button>
                    </form>
                </div>
            </div>

            <!-- Default box -->
            <div class="box">
                <div class="box-body">
                    <ul class="nav nav-tabs">
                        <li id="in" class="${param.type == '1' ? 'active' : ''}"><a href="/inventory/parts/stream?type=1">入库流水</a></li>
                        <li id="out" class="${param.type == '2' ? 'active' : ''}"><a href="/inventory/parts/stream?type=2">出库流水</a></li>
                    </ul>
                    <table class="table">
                        <thead>
                        <c:if test="${param.type eq '1'}">
                            <tr>
                                <th>订单号</th>
                                <th>配件编码</th>
                                <th>配件名称</th>
                                <th>入库前数量</th>
                                <th>入库数量</th>
                                <th>当前数量</th>
                                <th>采购员</th>
                                <th>入库日期</th>
                            </tr>
                        </c:if>
                        <c:if test="${param.type eq '2'}">
                            <tr>
                                <th>订单号</th>
                                <th>配件编码</th>
                                <th>配件名称</th>
                                <th>出库前数量</th>
                                <th>出库数量</th>
                                <th>余量</th>
                                <th>取件员</th>
                                <th>取件日期</th>
                            </tr>
                        </c:if>
                        </thead>
                        <tbody>
                        <c:forEach items="${page.list}" var="stream">
                            <tr>
                                <td>${stream.id}</td>
                                <td>${stream.partsNo}</td>
                                <td>${stream.partsName}</td>
                                <td>${stream.beforeNum}</td>
                                <td>${stream.num}</td>
                                <td>${stream.afterNum}</td>
                                <td>${stream.employeeName}</td>
                                <td><fmt:formatDate value="${stream.createTime}" pattern="yyyy-MM-dd HH:mm"/> </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                     <ul id="pagination" class="pagination pull-right"></ul>
                </div>
                <!-- /.box-body -->
            </div> <!-- /.box -->
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
        var startDate = "${param.startTime}";
        var endDate = "${param.endTime}";

        if(startDate && endDate) {
            $('#time').val(startDate + " / " + endDate);
        }
        $("#pagination").twbsPagination({
            totalPages : ${page.pages eq 0 ? 1: page.pages},
            visiblePages : 7,
            first : '首页',
            last:'末页',
            prev:'上一页',
            next:'下一页',
            href:"/inventory/parts/stream?p={{number}}&startTime=" + startDate + "&endTime=" + endDate + "&streamId=${param.streamId}&partsNo=${param.partsNo}"
        });
        var locale = {
            "format": 'YYYY-MM-DD',
            "separator": " - ",//
            "applyLabel": "确定",
            "cancelLabel": "取消",
            "fromLabel": "起始时间",
            "toLabel": "结束时间'",
            "customRangeLabel": "自定义",
            "weekLabel": "W",
            "daysOfWeek": ["日", "一", "二", "三", "四", "五", "六"],
            "monthNames": ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
            "firstDay": 1
        };
        $('#time').daterangepicker({
            autoUpdateInput:false,
            "locale": locale,
            "opens":"right",
            "timePicker":false
        },function(start,end) {
            $("#startTime").val(start.format('YYYY-MM-DD'));
            $("#endTime").val(end.format('YYYY-MM-DD'));

            $('#time').val(start.format('YYYY-MM-DD') + " / " + end.format('YYYY-MM-DD'));
        });
    })
</script>
</body>
</html>
