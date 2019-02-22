<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>车管家ERP-类型管理</title>
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
            <h1 style="display: inline-block">
                零件类型管理
            </h1>
            <shiro:hasPermission name="type:add">
            <a href="javascript:;" class="btn btn-primary pull-right" data-toggle="modal"
               data-target="#addModal">新增类型</a>
            </shiro:hasPermission>
        </section>
        <!-- Main content -->
        <section class="content">
            <!-- Default box -->
            <div class="box">
                <div class="box-body">
                    <table class="table">
                        <thead>
                        <tr>
                            <th>类型编码</th>
                            <th>类名名称</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${page.list}" var="type">
                            <tr>
                                <td>${type.id}</td>
                                <td>${type.typeName}</td>
                                <td>
                                    <shiro:hasPermission name="type:edit">
                                    <a href="javascript:;"rel="${type.id}" typeName="${type.typeName}"
                                       data-toggle="modal" data-target="#updateModal" class="edit">更新</a>
                                    </shiro:hasPermission>
                                    <shiro:hasPermission name="type:del">
                                    <a href="javascript:;" class="del" rel="${type.id}">删除</a> </td>
                                    </shiro:hasPermission>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <ul id="pagination" class="pagination pull-right"></ul>
                </div>
            </div>
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
<%--模态框 新增parts类型--%>
    <div class="modal fade" id="addModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">新增类型</h4>
                </div>
                <div class="modal-body">
                    <form action=""  class="form-horizontal" id="addForm">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">类型名称:</label>
                            <div class="col-sm-10">
                                <input type="text" name="addName" class="form-control" id="addName"
                                       placeholder="请输入节点名称">
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button class="btn btn-primary pull-left" id="addbtn">保存</button>
                    <button class="btn btn-default pull-left" data-dismiss="modal">取消</button>
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
    </div>


    <%--修改类型--%>
    <div class="modal fade" id="updateModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">更新类型</h4>
                </div>
                <div class="modal-body">
                    <form action="" class="form-horizontal" id="editForm">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">类型名称:</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="editName" name="editName"
                                       placeholder="请输入节点名称">
                                <input type="hidden"  name="editTypeId" id="editTypeId" >
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button class="btn btn-primary pull-left" id="editbtn">保存</button>
                    <button class="btn btn-default pull-left" data-dismiss="modal">取消</button>
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
    </div>

    <%--删除类型--%>
    <div class="modal fade" id="delModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">请输入删除类型</h4>
                </div>
                <div class="modal-body">
                    <form action="" class="form-horizontal" id="delForm">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">类型名称:</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="delTypeName" name="delName"
                                       placeholder="请输入要删除的类型名称">
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">

                    <button class="btn btn-primary pull-left" id="delBtn">删除</button>

                    <button class="btn btn-default pull-left" data-dismiss="modal">取消</button>
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
    </div>
    <!-- /.modal -->
    <!-- 底部 -->
    <%@ include file="../include/footer.jsp" %>
</div>

<%@ include file="../include/js.jsp" %>
<script>
    $(function() {
        $("#pagination").twbsPagination({
            totalPages : "${page.pages}",
            visiblePages : 6,
            href : "/car/type?p={{number}}",
            first : "首页",
            prev : "上一页",
            next : "下一页",
            last : "末页"
        });
        //删除的业务处理
        $(".del").click(function(){
            layer.confirm("are you sure delete this note?",{
                icon: 7, //询问框icon图标
                title:'类型删除',//询问框标题
                btn:['删除','取消']//询问框按钮
            }, function () {
                    $('#delModal').modal('show');
                    layer.closeAll('dialog');
             });
        });
        $("#delBtn").click(function () {
            $("#delForm").submit();
        });
        $("#delForm").validate({
            errorElement:'span',
            errorClass:'text-danger',
            rules:{
                delName : {
                    required : true,
                    remote:"/inventory/type/del/check"
                }
            },
            messages:{
                delName : {
                    required : "删除类型不得为空",
                    remote:"该类型下有零件不得删除或该类型不存在"
                }
            },
            submitHandler:function(){
                $.ajax({
                    url:"/inventory/type/del",
                    type:"post",
                    data:$("#delForm").serialize(),/*{"delName":$("#delTypeName").val()},*/
                    success:function(res){
                        if (res == 1){
                            layer.msg("删除成功！",{icon:1,time:1000},function () {
                               window.parent.location.reload();
                            });
                        }else{
                            layer.msg("删除失败！",{icon:2,time:1000},function () {
                                window.parent.location.reload();
                            });
                        }
                    },
                    error:function(){
                        layer.alert("系统异常");
                    }
                })
            }
        });


        //新增的业务处理 新增类型表单的ajax提交
        $("#addbtn").click(function(){
            $("#addForm").submit();
        });
        $("#addForm").validate({
            errorElement:'span',
            errorClass:'text-danger',
            rules:{
                addName : {
                    required : true,
                    remote:"/inventory/type/add/check"
                }
            },
            messages:{
                addName : {
                    required : "内容不得为空",
                    remote:"该节点已存在，不得重复"
                }
            },
            submitHandler:function(){
                $.ajax({
                    url:"/inventory/type/add",
                    type:"post",
                    data:$("#addForm").serialize(),
                    success:function(res){
                        if (res!=0){
                            layer.msg("新增成功！",{icon:1,time:1000},function () {
                                window.parent.location.reload();
                            });
                        }
                    },
                    error:function(){
                        alert("系统异常");
                    },
                })
            }
        });

        //更新节点的 ajax提交
        $(".edit").click(function(){
            var typeId= $(this).attr("rel");
            var typeName =$(this).attr("typeName");

            //回显表单 当前类型
            $("#editName").val(typeName);
            //添加隐藏的 typeid值
            $("#editTypeId").val(typeId);
            //表单按钮的点击事件
            $("#editbtn").click(function(){
                $("#editForm").submit();
            });

            $("#editForm").validate({
                errorElement:'span',
                errorClass:'text-danger',
                rules:{
                    editName : {
                        required : true,
                        remote:"/inventory/type/edit/check?typeId="+typeId//修改节点类型时验证，需加上当前typeid
                    }
                },
                messages:{
                    editName : {
                        required :"修改内容不得为空",
                        remote:"不得和已有节点重复"
                    }
                },
                submitHandler:function(){
                    $.ajax({
                        url:"/inventory/type/edit",
                        type:"post",
                        data:$("#editForm").serialize(),
                        success:function(res){
                            if (res!=0){
                               layer.msg("修改成功！",{icon:1,time:1000},function () {
                                   window.parent.location.reload();
                               });
                            }
                        },
                        error:function(){
                            alert("系统异常");
                        },
                    })
                }
            });

        });
    });
</script>
</body>
</html>
