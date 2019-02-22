package com.haohua.erp.controllor;    /*
 * @author  Administrator
 * @date 2018/7/28
 */

import com.haohua.erp.entity.Permission;
import com.haohua.erp.exception.ServiceException;
import com.haohua.erp.service.PremissionService;
import com.haohua.erp.shiro.MyShiroFilter;
import com.haohua.erp.util.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Controller
@RequestMapping("/manage/permission")
public class PremissionController {
    @Autowired
    private PremissionService premissionService;
    @Autowired
    private MyShiroFilter myShiroFilter;
    @GetMapping
    public String permissionList(Model model){
        List<Permission> permissions = premissionService.findPremissionList();
        model.addAttribute("permissions",permissions);
        return "/permission/permissionList";
    }
    @GetMapping("/add")
    public String addPermission(Model model){
        //可选的权限列表
        List<Permission> menuPermissionList = premissionService.findPremissionList();
        model.addAttribute("menuPermissionList",menuPermissionList);
        return  "permission/addPermission";
    }
    @PostMapping("/add")
    @ResponseBody
    public JsonResponse addPermission(Permission permission){
        premissionService.insertPermission(permission);
        myShiroFilter.updatePermission();
        return JsonResponse.success();
    }
    @GetMapping("/{permissionId:\\d+}/edit")
    public String editPermission(Model model,@PathVariable Integer permissionId) {
        Permission permission =premissionService.findById(permissionId);
        List<Permission> menu = premissionService.findPremissionList();
        List<Permission> menuPermissionList = premissionService.FilterPremissionList(permissionId,menu);
        model.addAttribute("menuPermissionList",menuPermissionList );
        model.addAttribute("permission",permission);
        return "permission/editPermission";
    }
    @PostMapping("/edit")
    @ResponseBody
    public JsonResponse editPermission(Permission permission){
        //修改权限
        premissionService.editPermission(permission);
        myShiroFilter.updatePermission();
        return JsonResponse.success();
    }
    @GetMapping("/{permissionId:\\d+}/del")
    @ResponseBody
    public JsonResponse delPermission(@PathVariable Integer permissionId){
        try{
            premissionService.delPermission(permissionId);
            myShiroFilter.updatePermission();
             return JsonResponse.success();
        }catch (ServiceException e){
            return JsonResponse.error(e.getMessage());
        }
    }
    @GetMapping("/check")
    @ResponseBody
    public boolean check(@RequestParam(required = false) String permissionName,
                         @RequestParam(required = false) String permissionCode,
                         @RequestParam(required = false) String url,
                         @RequestParam(required = false)Integer permissionId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("permissionName",permissionName);
        paramMap.put("permissionCode",permissionCode);
        paramMap.put("url",url);
            Permission permission = premissionService.findByParamMap(paramMap);
            if(permission==null){
                return true;
            }
            if (permission.getId().equals(permissionId)){
                return true;
            }
            return false;
    }
}
