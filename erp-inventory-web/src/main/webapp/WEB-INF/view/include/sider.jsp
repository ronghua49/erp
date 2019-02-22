<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 左侧菜单栏 -->
<aside class="main-sidebar">
    <section class="sidebar">
        <!-- 菜单 -->
        <!-- 菜单 -->
        <ul class="sidebar-menu">
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
                    <li class="${param.menu == 'stream' ? 'active' : ''}"><a href="/inventory/parts/stream"><i class="fa fa-circle-o"></i>配件流水查询</a></li>
                    <li class="${param.menu == 'fix_parts' ? 'active' : ''}"><a href="/inventory/parts/fixTransParts"><i class="fa fa-circle-o"></i>维修配件订单列表</a></li>
                </ul>
            </li>
        </ul>
    </section>
    <!-- /.sidebar -->
</aside>

<!-- =============================================== -->
    