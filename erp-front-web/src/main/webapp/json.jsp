<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/10/15
  Time: 13:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>json测试</title>
    <link rel="stylesheet" href="/static/bootstrap/css/bootstrap.css">
    <link rel="stylesheet" href="/static/bootstrap/css/font-awesome.min.css">
    <link rel="stylesheet" href="/static/plugins/datetimepicker/css/bootstrap-datetimepicker.min.css"/>
    <link rel="stylesheet" href="/static/plugins/daterangepicker/daterangepicker.css"/>

</head>

<body>

<div id="d1" style="color: yellow;padding: 50px">

    <div id="d2" style="padding: 50px">
        <h1>padding -margin -test</h1>
    </div>
</div>


<section class="content">
    <div class="box no-border">
        <div class="box-body">
            <form class="form-inline" id="serachForm" style="display: inline-block">
                <input type="text" class="form-control" id="time" name="orderTime" placeholder="下单日期选择">
            </form>
            <button class="btn btn-default" id="btn">搜索</button>
        </div>
    </div>

    <!-- Default box -->
    <div class="box">
        <div class="box-body">
            <input type="hidden" name="page" id="page">
            <table class="table">
                <thead>
                <tr>
                    <th>订单号</th>
                    <th>车主姓名</th>
                    <th>车主电话</th>
                    <th>车型</th>
                    <th>车牌号码</th>
                </tr>
                </thead>
                <tbody id="tbody-result">

                </tbody>
            </table>
        </div>
    </div>
    <!-- /.box -->
</section>


<script src="/static/plugins/jQuery/jquery-2.2.3.min.js"></script>
<script src="/static/plugins/jQuery/jquery.validate.js"></script>
<script src="/static/plugins/datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script src="/static/plugins/daterangepicker/moment.js"></script>
<script src="/static/plugins/daterangepicker/daterangepicker.js"></script>
<script src="/static/bootstrap/js/bootstrap.min.js"></script>
<script src="/static/plugins/layer/layer.js"></script>
<script>
    $(function () {

        var tbody = window.document.getElementById("tbody-result");
        $("#btn").click(function () {
            $("#serachForm").submit();
            console.log($("#time").val());
        });

        $("#serachForm").validate({
            errorElement: 'span',
            errorClass: 'text-danger',
            rules: {
                orderTime: {
                    required: true,
                }
            },
            messages: {
                orderTime: {
                    required: "请选择日期",
                }
            },
            submitHandler: function () {
                $.ajax({
                    url: "/order/json/test",
                    type: "get",
                    data: {
                        "orderTime": $("#time").val()
                    },
                    success: function (res) {
                        if (res.state == "success") {

                            console.log(res.result.list);
                            if (res.result.list.length>0) {
                                var item = res.result.list;
                                var str = '';
                                for (var i = 0; i <item.length; i++) {
                                    str += "<tr>" +
                                        "<td>" + item[i].id + "</td>" +
                                        "<td>" + item[i].car.customer.userName + "</td>" +
                                        "<td>" + item[i].car.customer.tel + "</td>" +
                                        "<td>" + item[i].car.carType + "</td>" +
                                        "<td>" + item[i].car.licenceNo + "</td>" +
                                        "</tr>";
                                }
                                tbody.innerHTML = str;
                            } else {
                                tbody.innerHTML='';
                                layer.msg("暂无信息!");
                            }
                        } else {
                            layer.msg("查询失败！", {icon: 2, time: 1000});
                        }
                    },
                    error: function () {
                        layer.alert("系统异常");
                    }
                })
            }
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
            autoUpdateInput: false,
            "locale": locale,
            "opens": "right",
            "timePicker": false
        }, function (start, end) {
            $('#time').val(start.format('YYYY-MM-DD') + " / " + end.format('YYYY-MM-DD'));
        });

        if ("${param.orderTime}") {
            $('#time').val("${param.orderTime}");
        }
        ;

    });


</script>

</body>
</html>
