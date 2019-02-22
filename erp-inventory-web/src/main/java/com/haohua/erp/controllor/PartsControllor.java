package com.haohua.erp.controllor;    /*
 * @author  Administrator
 * @date 2018/7/23
 */

import com.github.pagehelper.PageInfo;
import com.haohua.erp.entity.*;
import com.haohua.erp.exception.NotFoundException;
import com.haohua.erp.exception.ServiceException;
import com.haohua.erp.service.PartService;
import com.haohua.erp.util.JsonResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.jws.WebParam;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/inventory/parts")
public class PartsControllor {
    Logger logger = LoggerFactory.getLogger(PartsControllor.class);
    @Autowired
    private PartService partService;
    /**
     * 根据零件id查询parts对象的详情
     * @param id  零件的id
     * @param model 向前端发送值的对象
     * @return parts的详情页
     * @throws Exception
     */
    @GetMapping("/{id:\\d+}")
    public String findPartsById(@PathVariable Integer id, Model model){
        try{
           Parts parts = partService.findById(id);
           model.addAttribute("parts",parts);
        }catch (NotFoundException e){
            model.addAttribute("message",e.getMessage());
        }
            return "parts/partDetail";

    }

    /**
     * 根据页码 查询parts的详情页
     * @param pageNo 要查看的parts对象的页码
     * @param model 向页面发送 对应的详情页面
     * @return List<parts> 对象集合
     */
    @GetMapping
    public String findPartPage(@RequestParam (name = "p",defaultValue = "1",required = false)Integer pageNo,
                                @RequestParam(required = false) String partsName,
                               @RequestParam(required = false) Integer partsType,
                               @RequestParam(required = false) String message,
                               Model model){
        Map<String ,Object> paramMap= new HashMap<>();
        paramMap.put("partsName",partsName);
        paramMap.put("partsType",partsType);

        PageInfo<Parts> page = partService.findWithTypeByPageNoAndMap(pageNo,paramMap);
        List<Type> typeList = partService.findTypeList();

        model.addAttribute("typeList",typeList);
        model.addAttribute("page",page);
        model.addAttribute("message",message);
        return "parts/partlist";
    }
    @GetMapping("/add")
    public String addPart(Model model){
        List<Type> typeList = partService.findTypeList();
        model.addAttribute("typeList",typeList);
        return "parts/addParts";
    }
    @PostMapping("/add")
    @ResponseBody
    public Integer addParts(Parts parts){
        Subject subject = SecurityUtils.getSubject();
        Employee employee = (Employee) subject.getPrincipal();
            //增加新零部件
        Integer res= partService.addNewParts(parts,employee.getId());
        logger.debug("{}新增了{}-{} {}个",employee.getEmployeeName(),parts.getPartsNo(),parts.getPartsName(),parts.getNum());
        return res ;
    }
    @GetMapping("/{partsId:\\d+}/del")
    public String delParts(@PathVariable Integer partsId,  RedirectAttributes redirectAttributes){
            Integer res =partService.delPartsById(partsId);
        System.out.println(res);
            if (res!=0){
                redirectAttributes.addFlashAttribute("message","删除成功");
            }else{
                redirectAttributes.addFlashAttribute("message","删除失败");
            }
            return "redirect:/inventory/parts";
    }
    @GetMapping("/{partsId:\\d+}/edit")
    public String editPartsById(@PathVariable Integer partsId ,Model model){
        Parts parts = partService.findById(partsId);
        List<Type> typeList = partService.findTypeList();
        if (parts!=null){
            model.addAttribute("parts",parts);
            model.addAttribute("typeList",typeList);
        }else{
            model.addAttribute("message","参数异常");
        }
        return  "parts/edit";
    }
    @PostMapping("/edit")
    @ResponseBody
    public Integer editPartsByParts(Parts parts){
            Integer res =partService.editPartsByParts(parts);
            return res;
    }
    @GetMapping("/{partsId:\\d+}/detail")
    public String partsDetail(@PathVariable Integer partsId,Model model){
        Parts parts = partService.findById(partsId);
        model.addAttribute("parts",parts);
        return "parts/partDetail";
    }

    /**
     *  查询所有的配件需求列表
     */
    @GetMapping("/fixTransParts")
    public String getFixTransParts(Model model){
        List<FixTransParts> fixTransPartsList = partService.findFixTransParts();
        for (FixTransParts fixTransParts: fixTransPartsList){
           List<FixTransParts> listVo = partService.findFixTransPartsByOrderId(fixTransParts.getOrderId());
            fixTransParts.setFixTransPartsListVo(listVo);
        }
        model.addAttribute("fixTransPartsList",fixTransPartsList);
        return "parts/newFixPartsList";
    }

    /**
     *  点击配件领取
     */
    @GetMapping("/{orderId:\\d+}/receive")
    @ResponseBody
    public JsonResponse receiveParts(@PathVariable Integer orderId){
        try{
            partService.receiveParts(orderId);
            return  JsonResponse.success();
        }catch (ServiceException e){
            return JsonResponse.error(e.getMessage());
        }
    }


    /**
     * 单个订单需求的配件详情
     * @param orderId
     * @param model
     * @return
     */
    @GetMapping("/{orderId:\\d+}/fixPartsDetail")
    public String getFixPartsDetail(@PathVariable Integer orderId, Model model){
        FixTransParts fixTransParts = partService.findFixTransPartsByOrderId(orderId).get(0);
        List<FixTransParts> fixTransPartsList = partService.findFixTransPartsByOrderId(orderId);
        model.addAttribute("fixTransParts",fixTransParts);
        model.addAttribute("fixTransPartsList",fixTransPartsList);
        return "parts/fixPartsDetail";
    }
    /**
     * 查询配件的出入库流水
     * @param type
     * @param model
     * @return
     */
    @GetMapping("/stream")
    public String getPartsStream(@RequestParam (required = false) Integer type,
                                 @RequestParam(required = false) String startTime ,
                                 @RequestParam(required = false) String endTime,
                                 @RequestParam(required = false) String partsNo,
                                 @RequestParam(required = false) Integer streamId,
                                 @RequestParam(required = false,defaultValue = "1") Integer p,
                                 Model model){
        Map<String,Object> paramMap = new LinkedHashMap<>();
        paramMap.put("type",type);
        paramMap.put("partsNo",partsNo);
        paramMap.put("streamId",streamId);
        paramMap.put("startTime",startTime);
        paramMap.put("endTime",endTime);
        PageInfo<PartsStream> page = partService.findPartStreamByParamMap(p,paramMap);
        model.addAttribute("page",page);
        return "parts/partsStream";
    }
}
