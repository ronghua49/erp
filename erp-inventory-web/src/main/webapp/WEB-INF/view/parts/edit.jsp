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
                配件更新
            </h1>
        </section>
        <!-- Main content -->
        <section class="content">
            <!-- Default box -->
            <div class="box">
                <div class="box-body">
                    <form id="editForm">
                        <div class="form-group">
                            <label class=" control-label">配件编号:</label>
                            <input type="text" disabled class="form-control"  value="${parts.partsNo}" placeholder="请输入配件编号">
                        </div>
                        <div class="form-group">
                            <label>配件名称:</label>
                            <input type="hidden" name="id" value="${parts.id}" class="form-control" placeholder="请输入配件名称">
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
                            <label class="col-sm-2">类型:</label>
                            <select name="typeId" class="form-control">
                                <option>请选择类型</option>
                                <c:forEach items="${typeList}" var="type">
                                    <option value="${type.id}" ${parts.typeId == type.id ? 'selected': ''} >${type.typeName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>产地:</label>
                            <input type="text" name="address" value="${parts.address}"  class="form-control" placeholder="请输入产地">
                        </div>
                    </form>
                        <button class="btn btn-primary pull-left" id="editBtn">保存</button>
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
        $("#editBtn").click(function () {
            $("#editForm").submit();
        });
        $("#editForm").validate({
            errorElement:"span",
            errorClass:"text-danger",
            rules:{
                partsName:{
                    required:true,
                },
                inPrice:{
                    required:true,
                },
                salePrice:{
                    required:true,
                },
                typeId:{
                    required:true,
                },
                address:{
                    required:true,
                }
            },
            messages:{
                partsName:{
                    required:"零件名称不得为空",
                },
                inPrice:{
                    required:"进价不得为空",
                },
                salePrice:{
                    required:"售价不得为空",
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
                    url:"/inventory/parts/edit",
                    type:"post",
                    data:$("#editForm").serialize(),
                    beforeSend:function(){
                    $("#editBtn").attr("disabled","disabled").text("保存中...");
                 },
                    success:function(res){
                        if(res!=0){
                            layer.msg("修改成功",function () {
                                window.location.href="/inventory/parts"
                            })
                        }
                },
                error:function(){
                    layer.alert("系统繁忙");
                },
                complete:function(){
                    $("#editBtn").removeAttr("disabled").text("保存");
                }
                })
            }
        });
    });


</script>
</body>
</html>

