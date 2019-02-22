package com.haohua.erp.service;    /*
 * @author  Administrator
 * @date 2018/7/26
 */

import com.github.pagehelper.PageInfo;
import com.haohua.erp.entity.Employee;
import com.haohua.erp.entity.EmployeeLoginLog;

import java.util.Map;

public interface EmployeeService {
    /**
     * 根据用户电话号码查询用户
     * @param userTel
     * @return
     */
    Employee findByTelAndPassword(String userTel,String password,String loginIp);

    /**
     * 查询员工信息列表
     * @param p 页码
     * @param paramMap 参数条件
     * @return
     */
    PageInfo<Employee> fingByParamMap(Integer p, Map<String,Object> paramMap);

    /**
     * 增加员工
     * @param employee  封装的表单对象
     * @param roleId 当前员工的角色id数组
     * @return
     */
    Integer addEmployee(Employee employee,Integer[] roleId);

    /**
     * 删除员工
     * @param employeeId 员工id
     * @return
     */
    Integer delEmployee(Integer employeeId);

    /**
     * 根据员工id查询员工信息
     * @param employeeId
     * @return
     */
    Employee findById(Integer employeeId);

    /**
     *
     * @param employee 要更新表单的员工对象
     * @param roleIds 所担任的角色id数组
     * @return
     */
    void editEmployee(Employee employee,Integer[] roleIds);

    /**
     * 根据电话号码查询员工
     * @param employeeTel
     * @return
     */
    Employee findByTel(String employeeTel);

    /**
     * 更新员工
     * @param employee
     */
    void updateEmployee(Employee employee);

    /**
     * 保存登录日志到数据库
     * @param employeeLoginLog
     */
    void saveLoginLog(EmployeeLoginLog employeeLoginLog);
}
