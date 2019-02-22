package com.haohua.erp.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * employee
 * @author 
 */
public class Employee implements Serializable {
    public static final  Integer NORMAL_STATE=1;
    public static final  Integer PROHIBIT_STATE=2;
    /**
     * 员工ID
     */
    private Integer id;

    private String employeeTel;

    /**
     * 员工姓名
     */
    private String employeeName;


    /**
     * 密码
     */
    private String password;


    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 状态：1.禁用 2启用
     */
    private Integer state;
    private List<Role> roleList;
    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmployeeTel() {
        return employeeTel;
    }

    public void setEmployeeTel(String employeeTel) {
        this.employeeTel = employeeTel;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", employeeTel=" + employeeTel +
                ", employeeName='" + employeeName + '\'' +
                ", password='" + password + '\'' +
                ", createTime=" + createTime +
                ", state=" + state +
                ", roleList=" + roleList +
                '}';
    }
}