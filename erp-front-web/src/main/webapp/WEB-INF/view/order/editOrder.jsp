<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>车管家ERP-修改订单</title>

    <style>
        .td_title {
            font-weight: bold;
        }

        .table>tbody>tr>td {
            vertical-align: middle;
        }

    </style>
    <%@ include file="../include/css.jsp" %>
</head>
<body class="hold-transition skin-blue sidebar-mini" >
<!-- Site wrapper -->
<div class="wrapper">
    <%@ include file="../include/header.jsp" %>
    <jsp:include page="../include/sider.jsp">
        <jsp:param name="menu" value="order"/>
    </jsp:include>

    <!-- 右侧内容部分 -->
    <div class="content-wrapper" id="app">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1 style="text-align: center">
                保养维修单
            </h1>
        </section>

        <!-- Main content -->
        <section class="content">

            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">客户车辆信息</h3>
                </div>
                <div class="box-body">
                    <table class="table">
                        <tr>
                            <td class="td_title">车牌号:</td>
                            <td style="width: 280px">
                                <input type="text" id="carLisence" class="form-control" v-model="car.licenceNo">

                            </td>
                            <td >
                            <span class="input-group-btn">
                                <button type="button" class="btn btn-info btn-flat" id="search" @click = "search"><i class="fa fa-search"></i></button>
                            </span>
                            </td>
                            <td class="td_title">客户姓名:</td>
                            <td>{{customer.userName}}</td>
                            <td class="td_title">身份证号:</td>
                            <td id="idCard">{{customer.idCard}}</td>
                        </tr>
                        <tr>
                            <td class="td_title">车主电话:</td>
                            <td id="tel">{{customer.tel}}</td>
                            <td></td>
                            <td class="td_title">车型:</td>
                            <td id="carType">{{car.carType}}</td>
                            <td class="td_title">车辆识别码:</td>
                            <td id="carNo">{{car.carNo}}</td>

                        </tr>
                    </table>

                </div>
            </div>
            <div class="box">
                <div class="box-header">
                    <h3 class="box-title">项目选择</h3>
                </div>

                <div class="box-body">
                    <div class="form-inline">
                        <select class="form-control" name="type"  v-model="serviceTypeSelect">
                            <option value="">请选择项目</option>
                            <option v-for="item in serviceTypeList" :value="item">{{item.serviceName}}</option>
                        </select>
                    </div>
                    <br>
                    <table class="table table-bordered " style="border-width: 2px;" id="infoForm">
                        <thead>
                        <tr>
                            <th>项目代码</th>
                            <th>项目名称</th>
                            <th>工时费用</th>
                        </tr>
                        </thead>
                        <tbody id="addTr">
                        <tr v-if="serviceTypeSelect">
                            <td>{{serviceTypeSelect.serviceNo}}</td>
                            <td>{{serviceTypeSelect.serviceName}}</td>
                            <td>{{serviceTypeSelect.serviceHour*50}}</td>
                        </tr>
                        </tbody>
                        <tfoot>
                        <tr>
                            <td colspan="4" class="td_title">小计 ：{{hourFee}} 元</td>
                        </tr>
                        </tfoot>
                    </table>
                </div>
            </div>

            <div class="box">
                <div class="box-header">
                    <h3 class="box-title">选择配件</h3>
                </div>

                <div class="box-body">

                    <div class="form-inline">
                        <select class="form-control" name="type" id="type" v-model="selectedTypeId" @change="changePartsList">
                            <option value="" disabled>请选择配件类型</option>
                            <option v-for="item in typeList" :value="item.id">{{item.typeName}}</option>
                        </select>
                        <select class="form-control" name="parts" id="parts" v-model="partsSelect">
                            <option value="" disabled>请选择配件</option>
                            <option v-for="item in partsList":value="item">{{item.partsName}}- {{item.partsNo}}</option>
                        </select>
                        <button class="btn btn-info" @click="addParts">选择</button>
                    </div>
                    <br>
                    <table class="table table-bordered " style="border-width: 2px;" id="partsInfoForm">
                        <thead>
                        <tr>
                            <th>编号</th>
                            <th>名称</th>
                            <th>单价</th>
                            <th>数量</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr v-for="item in choosePartsList">
                            <td>{{item.partsNo}}</td>
                            <td>{{item.partsName}}</td>
                            <td>{{item.salePrice}}</td>
                            <td><button type="button" class="btn btn-box-tool" @click="minus(item)"><i class="fa fa-minus"></i></button>
                                <input type="text" v-model="item.num" style="width:30px">
                                <button type="button" class="btn btn-box-tool" @click="add(item)"><i class="fa fa-plus"></i></button></td>
                        </tr>
                        </tbody>
                        <tfoot>
                        <tr>
                            <td colspan="4" class="td_title">小计 ：{{partsFee}}元</td>
                        </tr>
                        </tfoot>
                    </table>
                </div>
            </div>

            <div class="box">
                <div class="box-header">
                    <h4>总计： {{fee}} 元</h4>
                </div>
            </div>

            <button class="btn btn-info btn-block btn-lg" @click="editOrder">确认修改</button>

            <div class="modal fade" tabindex="-1" role="dialog" id="addUserModal">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title">添加用户</h4>
                        </div>
                        <div class="modal-body">
                            <form id="addCarForm" class="form">
                                <div class="form-group">
                                    <label>车牌号码：</label>
                                    <input type="text" id="newCarLisence" name="licenceNo" class="form-control" v-model="car.licenceNo">
                                </div>
                                <div class="form-group">
                                    <label>车辆识别码：</label>
                                    <input type="text" name="carNo" class="form-control">
                                </div>
                                <div class="form-group">
                                    <label>车辆型号</label>
                                    <input type="text" name="carType" class="form-control">
                                </div>
                                <div class="form-group">
                                    <label>车主姓名：</label>
                                    <input type="text" name="userName" class="form-control">
                                </div>
                                <div class="form-group">
                                    <label>车主身份证：</label>
                                    <input type="text" name="idCard" class="form-control">
                                </div>
                                <div class="form-group">
                                    <label>车主电话：</label>
                                    <input type="text" name="tel" class="form-control">
                                </div>
                                <div class="form-group">
                                    <label>颜色：</label>
                                    <input type="text" name="color" class="form-control">
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                            <button type="button" class="btn btn-primary" id="addCar" @click="addCarInfo">添加</button>
                        </div>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
    <%@ include file="../include/footer.jsp" %>
