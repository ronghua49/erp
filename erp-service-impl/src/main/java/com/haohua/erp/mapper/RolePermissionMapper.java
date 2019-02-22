package com.haohua.erp.mapper;

import com.haohua.erp.entity.Permission;
import com.haohua.erp.entity.RolePermission;
import com.haohua.erp.entity.RolePermissionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePermissionMapper {
    long countByExample(RolePermissionExample example);

    int deleteByExample(RolePermissionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RolePermission record);

    int insertSelective(RolePermission record);

    List<RolePermission> selectByExample(RolePermissionExample example);

    RolePermission selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RolePermission record, @Param("example") RolePermissionExample example);

    int updateByExample(@Param("record") RolePermission record, @Param("example") RolePermissionExample example);

    int updateByPrimaryKeySelective(RolePermission record);

    int updateByPrimaryKey(RolePermission record);

    /**
     *根据roleId 查询权限集合
     * @return
     */
    List<Permission> findPermissionByRoleId(Integer roleId);
}