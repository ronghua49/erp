<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>ERP - 系统管理 - 修改</title>
    <%@include file="../include/css.jsp"%>
</head>
<body class="hold-transition skin-purple sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

    <%@include file="../include/header.jsp"%>

    <!-- =============================================== -->

    <jsp:include page="../include/sider.jsp">
        <jsp:param name="menu" value="permission"/>
    </jsp:include>

    <!-- =============================================== -->

    <!-- 右侧内容部分 -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                权限管理
            </h1>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="box">
                <div class="box-header">
                    <h3 class="box-title">修改权限</h3>
                    <div class="box-tools">
                        <a href="/manage/permission" class="btn btn-success btn-sm">返回</a>
                    </div>
                </div>
                <div class="box-body">
                    <form method="post" id="editForm">
                        <div class="form-group">
                            <label>权限名称</label>
                            <input type="hidden" name="id" value="${permission.id}" class="form-control" id="id">
                            <input type="text" name="permissionName" value="${permission.permissionName}" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>权限代号</label>
                            <input type="text" name="permissionCode" value="${permission.permissionCode}" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>资源URL</label>
                            <input type="text" name="url" value="${permission.url}" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>权限类型</label>
                            <select name="permissionType" class="form-control">
                                <option value="菜单" ${permission.permissionType == "菜单" ? 'selected' : ''}>菜单</option>
                                <option value="按钮" ${permission.permissionType == "按钮" ? 'selected' : ''}>按钮</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>父权限</label>
                            <select name="pid" class="form-control">
                                <option value="0">顶级菜单</option>
                                <c:forEach items="${menuPermissionList}" var="menuPermission">
                                    <option value="${menuPermission.id}" ${permission.pid == menuPermission.id ? 'selected' : ''}>${menuPermission.permissionName}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </form>
                </div>
                <div class="box-footer">
                    <button class="btn pull-right btn-primary" id="editBtn">保存</button>
                </div>
            </div>
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
</div>
<!-- ./wrapper -->

<%@include file="../include/js.jsp"%>
<script>
    $(function () {
        var id=$("#id").val();
        $("#editBtn").click(function () {
            $("#editForm").submit();
        });
        $("#editForm").validate({
            errorElement:"span",
            errorClass:"text-danger",
            rules:{
                permissionName:{
                    required:true,
                    remote:"/manage/permission/check?permissionId="+id,
                },
                permissionCode:{
                    required:true,
                    remote:"/manage/permission/check?permissionId="+id,
                },
                url:{
                    required:true,
                    remote:"/manage/permission/check?permissionId="+id,
                },
                permissionType:{
                    required:true,
                },
                pid:{
                    required:true,
                }
            },
            messages:{
                permissionName:{
                    required:"权限名不得为空",
                    remote:"权限名不得重复"
                },
                permissionCode:{
                    required:"权限代号不得为空",
                    remote:"权限代号不得重复"
                },
                url:{
                    required:"资源路径不得为空",
                    remote:"资源路径名不得重复"
                },
                permissionType:{
                    required:true,
                },
                pid:{
                    required:true,
                }
            },
            submitHandler: function() {
                $.ajax({
                    url:"/manage/permission/edit",
                    type:"post",
                    data:$("#editForm").serialize(),
                    beforeSend:function(){
                        $("#editBtn").attr("disabled","disabled").text("保存中...");
                    },
                    success:function(result){
                        if(result.state=="success"){
                            layer.msg("修改成功", {icon:1, time:1000},function () {
                                window.location.href="/manage/permission";
                            });
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
        })
    })
</script>
</body>
</html>
