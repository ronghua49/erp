package com.haohua.erp.serviceImp;    /*
 * @author  Administrator
 * @date 2018/7/26
 */

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.haohua.erp.entity.*;
import com.haohua.erp.exception.ServiceException;
import com.haohua.erp.mapper.EmployeeLoginLogMapper;
import com.haohua.erp.mapper.EmployeeMapper;
import com.haohua.erp.mapper.EmployeeRoleMapper;
import com.haohua.erp.service.EmployeeService;
import com.haohua.erp.util.Constant;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Service
public class EmployeeServiceImpl implements EmployeeService {

    Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private EmployeeLoginLogMapper employeeLoginLogMapper;
    @Autowired
    private EmployeeRoleMapper employeeRoleMapper;
    /**
     * 根据用户电话号码查询用户
     *
     * @param userTel
     * @return
     */
    @Override
    public Employee findByTelAndPassword(String userTel,String password,String loginIp) {
        EmployeeExample employeeExample = new EmployeeExample();
        employeeExample.createCriteria().andEmployeeTelEqualTo(userTel);
        List<Employee> employeeList = employeeMapper.selectByExample(employeeExample);
        //如果用户名存在并且密码加密后匹配
        if(!employeeList.isEmpty()&&DigestUtils.md5Hex(employeeList.get(0).getPassword()).equals(password)){
            Employee employee = employeeList.get(0);
            //判断用户状态是否正常
            if (employee.getState().equals(Employee.NORMAL_STATE)){
                //记录员工登录流水
                EmployeeLoginLog employeeLoginLog = new EmployeeLoginLog();
                employeeLoginLog.setEmployeeId(employee.getId());
                employeeLoginLog.setLoginIp(loginIp);
                employeeLoginLogMapper.insertSelective(employeeLoginLog);
                //记录控制台员工登录流水
                logger.debug("{}-{}在{}登录",employee.getEmployeeName(),userTel,new Date());
                return employee;
            }else{
                throw new ServiceException("状态异常，不得登入");
            }
        }else{
            throw new ServiceException("用户名或者密码错误");
        }
    }

    /**
     * 查询员工信息列表
     *
     * @param p        页码
     * @param paramMap 参数条件
     * @return
     */
    @Override
    public PageInfo<Employee> fingByParamMap(Integer p, Map<String, Object> paramMap) {
        PageHelper.startPage(p,Constant.DEFAULT_PAGESIZA);
        List<Employee> employeeList = employeeMapper.selectWithRoleByMap(paramMap);

        for(Employee employee:employeeList){
            System.out.println(employee);
        }
        return new PageInfo<>(employeeList);
    }

    /**
     * 增加员工
     *
     * @param employee 封装的表单对象
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Integer addEmployee(Employee employee,Integer[] roleIds) {

        //增加事务
        try{
            String password = DigestUtils.md5Hex(employee.getPassword());
            employee.setPassword(password);
            employeeMapper.insertSelective(employee);
            int employeeId = employee.getId();
            for(Integer id:roleIds){
                EmployeeRole employeeRole = new EmployeeRole();
                employeeRole.setEmployeeId(employeeId);
                employeeRole.setRoleId(id);
                employeeRoleMapper.insert(employeeRole);
            }
            return 1;
        }catch (Exception e){
            return 0;
        }
    }
    /**
     * 删除员工
     *
     * @param employeeId 员工id
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Integer delEmployee(Integer employeeId) {
        try{
             employeeMapper.deleteByPrimaryKey(employeeId);
            EmployeeRoleExample employeeRoleExample = new EmployeeRoleExample();
            employeeRoleExample.createCriteria().andEmployeeIdEqualTo(employeeId);
             employeeRoleMapper.deleteByExample(employeeRoleExample);
             return 1;
        }catch (Exception e){
            return 0;
        }
    }

    /**
     * 根据员工id查询员工信息
     *
     * @param employeeId
     * @return
     */
    @Override
    public Employee findById(Integer employeeId) {
        Map<String,Object> map = new HashMap<>();
        map.put("employeeId",employeeId);
       List<Employee>  employeeList = employeeMapper.selectWithRoleByMap(map);
        if (employeeList!=null){
            return employeeList.get(0);
        }else {
            throw  new ServiceException("参数异常");
        }

    }
    /**
     * @param employee
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void editEmployee(Employee employee,Integer[] roleIds) {

            //更新员工基本信息
        employeeMapper.updateByPrimaryKeySelective(employee);
            //删除初始的员工角色
        EmployeeRoleExample employeeRoleExample = new EmployeeRoleExample();
        employeeRoleExample.createCriteria().andEmployeeIdEqualTo(employee.getId());
        employeeRoleMapper.deleteByExample(employeeRoleExample);
            //增加新角色
        for(Integer id : roleIds){
            EmployeeRole employeeRole = new EmployeeRole();
            employeeRole.setRoleId(id);
            employeeRole.setEmployeeId(employee.getId());
            employeeRoleMapper.insert(employeeRole);
        }

    }

    /**
     * 根据电话号码查询员工
     *
     * @param employeeTel
     * @return
     */
    @Override
    public Employee findByTel(String employeeTel) {
        EmployeeExample employeeExample = new EmployeeExample();
        employeeExample.createCriteria().andEmployeeTelEqualTo(employeeTel);
        List<Employee> employeeList = employeeMapper.selectByExample(employeeExample);
        if (!employeeList.isEmpty()){
            return employeeList.get(0);
        }
        return null;
    }

    /**
     * 更新员工
     * @param employee
     */
    @Override
    public void updateEmployee(Employee employee) {
        employeeMapper.updateByPrimaryKeySelective(employee);
    }

    /**
     * 保存登录日志到数据库
     * @param employeeLoginLog
     */
    @Override
    public void saveLoginLog(EmployeeLoginLog employeeLoginLog) {
        employeeLoginLogMapper.insertSelective(employeeLoginLog);
    }

}
