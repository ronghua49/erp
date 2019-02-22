<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>车管家ERP-库存管理</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <%@ include file="../include/css.jsp"%>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

    <%@ include file="../include/header.jsp" %>
    <jsp:include page="../include/sider.jsp">
        <jsp:param name="menu" value="parts"/>
    </jsp:include>

    <!-- 右侧内容部分 -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1 style="display: inline-block;">
                库存管理
            </h1>
            <div class="box-tools pull-right" style="display: inline-block">
                <shiro:hasPermission name="parts:add">
                <a href="/inventory/parts/add"  class="btn btn-primary" >
                        <i class="fa fa-plus"></i> 添加部件</a>
                </shiro:hasPermission>

            </div>
        </section>
        <!-- Main content -->
        <section class="content">

            <div class="box no-border">
                <div class="box-body">
                    <form class="form-inline pull-left" id="searchForm" action="">
                        <input type="text" name="partsName" placeholder="配件名称" class="form-control" value="${param.partsName}">
                        <select class="form-control" name="partsType" id="partsType">

                            <option value="">请选择配件类型</option>
                            <c:forEach items="${typeList}" var="type">
                                <option value="${type.id}" ${(param.partsType==type.id ) ? 'selected' : '' } >${type.typeName}</option>
                            </c:forEach>
                        </select>
                        <button class="btn btn-default" id="btn" >搜索</button>
                    </form>
                </div>
            </div>

            <!-- Default box -->
            <div class="box">
                <div class="box-body">
                    <table class="table">
                        <thead>
                        <tr>
                            <th>配件编码</th>
                            <th>名称</th>
                            <th>库存</th>
                            <th>进价</th>
                            <th>售价</th>
                            <th>类型</th>
                            <th>产地</th>
                            <th>#</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${page.list}" var="parts">
                            <tr>
                                <td>${parts.partsNo}</td>
                                <td><a href="/inventory/parts/${parts.id}/detail"> ${parts.partsName}</a></td>
                                <td>${parts.inventory}</td>
                                <td>${parts.inPrice}</td>
                                <td>${parts.salePrice}</td>
                                <td>${parts.type.typeName}</td>
                                <td>${parts.address}</td>

                                <td>
                                    <shiro:hasPermission name="parts:edit">
                                        <a class="btn btn-primary btn-xs" href="/inventory/parts/${parts.id}/edit" title="更新"><i class="fa fa-edit"></i></a>
                                    </shiro:hasPermission>
                                    <shiro:hasPermission name="parts:del">
                                    <a class="btn btn-danger btn-xs del" href="javascript:;"  rel="${parts.id}" title="删除"><i class="fa fa-trash"></i></a> </td>
                                    </shiro:hasPermission>
                                </tr>
                        </c:forEach>

                        </tbody>
                    </table>
                    <ul id="pagination" class="pagination pull-right"></ul>
                </div>
                <!-- /.box-body -->
            </div>
            <!-- /.box -->

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
        $("#pagination").twbsPagination({
            totalPages : ${page.pages},
            visiblePages : 6,
            first : '首页',
            last:'末页',
            prev:'上一页',
            next:'下一页',
            href:"?p={{number}}&partsType=${param.partsType}&partsName="+encodeURIComponent("${param.partsName}")
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

        var startDate = "";
        var endDate = "";

        if(startDate && endDate) {
            $('#time').val(startDate + " / " + endDate);
        }

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

        var message=$("#message").val();
        if(message){
            layer.msg(message);
        }
        $(".del").click(function(){
            var partsId = $(this).attr("rel");
            layer.confirm("are your sure delete this parts?",function () {
                window.location.href="/inventory/parts/"+partsId+"/del";
            })
        });
    })
</script>
</body>
</html>
