package com.haohua.erp.controllor;    /*
 * @author  Administrator
 * @date 2018/7/26
 */

import com.github.pagehelper.PageInfo;
import com.haohua.erp.entity.Employee;
import com.haohua.erp.entity.Role;
import com.haohua.erp.service.EmployeeService;
import com.haohua.erp.service.RoleService;
import com.haohua.erp.shiro.MyShiroFilter;
import com.haohua.erp.util.JsonResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/manage/employee")
public class EmployeeControllor {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private RoleService  roleService;
    @Autowired
    private MyShiroFilter myShiroFilter;
    @GetMapping
    public String employeeList(@RequestParam(defaultValue = "1",required = false) Integer p,
                               @RequestParam(required = false) String employeeNameOrTel,
                               @RequestParam(required = false) Integer roleId,
                               @RequestParam(required = false) Integer state,
                               Model model){
        Map<String,Object> paramMap = new HashMap<>();
        String employeeName=null;
        String employeeTel=null;
        if(StringUtils.isNumeric(employeeNameOrTel)){
            employeeTel=employeeNameOrTel;
        }else{
            employeeName=employeeNameOrTel;
        }
        paramMap.put("employeeName",employeeName);
        paramMap.put("state",state);
        paramMap.put("employeeTel",employeeTel);
        paramMap.put("roleId",roleId);
        PageInfo<Employee> page = employeeService.fingByParamMap(p,paramMap);
        //查询所有的角色集合
        List<Role> roleList = roleService.findRoleList();
        model.addAttribute("roleList",roleList);
        model.addAttribute("page",page);
        return "employee/employeeList";
    }
    @GetMapping("/add")
    public String addEmployee(Model model){
        List<Role> roleList = roleService.findRoleList();
        model.addAttribute("roleList",roleList);
        return "employee/addEmployee";
    }
    @PostMapping("/add")
    @ResponseBody
    public JsonResponse employeeAdd(Employee employee, Integer[] roleIds){
         Integer res = employeeService.addEmployee(employee,roleIds);
            if (res==1){
                myShiroFilter.updatePermission();
                return JsonResponse.success();
            }
            return JsonResponse.error();
    }
    @GetMapping("/{employeeId:\\d+}/del")
    public String delEmployee(@PathVariable Integer employeeId, RedirectAttributes redirectAttributes){
        Integer res = employeeService.delEmployee(employeeId);
        if (res==1){
            redirectAttributes.addFlashAttribute("message","删除成功！");
        }else {
            redirectAttributes.addAttribute("message","删除失败！");
        }
        return "redirect:/manage/employee";
    }
    @GetMapping("/{employeeId:\\d+}/edit")
    public String editEmployee(@PathVariable Integer employeeId,Model model ){
            Employee employee = employeeService.findById(employeeId);
            List<Role> roleList = roleService.findRoleList();
            List<Role> currEmpRoles = roleService.findRoleList(employeeId);
            model.addAttribute("currEmpRoles",currEmpRoles);
            model.addAttribute("roleList",roleList);
            model.addAttribute("employee",employee);
             return "employee/editEmployee";
    }
    @PostMapping("/{employeeId:\\d+}/edit")
    @ResponseBody
    public JsonResponse employeeEdit(Employee employee,Integer[] roleIds){
        try{
            employeeService.editEmployee(employee,roleIds);
            myShiroFilter.updatePermission();
            return JsonResponse.success();
        }catch (Exception e){
            e.printStackTrace();
            return JsonResponse.error();
        }

    }
    @GetMapping("/check")
    @ResponseBody
    public boolean check(@RequestParam(required = false) Integer employeeId,
                         @RequestParam String  employeeTel){
            Employee employee = employeeService.findByTel(employeeTel);
          if (employee==null){
              return true;
          }
          if (employee.getId().equals(employeeId)){
            return  true;
          }
          return false;
    }
    @RequestMapping("/{employeeId:\\d+}/lock")
    public String lock(@PathVariable Integer employeeId){
        Employee employee = employeeService.findById(employeeId);
        if (employee.getState().equals(Employee.NORMAL_STATE)){
            employee.setState(Employee.PROHIBIT_STATE);
            employeeService.updateEmployee(employee);
        }else {
            employee.setState(Employee.NORMAL_STATE);
            employeeService.updateEmployee(employee);
        }
        return "redirect:/manage/employee";
    }

}
