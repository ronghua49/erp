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
                新增员工
            </h1>
        </section>
        <!-- Main content -->
        <section class="content">
            <!-- Default box -->
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">新增员工</h3>
                    <div class="box-tools pull-right">
                        <a class="btn btn-primary btn-sm" href="/manage/employee"><i class="fa fa-arrow-left"></i> 返回列表</a>
                    </div>
                </div>
                <div class="box-body">
                    <form action="" id="addForm">
                        <div class="form-group">
                            <label>姓名</label>
                            <input type="text" class="form-control" name="employeeName">
                        </div>
                        <div class="form-group">
                            <label>手机号码</label>
                            <input type="text" class="form-control" name="employeeTel">
                        </div>
                        <div class="form-group">
                            <label>密码(默认000000)</label>
                            <input type="password" class="form-control" value="000000" name="password">
                        </div>


                        <div class="form-group">
                            <label >职位:</label>
                            <c:forEach items="${roleList}" var="role">
                                <div class="checkbox-inline">
                                    <input type="checkbox" value="${role.id}" name="roleIds"> ${role.roleName}
                                </div>
                            </c:forEach>
                        </div>
                    </form>
                </div>
                <div class="box-footer">
                    <button class="btn btn-primary" id="addBtn">保存</button>
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
    /*select2 多选角色*/
   /* $("#classic").select2({
        placeholder : '请选择职位',
        tags : true,
        multiple : true,
        /!*  height : '40px',*!/
        maximumSelectionLength : 3,
        allowClear : true,
        language : "zh-CN",});
*/


    $(function () {
        $("#addBtn").click(function () {
            $("#addForm").submit();
        });

        $("#addForm").validate({
            errorElement:"span",
            errorClass:"text-danger",
            rules:{
                employeeName:{
                    required:true,
                },
                employeeTel:{
                    required:true,
                    remote:"/manage/employee/check"
                },
                password:{
                    required:true,
                }
            },
            messages:{
                employeeName:{
                    required:"员工名不得为空",
                },
                employeeTel:{
                    required:"电话不得为空",
                    remote:"电话号码不得和其他人员重复"
                },
                password:{
                    required:"密码不得为空",
                }
            },
            submitHandler:function () {
                $.ajax({
                    url:"/manage/employee/add",
                    type:"post",
                    data:$("#addForm").serialize(),

                    beforeSend:function(){
                        $("#addBtn").attr("disabled","disabled").text("保存中...");
                    },
                    success:function(result){
                        if(result.res!=0){
                            layer.msg("添加成功",{icon:1,time:1000},function () {
                                window.location.href="/manage/employee";
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