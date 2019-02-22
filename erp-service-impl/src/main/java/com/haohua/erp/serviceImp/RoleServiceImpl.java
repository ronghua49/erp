package com.haohua.erp.serviceImp;    /*
 * @author  Administrator
 * @date 2018/7/27
 */

import com.haohua.erp.entity.*;
import com.haohua.erp.exception.NotFoundException;
import com.haohua.erp.exception.ServiceException;
import com.haohua.erp.mapper.EmployeeRoleMapper;
import com.haohua.erp.mapper.PermissionMapper;
import com.haohua.erp.mapper.RoleMapper;
import com.haohua.erp.mapper.RolePermissionMapper;
import com.haohua.erp.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    @Autowired
    private EmployeeRoleMapper employeeRoleMapper;

    /**
     * 查询所有的角色集合
     *
     * @return
     */
    @Override
    public List<Role> findRoleList() {
        RoleExample roleExample= new RoleExample();
        List<Role> roleList =  roleMapper.selectByExample(roleExample);
        return roleList;
    }

    /**
     * 查询所有角色和角色的所有权限
     *
     * @return
     */
    @Override
    public List<Role> findRoleListWithPermission() {
        return roleMapper.selectRolesWithPermission();
    }
    /**
     * 根据表单传过来的角色，和权限id数组添加角色
     * @param role
     * @param permissionId
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void insertRole(Role role, Integer[] permissionId) {
        //添加角色
        roleMapper.insertSelective(role);
        System.out.println(role.getId());
        //添加权限的对应关系表
        for(Integer id : permissionId){
            RolePermission rolePermission = new RolePermission();
            rolePermission.setPermissionId(id);
            rolePermission.setRoleId(role.getId());
            rolePermissionMapper.insert(rolePermission);
        }
    }
    /**
     * 根据id查询role对象
     * @param roleId
     * @return
     */
    @Override
    public Role findById(Integer roleId) {
         Role role = roleMapper.selectByPrimaryKey(roleId);
         if (role!=null){
             return  role;
         }else{
             throw new NotFoundException("参数异常");
         }
    }

    /**
     * 根据角色和id权限的id数组修改角色
     * @param role
     * @param permissionId
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void editRole(Role role, Integer[] permissionId) {
        //更新角色
        roleMapper.updateByPrimaryKeySelective(role);
        //删除角色原始权限关系
        RolePermissionExample rolePermissionExample = new RolePermissionExample();
        rolePermissionExample.createCriteria().andRoleIdEqualTo(role.getId());
        rolePermissionMapper.deleteByExample(rolePermissionExample);
        //新增角色的关联关系
        for(Integer id :permissionId){
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(role.getId());
            rolePermission.setPermissionId(id);
            rolePermissionMapper.insert(rolePermission);
        }
    }

    /**
     * 根据roleid删除对应的role
     * @param roleId
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void delRole(Integer roleId) {
        //判断id是否篡改
        Role role = roleMapper.selectByPrimaryKey(roleId);
        if (role!=null){
            //判断角色是否有员工所占用
            EmployeeRoleExample employeeRoleExample = new EmployeeRoleExample();
            employeeRoleExample.createCriteria().andRoleIdEqualTo(roleId);
             List<EmployeeRole> employeeRoles =employeeRoleMapper.selectByExample(employeeRoleExample);
             if (employeeRoles.isEmpty()){
                //删除角色
                 roleMapper.deleteByPrimaryKey(roleId);
                 //删除角色和权限的关系表
                 RolePermissionExample rolePermissionExample = new RolePermissionExample();
                 rolePermissionExample.createCriteria().andRoleIdEqualTo(roleId);
                 rolePermissionMapper.deleteByExample(rolePermissionExample);
             }else{
                 throw  new ServiceException("该角色被员工占用，不得删除");
             }
        }else{
            throw  new NotFoundException("参数异常");
        }
    }

    /**
     * 根据员工id查找对应的所有角色集合
     * @param employeeId
     * @return
     */
    @Override
    public List<Role> findRoleList(Integer employeeId) {
        return roleMapper.findRolesByEmpId(employeeId);
    }

    /**
     * 根据角色名查询
     * @param roleName
     * @return
     */
    @Override
    public Role findRole(String roleName) {
        RoleExample roleExample = new RoleExample();
        roleExample.createCriteria().andRoleNameEqualTo(roleName);
        List<Role> roleList = roleMapper.selectByExample(roleExample);
        if (!roleList.isEmpty()){
            return  roleList.get(0);
        }
        return  null;


    }
}
