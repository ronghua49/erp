package com.haohua.erp.controllor;    /*
 * @author  Administrator
 * @date 2018/7/25
 */
import com.github.pagehelper.PageInfo;
import com.haohua.erp.entity.Parts;
import com.haohua.erp.entity.Type;
import com.haohua.erp.service.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
/**
 * parts类型的增删改查
 */
@Controller
@RequestMapping("/inventory/type")
public class TypeControllor {
    @Autowired
    private PartService partService;
    @GetMapping()
    public String addType(@RequestParam(defaultValue = "1",required = false) Integer p, Model model){

        PageInfo<Type> page =partService.findTypePage(p);
        model.addAttribute("page",page);
        return "type/typeManage";
    }
    @ResponseBody
    @GetMapping("/del/check")
    public boolean checkDelTypeName(String delName){
       List<Type> typeList = partService.findTypeByTypeName(delName);
        //根据类型查找零件
       if(!typeList.isEmpty()){
            Type type = (Type) typeList.toArray()[0];
            List<Parts> partsList = partService.findByTypeId(type.getId());
            if (!partsList.isEmpty()){
                return false;
            }else{return true;}
       }else{
            return false;
       }
    }
    @ResponseBody
    @PostMapping("/del")
    public Integer delType( String delName){
        return  partService.delTypeByTypeName(delName);
    }

    @ResponseBody
    @GetMapping("/add/check")
    public boolean checkAddTypeName(String addName){
        List<Type> typeList = partService.findTypeByTypeName(addName);
        if(!typeList.isEmpty()){
            return false;
        }
        return true;
    }
    @ResponseBody
    @PostMapping("/add")
    public Integer addType( String addName){
        Integer res = partService.addTypeByTypeName(addName);
        return  res;
    }

    @ResponseBody
    @GetMapping("/edit/check")
    public boolean checkEditTypeName(String editName,int typeId ){
        List<Type> typeList = partService.findTypeByTypeName(editName);
        Type type = (Type) typeList.toArray()[0];
        if(type!=null&&typeId!=type.getId()){
            return false;
        }
        return true;
    }
    @ResponseBody
    @PostMapping("/edit")
    public Integer editType( String editName,int editTypeId){
       Type type = new Type();
       type.setId(editTypeId);
       type.setTypeName(editName);
        Integer res = partService.editTypeByType(type);
        return  res;
    }
}
