package com.haohua.erp.serviceImp;    /*
 * @author  Administrator
 * @date 2018/7/27
 */

import com.haohua.erp.entity.Permission;
import com.haohua.erp.mapper.RolePermissionMapper;
import com.haohua.erp.service.RolePremissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RolePremissionServiceImpl implements RolePremissionService {
    @Autowired
    private RolePermissionMapper rolePermissionMapper ;
    /**
     * 根据角色id查询对应的权限
     * @param id
     * @return
     */
    @Override
    public List<Permission> findPermissionsByRoleId(Integer id) {
        return rolePermissionMapper.findPermissionByRoleId(id);
    }
}
