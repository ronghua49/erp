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
    <%@ include file="../include/css.jsp" %>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

    <%@ include file="../include/header.jsp" %>
    <jsp:include page="../include/sider.jsp">
        <jsp:param name="menu" value="parts"/>
    </jsp:include>
    <!-- =============================================== -->

    <!-- 右侧内容部分 -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
               新增配件
            </h1>
        </section>
        <!-- Main content -->
        <section class="content">
            <!-- Default box -->
            <div class="box">
                <div class="box-body">
                    <form id="addForm">
                        <div class="form-group">
                            <label class=" control-label">配件编号:</label>
                            <input type="text"  name="partsNo" class="form-control"  value="${parts.partsNo}" placeholder="请输入配件编号">
                        </div>
                        <div class="form-group">
                            <label>配件名称:</label>
                            <input type="text" name="partsName" value="${parts.partsName}" class="form-control" placeholder="请输入配件名称">
                        </div>

                        <div class="form-group">
                            <label>进价:</label>
                            <input type="text" name="inPrice" value="${parts.inPrice}" class="form-control" placeholder="请输入进价">
                        </div>
                        <div class="form-group">
                            <label>售价:</label>
                            <input type="text" name="salePrice" value="${parts.salePrice}" class="form-control" placeholder="请输入售价">
                        </div>
                        <div class="form-group">
                            <label>库存:</label>
                            <input type="text" name="inventory" value="${parts.inventory}" class="form-control" placeholder="请输入库存">
                        </div>
                        <div class="form-group">
                            <label>类型:</label>
                            <select name="typeId" class="form-control">
                                <option>请选择类型</option>
                                <c:forEach items="${typeList}" var="type">
                                    <option value="${type.id}">${type.typeName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>产地:</label>
                            <input type="text" name="address" value="${parts.address}"  class="form-control" placeholder="请输入产地">
                        </div>
                    </form>
                    <button class="btn btn-primary pull-left" id="addBtn">保存</button>
                </div>
                <!-- /.box-body -->

            </div>
            <!-- /.box -->

        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

    <!-- 底部 -->
    <%@ include file="../include/footer.jsp" %>

</div>

<%@ include file="../include/js.jsp" %>
<script>

    $(function () {
        $("#addBtn").click(function () {
            $("#addForm").submit();
        });
        $("#addForm").validate({
            errorElement:"span",
            errorClass:"text-danger",
            rules:{
                partsNo:{
                    required:true,
                },
                partsName:{
                    required:true,
                },
                inPrice:{
                    required:true,
                    number:true
                },
                salePrice:{
                    required:true,
                    number:true
                },
                inventory:{
                    required:true,
                    digits:true
                },
                typeId:{
                    required:true,
                },
                address:{
                    required:true,
                }
            },
            messages:{
                partsNo:{
                    required:"零件编号不得为空",
                },
                partsName:{
                    required:"零件名称不得为空",
                },
                inPrice:{
                    required:"进价不得为空",
                },
                salePrice:{
                    required:"售价不得为空",
                },
                inventory:{
                    required:"库存不得为空",
                },
                typeId:{
                    required:"请选择类型",
                },
                address:{
                    required:"产地不得为空",
                }
            },
            submitHandler:function () {
                $.ajax({
                    url:"/inventory/parts/add",
                    type:"post",
                    data:$("#addForm").serialize(),

                    beforeSend:function(){
                        $("#addBtn").attr("disabled","disabled").text("保存中...");
                    },
                    success:function(result){
                            if(result.res!=0){
                                layer.msg("入库成功",{icon:1,time:1000},function () {
                                window.location.href="/inventory/parts";
                                })
                            }
                    },
                    error:function(){
                        layer.alert("系统繁忙");
                    },
                    complete:function(){
                        $("#addBtn").removeAttr("disabled").text("保存");
                    }
                })
            }
        });
    });
</script>
</body>
</html>