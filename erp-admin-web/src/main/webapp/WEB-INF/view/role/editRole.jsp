<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>ERP - 系统管理 - 更新角色</title>
    <%@include file="../include/css.jsp"%>
    <link rel="stylesheet" href="/static/plugins/treegrid/css/jquery.treegrid.css">
    <!-- iCheck -->
    <link rel="stylesheet" href="/static/plugins/iCheck/square/blue.css">
</head>
<body class="hold-transition skin-purple sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

    <%@include file="../include/header.jsp"%>

    <!-- =============================================== -->

    <jsp:include page="../include/sider.jsp">
        <jsp:param name="menu" value="roles"/>
    </jsp:include>

    <!-- =============================================== -->

    <!-- 右侧内容部分 -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                角色管理
            </h1>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="box">
                <div class="box-header">
                    <h3 class="box-title">更新角色</h3>
                    <div class="box-tools">
                        <a href="/manage/roles" class="btn btn-success btn-sm">返回</a>
                    </div>
                </div>
                <div class="box-body">
                    <form id="editForm">
                        <div class="form-group">
                            <label>角色名称</label>
                            <input type="text" name="roleName" value="${role.roleName}" class="form-control">
                            <input type="hidden" name="id" value="${role.id}" id="roleId">
                        </div>
                        <div class="form-group">
                            <label>角色代号</label>
                            <input type="text" name="roleCode" value="${role.roleCode}" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>权限配置</label>
                            <table class="table tree">
                                <tbody>
                                <c:forEach items="${rolePermissionMap}" var="entry">
                                    <tr class="treegrid-${entry.key.id}
                                <c:if test="${entry.key.pid != 0}">
                                    treegrid-parent-${entry.key.pid}
                                </c:if>">
                                        <td>
                                            <input type="checkbox" name="permissionId"  value="${entry.key.id}" ${entry.value ? 'checked' : ''}>
                                        </td>
                                        <td>${entry.key.permissionName}</td>
                                        <td>${entry.key.permissionCode}</td>
                                        <td>${entry.key.url}</td>
                                        <td>${entry.key.permissionType}</td>

                                    </tr>
                                </c:forEach>

                                </tbody>
                            </table>

                        </div>

                    </form>
                </div>
                <div class="box-footer">
                    <button class="btn pull-right btn-primary" id="editBtn">更新</button>
                </div>
            </div>
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
</div>
<!-- ./wrapper -->

<%@include file="../include/js.jsp"%>
<script src="/static/plugins/treegrid/js/jquery.treegrid.min.js"></script>
<script src="/static/plugins/treegrid/js/jquery.treegrid.bootstrap3.js"></script>
<!-- iCheck -->
<script src="/static/plugins/iCheck/icheck.min.js"></script>
<script>
    $(function () {
        /* $('input[type=checkbox]').iCheck({
             checkboxClass: 'icheckbox_square-blue',
             radioClass: 'iradio_square-blue',
             increaseArea: '20%' /!* optional *!/
         });*/
        var roleId = $("#roleId").val();
        $('.tree').treegrid();
        $("#editBtn").click(function () {
            $("#editForm").submit();
        });
        $("#editForm").validate({
            errorElement:"span",
            errorClass:"text-danger",
            rules:{
                roleName:{
                    required:true,
                    remote:"/manage/roles/check?roleId="+roleId,
                }
            },
            messages:{
                roleName:{
                    required:"角色名不得为空",
                    remote:"角色名不得重复"
                }
            },
            submitHandler:function () {
                $.ajax({
                    url:"/manage/roles/edit",
                    type:"post",
                    data:$("#editForm").serialize(),

                    beforeSend:function(){
                        $("#editBtn").attr("disabled","disabled").text("更新中...");
                    },
                    success:function(result){
                        if(result.state=="success"){
                            layer.msg("修改成功",{icon:1, time:1000},function () {
                                window.location.href="/manage/roles";
                            })
                        }
                    },
                    error:function(){
                        layer.alert("系统繁忙");
                    },
                    complete:function(){
                        $("#editBtn").removeAttr("disabled").text("更新");
                    }
                })
            }
        });
    });
</script>
</body>
</html>
