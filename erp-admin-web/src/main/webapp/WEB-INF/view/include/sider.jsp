<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<!-- 左侧菜单栏 -->
<aside class="main-sidebar">
    <section class="sidebar">
        <!-- 菜单 -->
        <ul class="sidebar-menu">
            <li class="header">系统功能</li>
            <!-- 保养服务 -->
            <shiro:hasRole name="car:fix">
            <li class="treeview">
                <a href="#">
                    <i class="fa fa-share-alt"></i> <span>保养服务</span>
                    <span class="pull-right-container">
                <i class="fa fa-angle-left pull-right"></i>
              </span>
                </a>
                <ul class="treeview-menu">
                    <li><a href="#"><i class="fa fa-circle-o"></i>历史订单查询</a></li>
                    <li><a href="#"><i class="fa fa-circle-o"></i>未完成订单查询</a></li>
                    <li><a href="#"><i class="fa fa-circle-o"></i>新建订单</a></li>
                </ul>
            </li>

            <!-- 维修服务 -->
            <li class="treeview">
                <a href="#">
                    <i class="fa fa-wrench"></i> <span>维修服务</span>
                    <span class="pull-right-container">
                <i class="fa fa-angle-left pull-right"></i>
              </span>
                </a>
                <ul class="treeview-menu">
                    <li><a href="#"><i class="fa fa-circle-o"></i>历史订单查询</a></li>
                    <li><a href="#"><i class="fa fa-circle-o"></i>未完成订单查询</a></li>
                    <li><a href="#"><i class="fa fa-circle-o"></i>新建订单</a></li>
                </ul>
            </li>
            </shiro:hasRole>
            
            <shiro:hasRole name="inventory:Manage">
            <!-- 库存管理 -->
            <li class="treeview ${param.menu == 'parts' ? 'active' : ''}">
                <a href="#">
                    <i class="fa fa-home active"></i> <span>库存管理</span>
                    <span class="pull-right-container">
                <i class="fa fa-angle-left pull-right"></i>
              </span>
                </a>
                <ul class="treeview-menu">
                    <li class="${param.menu == 'parts' ? 'active' : ''}"><a href="/inventory/parts"><i class="fa fa-circle-o"></i>配件管理</a></li>
                    <li class="${param.menu == 'type' ? 'active' : ''}"><a href="/inventory/type"><i class="fa fa-circle-o"></i>类型管理</a></li>
                    <li class="${param.menu == 'partsIn' ? 'active' : ''}"><a href="/inventory/parts/add"><i class="fa fa-circle-o"></i>配件入库</a></li>
                    <li class="${param.menu == 'inFind' ? 'active' : ''}"><a href="#"><i class="fa fa-circle-o"></i>入库查询</a></li>
                    <li class="${param.menu == 'outFind' ? 'active' : ''}"><a href="#"><i class="fa fa-circle-o"></i>出库查询</a></li>
                </ul>
            </li>
            </shiro:hasRole>

            <!-- 结算管理 -->
            <li class="treeview">
                <a href="#">
                    <i class="fa fa-jpy"></i> <span>结算管理</span>
                    <span class="pull-right-container">
                <i class="fa fa-angle-left pull-right"></i>
              </span>
                </a>
                <ul class="treeview-menu">
                    <li><a href="#"><i class="fa fa-circle-o"></i>订单结算</a></li>
                    <li><a href="#"><i class="fa fa-circle-o"></i>优惠券</a></li>
                </ul>
            </li>
            <!-- 统计报表 -->
            <li class="treeview">
                <a href="#">
                    <i class="fa fa-pie-chart"></i> <span>统计报表</span>
                    <span class="pull-right-container">
                <i class="fa fa-angle-left pull-right"></i>
              </span>
                </a>
                <ul class="treeview-menu">
                    <li><a href="#"><i class="fa fa-circle-o"></i>收入统计</a></li>
                </ul>
            </li>

            <shiro:hasAnyRoles name="admin:manage,boss">
            <li class="header active">系统管理</li>
            <!-- 部门员工管理 -->
            <li class="${param.menu == 'employee' ? 'active' : ''}"><a href="/manage/employee"><i class="fa fa-users"></i> <span>员工管理</span></a></li>
            <li  class="${param.menu == 'roles' ? 'active' : ''}"><a href="/manage/roles"><i class="fa fa-user-circle"></i> <span>角色管理</span></a></li>
            <li class="${param.menu == 'permission' ? 'active' : ''}"><a href="/manage/permission"><i class="fa fa-gavel"></i> <span>权限管理</span></a></li>
            </shiro:hasAnyRoles>
        </ul>
    </section>
    <!-- /.sidebar -->
</aside>

<!-- =============================================== -->
    