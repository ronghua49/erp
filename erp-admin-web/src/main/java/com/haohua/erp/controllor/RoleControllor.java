package com.haohua.erp.controllor;    /*
 * @author  Administrator
 * @date 2018/7/27
 */
import com.haohua.erp.entity.Permission;
import com.haohua.erp.entity.Role;
import com.haohua.erp.exception.ServiceException;
import com.haohua.erp.service.PremissionService;
import com.haohua.erp.service.RoleService;
import com.haohua.erp.shiro.MyShiroFilter;
import com.haohua.erp.util.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/manage/roles")
public class RoleControllor {
    @Autowired
    private RoleService roleService;
    @Autowired
    private PremissionService premissionService;
    @GetMapping
    public String roleList(Model model){
        List<Role> roleList = roleService.findRoleListWithPermission();
        model.addAttribute("roleList",roleList);
        return "/role/roleList";
    }
    @GetMapping("/add")

    public String addRole(Model model){
        List<Permission> permissionList =premissionService.findPremissionList();
        model.addAttribute("permissionList",permissionList);

        return  "role/addRole";
    }
    @PostMapping("/add")
    @ResponseBody
    public JsonResponse addRole(Role role,Integer[] permissionId){
            roleService.insertRole(role,permissionId);
            return JsonResponse.success();
    }
    @GetMapping("/{roleId:\\d+}/edit")
    public String editRole(Model model,@PathVariable Integer roleId){
            Role role = roleService.findById(roleId);
            model.addAttribute("role",role);
            //返回所有权限，若当前角色拥有的权限 则boolean值为true
            Map<Permission,Boolean> rolePermissionMap =premissionService.findRolePermissionMap(roleId);
            model.addAttribute("rolePermissionMap",rolePermissionMap);
            return  "role/editRole";
    }
    @PostMapping("/edit")
    @ResponseBody
    public JsonResponse editRole(Role role,Integer[] permissionId){
            //修改角色
            roleService.editRole(role,permissionId);
            return JsonResponse.success();
    }
    @GetMapping("/{roleId:\\d+}/del")
    @ResponseBody
    public JsonResponse delRole(@PathVariable Integer roleId){
        try{
            roleService.delRole(roleId);
            return JsonResponse.success();
        }catch (ServiceException e){
            return  JsonResponse.error(e.getMessage());
        }
    }

    @GetMapping("/check")
    @ResponseBody
    public boolean check(String roleName,@RequestParam(required = false) Integer roleId){
        Role role = roleService.findRole(roleName);
        if (role==null){
            return  true;
        }
        if (role.getId().equals(roleId)){
            return true;
        }
        return  false;

    }

}
