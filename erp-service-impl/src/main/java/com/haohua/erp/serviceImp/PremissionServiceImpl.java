package com.haohua.erp.serviceImp;    /*
 * @author  Administrator
 * @date 2018/7/27
 */

import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.haohua.erp.entity.Permission;
import com.haohua.erp.entity.PermissionExample;
import com.haohua.erp.entity.RolePermission;
import com.haohua.erp.entity.RolePermissionExample;
import com.haohua.erp.exception.NotFoundException;
import com.haohua.erp.exception.ServiceException;
import com.haohua.erp.mapper.PermissionMapper;
import com.haohua.erp.mapper.RolePermissionMapper;
import com.haohua.erp.service.PremissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import javax.sql.rowset.serial.SerialException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@Service
public class PremissionServiceImpl implements PremissionService {
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    /**
     * 查询所有的权限集合
     *
     * @return
     */
    @Override
    public List<Permission> findPremissionList() {
       List<Permission> permissionList= permissionMapper.selectByExample(null);
        List<Permission> endList = new ArrayList<>();
        treeList(permissionList, endList, 0);
        return endList;
    }
    /**
     * 将查询数据库的角色列表转换为树形集合结果
     * @param sourceList 数据库查询出的集合
     * @param endList 转换结束的结果集合
     * @param parentId 父ID
     */
    private void treeList(List<Permission> sourceList, List<Permission> endList, int parentId) {
        //从顶级菜单权限开始找，找到下一级的子权限集合放入templist
        List<Permission> tempList = Lists.newArrayList(Collections2.filter(sourceList, new com.google.common.base.Predicate<Permission>() {
            @Override
            public boolean apply(@Nullable Permission permission) {
                if (!permission.getPid().equals(parentId)) {
                    return false;
                } else {
                    return true;
                }
            }
        }));
        for(Permission permission : tempList) {
            //按照权限级别，按顺序从上到下添加权限集合
            endList.add(permission);
            treeList(sourceList,endList,permission.getId());
        }
    }
    /**
     * 新增权限
     * @param permission
     */
    @Override
    public void insertPermission(Permission permission) {
        permissionMapper.insertSelective(permission);
    }

    /**
     * 根据id 查找permission
     * @param permissionId
     * @return
     */
    @Override
    public Permission findById(Integer permissionId) {
        Permission permission = permissionMapper.selectByPrimaryKey(permissionId);
        //判断参数
        if(permission!=null){
                return permission;
        }else{
            throw  new NotFoundException("参数异常");
        }
    }
    /**
     * 根据表单对象 修改权限
     * @param permission
     */
    @Override
    public void editPermission(Permission permission) {
            permissionMapper.updateByPrimaryKeySelective(permission);
    }

    /**
     * 根据id删除 权限
     * @param permissionId
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void delPermission(Integer permissionId) {
        //判断该权限下有无子权限
        PermissionExample permissionExample = new PermissionExample();
        permissionExample.createCriteria().andPidEqualTo(permissionId);
       List<Permission> childPermissions = permissionMapper.selectByExample(permissionExample);
       if (childPermissions.isEmpty()){
        //判断该权限有无角色占用
           RolePermissionExample rolePermissionExample = new RolePermissionExample();
           rolePermissionExample.createCriteria().andPermissionIdEqualTo(permissionId);
           List<RolePermission> rolePermissions =rolePermissionMapper.selectByExample(rolePermissionExample);
           if (rolePermissions.isEmpty()){
            //删除该权限
               permissionMapper.deleteByPrimaryKey(permissionId);
           }else{
            throw  new ServiceException("该权限被角色占用不得删除");
           }
       }else{
            throw  new ServiceException("该权限下有子权限，不得删除");
       }
    }

    /**
     * 根据roleid查询对应的权限和该权限对应的所有父权限
     * 页面回显
     * @param roleId
     * @return
     */
    @Override
    public Map<Permission, Boolean> findRolePermissionMap(Integer roleId) {
        Map<Permission, Boolean> map = new LinkedHashMap<>();
        //查找所有的权限 经过排序的权限
        List<Permission> menuPermission= findPremissionList();

        //找到当前角色的权限
        List<Permission> permissionList =rolePermissionMapper.findPermissionByRoleId(roleId);

        for (Permission menu : menuPermission){
                boolean flag = false;
           for (Permission curr :permissionList){
                if (menu.getId().equals(curr.getId())){
                        flag=true;
                        break;
                }
           }
                map.put(menu,flag);
        }
        return map;
    }
    /**
     * 根据参数查询权限
     * @param paramMap
     * @return
     */
    @Override
    public Permission findByParamMap(Map<String, Object> paramMap) {
        return permissionMapper.findByParamMap(paramMap);
    }
    /**
     * 查找除其本身和其所有子权限 的其他权限集合
     * @return
     */
    @Override
    public List<Permission> FilterPremissionList(Integer permissionId, List<Permission> menuList) {

        List<Permission> menuPermission  = permissionMapper.selectByExample(null);
        //查找当前的权限
        Permission currPermission = permissionMapper.selectByPrimaryKey(permissionId);
        for(Permission permission:menuPermission){
            //若果列表包含有当前权限的子权限，则祛除
            //如果当前权限的id为其他权限的Pid，则存在子权限
            if((permission.getPid()).equals(currPermission.getId())){
                FilterPremissionList(permission.getId(),menuList);
            }
        }
        menuList.remove(currPermission);
        return menuList;
    }

}