</div>
<!-- ./wrapper -->
<%@ include file="../include/js.jsp" %>
<script src="/static/plugins/vue/vue.js"></script>
<script>
    $(function() {
        var orderId = ${orderId};
        var vm = new Vue({
            el:"#app",
            data:{
                car:{},
                customer:{},
                serviceTypeList:[],
                typeList:[],
                serviceTypeSelect:'',
                partsList:[],
                selectedTypeId:'',
                partsSelect:{},
                choosePartsList:[],
                partsNum:''
            },
            methods:{
                search: function () {
                    var licenceNo = this.car.licenceNo;
                    if(licenceNo){
                        $.get("/car/search",{"licenceNo":licenceNo}).done(function(res){
                            if (res.state == 'success') {
                                // 如果已存在显示车辆信息
                                vm.car = res.result;
                                vm.customer = res.result.customer;
                            } else {
                                // 如果不存在则打开模态框新增车辆信息
                                $("#addUserModal").modal({
                                    show:true,
                                    backdrop:'static'
                                });
                            }
                        }).fail(function(){
                            layer.msg("系统异常")
                        })
                    }else{
                        layer.msg("请输入车牌号码");
                    }
                },
                changePartsList: function () {
                    if(this.selectedTypeId){
                        $.get("/order/"+this.selectedTypeId+"/parts").done(function(res){
                            for(var i = 0; i < res.result.length; i++) {
                                res.result[i].num = 1;
                            }
                            vm.partsList= res.result;
                            vm.partsSelect=null;
                        }).error(function(err){
                            layer.msg("系统异常");
                        });
                    }else{
                        vm.partsList=null;
                    }
                },
                addCarInfo: function () {
                    $.post("/car/new",$("#addCarForm").serialize()).done(function(res){
                        if(res.state=="success"){
                            vm.car= res.result;
                            vm.customer=res.result.customer;
                            $("#addUserModal").modal('hide');
                        }
                    }).fail(function(){
                        $("#addUserModal").modal('hide');
                        layer.msg("系统异常")
                    });
                },
                addParts: function(){
                    if(this.partsSelect.partsNo){
                        var flag= true;
                        for(var i = 0; i < this.choosePartsList.length; i++) {
                            if(this.partsSelect == this.choosePartsList[i]) {
                                this.choosePartsList[i].num = this.choosePartsList[i].num + 1;
                                flag=false;
                                break;
                            }
                        }
                        if(flag){
                            this.choosePartsList.push(this.partsSelect);
                        }
                    }
                },
                minus: function (item) {
                    if(item.num>1){
                        item.num--;
                    }else{
                        var index = this.choosePartsList.indexOf(item);
                        this.choosePartsList.splice(index,1);
                    }
                },
                add: function(item){
                    item.num++;
                },
                editOrder:function(){
                    if(!this.car.id) {
                        layer.msg("请填写车辆信息");
                        return;
                    }

                    if(!this.serviceTypeSelect) {
                        layer.msg("请选择项目类型");
                        return;
                    }

                    if(!this.choosePartsList.length) {
                        layer.msg("请选择配件");
                        return;
                    }

                    var partsLists = [];
                    for(var i = 0; i < this.choosePartsList.length; i++){
                        var parts = {};
                        parts.id = this.choosePartsList[i].id;
                        parts.num = this.choosePartsList[i].num;
                        partsLists.push(parts);
                    }

                    $.ajax({ // axios
                        type: "POST",
                        url: "/order/edit",
                        data: {
                            json: JSON.stringify({
                                "orderId":orderId,
                                "carId": vm.car.id,
                                "serviceTypeId": vm.serviceTypeSelect.id,
                                "fee": vm.fee,
                                "partsLists":partsLists
                            })
                        },
                        success: function(json){
                            if(json.state == "success") {
                                layer.msg("修改成功！",{icon:1,time:1500},function () {
                                    window.location.href="/order/search";
                                });


                            }else {
                                layer.msg(json.result);
                            }
                        },
                        error:function () {
                            layer.msg("系统异常");
                        }
                    });
                }
            },
            mounted: function () {
                $.get("/order/serviceType").done(function (res) {
                    vm.serviceTypeList = res.result;
                }).error(function () {
                    layer.msg("系统异常");
                });
                $.get("/order/partsType").done(function (res) {
                    vm.typeList = res.result;
                }).error(function () {
                    layer.msg("系统异常");
                });
                $.get("/order/"+orderId+"/edit").done(function (res) {
                    if(res.state=='success'){
                        vm.car = res.result.car,
                        vm.customer = res.result.car.customer,
                        vm.serviceTypeSelect = res.result.chooseServiceType,
                        vm.choosePartsList = res.result.choosePartsList
                    }else{
                        layer.msg("获取订单信息失败");
                    }
                }).error(function () {
                    layer.msg("系统异常");
                });

            },
            computed: {
                hourFee: function () {
                    if(this.serviceTypeSelect){
                        return (this.serviceTypeSelect.serviceHour)*50
                    }else{
                        return 0;
                    }
                },
                partsFee: function () {
                    var sum=0;
                   // if(vm.choosePartsList.length){
                        for(var i = 0;i<vm.choosePartsList.length;i++){
                            sum+=this.choosePartsList[i].salePrice*this.choosePartsList[i].num
                        }
                        return sum
                  /*  }else{
                        return sum;
                    }*/
                },
                fee: function () {
                    return this.partsFee+this.hourFee ? this.partsFee+this.hourFee : 0 ;
                }
            },
        });
    })
</script>
</body>
</html>
