package com.haohua.erp.service;    /*
 * @author  Administrator
 * @date 2018/7/27
 */

import com.haohua.erp.entity.Permission;

import java.util.List;
import java.util.Map;

public interface PremissionService {
    /**
     * 查询所有的权限集合
     * @return
     */
    List<Permission> findPremissionList();

    /**
     * 新增权限
     * @param permission
     */
    void insertPermission(Permission permission);

    /**
     * 根据id 查找permission
     * @param permissionId
     * @return
     */
    Permission findById(Integer permissionId);

    /**
     * 根据表单对象 修改权限
     * @param permission
     */
    void editPermission(Permission permission);

    /**
     * 根据id删除 权限
     * @param permissionId
     */
    void delPermission(Integer permissionId);
    /**
     * 根据roleid查询对应的权限和该权限对应的父权限
     * @param roleId
     * @return
     */
    Map<Permission,Boolean> findRolePermissionMap(Integer roleId);

    /**
     * 根据参数查询权限
     * @param paramMap
     * @return
     */
    Permission findByParamMap(Map<String,Object> paramMap);

    /**
     * 查找除其本身和其所有子权限 的其他权限集合
     * @return
     */
    List<Permission> FilterPremissionList(Integer permissionId,List<Permission> menuPermission);
}
