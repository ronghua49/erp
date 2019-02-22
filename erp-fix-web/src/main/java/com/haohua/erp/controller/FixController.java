package com.haohua.erp.controller;    /*
 * @author  Administrator
 * @date 2018/8/7
 */

import com.alibaba.dubbo.config.annotation.Reference;
import com.haohua.erp.entity.Employee;
import com.haohua.erp.entity.FixOrder;
import com.haohua.erp.exception.ServiceException;
import com.haohua.erp.service.FixOrderService;
import com.haohua.erp.util.JsonResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebParam;
import java.util.List;

@Controller
@RequestMapping("/fix")
public class FixController {

    @Autowired
    private FixOrderService fixOrderService;



    @GetMapping("/new")
   public String newFixOrder(Model model){
        List<FixOrder> fixOrderList = fixOrderService.findFixOrderListWithParts();
        model.addAttribute("fixOrderList",fixOrderList);
        return "fix/fixList";
   }

    @GetMapping("/{orderId:\\d+}/accept")
    @ResponseBody
    public JsonResponse acceptOrder(@PathVariable Integer orderId){
        Subject subject = SecurityUtils.getSubject();
        Employee employee = (Employee) subject.getPrincipal();

        try{
            fixOrderService.acceptOrder(orderId,employee);
            fixOrderService.countTaskTime(orderId,employee.getId());
            return  JsonResponse.success();
        }catch (ServiceException e){
            return  JsonResponse.error(e.getMessage());
        }
    }

    /*@GetMapping("/{orderId:\\d+}/detail")
    public String fixOrderDetail(@PathVariable Integer orderId,Model model){
        Subject subject = SecurityUtils.getSubject();
        Employee employee = (Employee) subject.getPrincipal();
        FixOrder fixOrder = fixOrderService.findFixOrderWithPartsById(orderId);
        model.addAttribute("fixOrder",fixOrder);
        model.addAttribute("currId",employee.getId());
        return "fix/fixOrderDetail";
    }*/


    @RequestMapping(value = "/{orderId:\\d+}/detail",method =RequestMethod.GET)
    public ModelAndView fixOrderDetail(@PathVariable Integer orderId){
        Subject subject = SecurityUtils.getSubject();
        Employee employee = (Employee) subject.getPrincipal();
        ModelAndView mav = new ModelAndView("fix/fixOrderDetail");

        FixOrder fixOrder = fixOrderService.findFixOrderWithPartsById(orderId);
        mav.addObject("fixOrder",fixOrder);
        mav.addObject("currId",employee.getId());
        return mav;
    }

    @GetMapping("/check")
    public String checkList(){
        return "fix/checkList";
    }
    /**
     *  跳转到等待质检的订单页面，前台发送消息
     */
    @GetMapping("/{orderId:\\d+}/done")
    public String  getFixDone(@PathVariable Integer orderId,Model model){
        Subject subject = SecurityUtils.getSubject();
        Employee employee = (Employee) subject.getPrincipal();
        fixOrderService.getFixDone(orderId);
        //删除定时任务
        fixOrderService.deleteJob(orderId,employee.getId());
        //从数据库中查询所有的等待质检的订单列表
        List<FixOrder> checkOrderList = fixOrderService.findCheckOrderListWithParts();
        model.addAttribute("checkOrderList",checkOrderList);
        return "fix/checkList";
    }



    /**
     * 领取质检任务
     */
    @GetMapping("/{orderId:\\d+}/check")
    @ResponseBody
    public JsonResponse getCheckFixDone(@PathVariable Integer orderId){
        Subject subject = SecurityUtils.getSubject();
        Employee employee = (Employee) subject.getPrincipal();
        try{
            fixOrderService.getCheckTask(orderId,employee);
            return JsonResponse.success();
        }catch (ServiceException e){
            return  JsonResponse.error(e.getMessage());
        }
    }

    /**
     * 质检的详情单
     */
    @GetMapping("/{orderId:\\d+}/checkDetail")
    public String checkDetail(@PathVariable Integer orderId, Model model){

        Subject subject = SecurityUtils.getSubject();
        Employee employee = (Employee) subject.getPrincipal();
        model.addAttribute("currId",employee.getId());


        FixOrder fixOrder =fixOrderService.findCheckOrderWithPartsById(orderId);
        model.addAttribute("fixOrder",fixOrder);
        model.addAttribute("currId",employee.getId());
        return  "fix/checkOrderDetail";
    }


}
