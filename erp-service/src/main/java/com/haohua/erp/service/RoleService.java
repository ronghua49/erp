package com.haohua.erp.service;    /*
 * @author  Administrator
 * @date 2018/7/27
 */

import com.haohua.erp.entity.Role;

import java.util.List;

public interface RoleService {
    /**
     * 查询所有的角色集合
     * @return
     */
    List<Role> findRoleList();

    /**
     * 查询所有角色和角色的所有权限
     * @return
     */
    List<Role> findRoleListWithPermission();

    /**
     * 根据表单传过来的角色，和权限id数组添加角色
     * @param role
     * @param permissionId
     */
    void insertRole(Role role, Integer[] permissionId) ;

    /**
     * 根据id查询role对象
     * @param roleId
     * @return
     */
    Role findById(Integer roleId);
    /**
     * 根据角色和id权限的id数组修改角色
     * @param role
     * @param permissionId
     */
    void editRole(Role role, Integer[] permissionId);

    /**
     * 根据roleid删除对应的role
     * @param roleId
     */
    void delRole(Integer roleId);

    /**
     * 根据员工id查找对应的所有角色集合
     * @param employeeId
     * @return
     */
    List<Role> findRoleList(Integer employeeId);

    /**
     * 根据角色名查询
     * @param roleName
     * @return
     */
    Role findRole(String roleName);
}
