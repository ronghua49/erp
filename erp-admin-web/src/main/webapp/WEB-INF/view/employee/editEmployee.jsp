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
        <jsp:param name="menu" value="employee"/>
    </jsp:include>
    <!-- =============================================== -->

    <!-- 右侧内容部分 -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                修改员工信息
            </h1>
        </section>
        <!-- Main content -->
        <section class="content">
            <!-- Default box -->
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">修改员工信息</h3>
                    <div class="box-tools pull-right">
                        <a class="btn btn-primary btn-sm" href="/manage/employee"><i class="fa fa-arrow-left"></i> 返回列表</a>
                    </div>
                </div>
                <div class="box-body">
                    <form id="editForm">
                        <div class="form-group">
                            <label>姓名</label>
                            <input type="hidden" name="id" value="${employee.id}">
                            <input type="text" class="form-control" name="employeeName" value="${employee.employeeName}">
                        </div>
                        <div class="form-group">
                            <label>手机号码</label>
                            <input type="text" class="form-control" name="employeeTel" value="${employee.employeeTel}">
                        </div>
                        <div class="form-group">
                            <label >职位:</label>
                            <div>
                                <c:forEach items="${roleList}" var="role">
                                    <c:set var="flag" value="false"/>
                                    <c:forEach items="${currEmpRoles}" var="currRole">
                                          <c:if test="${role.id eq currRole.id}"> <c:set var="flag" value="true"/></c:if>
                                    </c:forEach>
                                    <div class="checkbox-inline">
                                        <input type="checkbox" value="${role.id}" name="roleIds"
                                        ${flag ? 'checked':''}
                                        > ${role.roleName}
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="box-footer">
                    <button class="btn btn-primary" id="editBtn">保存</button>
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
                employeeName:{
                    required:true,
                },
                employeeTel:{
                    required:true,
                    remote:"/manage/employee/check?employeeId=${employee.id}"
                }
            },
            messages:{
                employeeName:{
                    required:"员工名不得为空",
                },
                employeeTel:{
                    required:"电话不得为空",
                    remote:"电话号码不得和其他人员重复"
                }
            },
            submitHandler:function () {
                $.ajax({
                    url:"/manage/employee/${employee.id}/edit",
                    type:"post",
                    data:$("#editForm").serialize(),
                    beforeSend:function(){
                        $("#editBtn").attr("disabled","disabled").text("保存中...");
                    },
                    success:function(result){
                        if(result.state=="success"){
                            layer.msg("修改成功",{icon:1, time:1000},function () {
                                    window.location.href="/manage/employee";
                            })
                        }else {
                            layer.alert(result.message);
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