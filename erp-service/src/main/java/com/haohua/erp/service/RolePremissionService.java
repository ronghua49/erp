package com.haohua.erp.service;    /*
 * @author  Administrator
 * @date 2018/7/27
 */

import com.haohua.erp.entity.Permission;

import java.util.List;
import java.util.Map;

public interface RolePremissionService {

    /**
     * 根据角色id查询对应的权限
     * @param id
     * @return
     */
    List<Permission> findPermissionsByRoleId(Integer id);
}
