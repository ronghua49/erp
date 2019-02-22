<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>ERP - 系统管理 - 新增</title>
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
                    <h3 class="box-title">新增权限</h3>
                    <div class="box-tools">
                        <a href="/manage/permission" class="btn btn-success btn-sm">返回</a>
                    </div>
                </div>
                <div class="box-body">
                    <form  id="saveForm">
                        <div class="form-group">
                            <label>权限名称</label>
                            <input type="text" name="permissionName" class="form-control" id="pn">
                        </div>
                        <div class="form-group">
                            <label>权限代号</label>
                            <input type="text" name="permissionCode" class="form-control" id="pc">
                        </div>
                        <div class="form-group">
                            <label>资源URL</label>
                            <input type="text" name="url" class="form-control" id="url">
                        </div>
                        <div class="form-group">
                            <label>权限类型</label>
                            <select name="permissionType" class="form-control"id="pt">
                                <option value="菜单">菜单</option>
                                <option value="按钮">按钮</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>父权限</label>
                            <select name="pid" class="form-control" id="pid">
                                <option value="0">顶级菜单</option>
                                <c:forEach items="${menuPermissionList}" var="permission">
                                    <option value="${permission.id}">${permission.permissionName}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </form>
                </div>
                <div class="box-footer">
                    <button class="btn pull-right btn-primary" id="saveBtn">保存</button>
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
        $("#saveBtn").click(function () {
            $("#saveForm").submit();
        });
        $("#saveForm").validate({
            errorElement:"span",
            errorClass:"text-danger",
            rules:{
                permissionName:{
                    required:true,
                    remote:"/manage/permission/check"
                },
                permissionCode:{
                    required:true,
                    remote:"/manage/permission/check"
                },
                url:{
                    required:true,
                    remote:"/manage/permission/check"
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
                    url:"/manage/permission/add",
                    type:"post",
                    data:$("#saveForm").serialize(),
                    beforeSend:function(){
                        $("#saveBtn").attr("disabled","disabled").text("保存中...");
                    },
                    success:function(result){
                        if(result.state=="success"){
                            layer.msg("添加成功", {icon:1, time:1000},function () {
                                window.location.href="/manage/permission";
                            });
                        }
                    },
                    error:function(){
                        layer.alert("系统繁忙");
                    },
                    complete:function(){
                        $("#saveBtn").removeAttr("disabled").text("保存");
                    }
                })
            }
        })
    })
</script>
</body>
</html>
