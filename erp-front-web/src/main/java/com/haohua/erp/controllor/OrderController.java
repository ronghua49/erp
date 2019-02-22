package com.haohua.erp.controllor;    /*
 * @author  Administrator
 * @date 2018/8/3
 */

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.haohua.erp.entity.*;
import com.haohua.erp.entity.VO.OrderEditTransInfo;
import com.haohua.erp.entity.VO.OrderVo;
import com.haohua.erp.exception.ServiceException;
import com.haohua.erp.service.CarService;
import com.haohua.erp.service.OrderService;
import com.haohua.erp.util.ConfigUtil;
import com.haohua.erp.util.JsonResponse;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @GetMapping("/new")
    public String newOrder() {
        return "order/newOrder";
    }

    @PostMapping(value = "/new")
    @ResponseBody
    public JsonResponse newOrder(@RequestBody OrderVo json) {
       /* Gson gson = new Gson();
        OrderVo orderVo = gson.fromJson(json, OrderVo.class);
*/
        
        System.out.println(json);
        Subject subject = SecurityUtils.getSubject();
        Employee employee = (Employee) subject.getPrincipal();
        //得到订单详情，新增订单
        try {
            orderService.addOrder(json, employee.getId());
            ReentrantLock locak = new ReentrantLock();

            return JsonResponse.success();
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResponse.error();
        }
    }

    @GetMapping("/serviceType")
    @ResponseBody
    public JsonResponse allServiceType() {
        List<ServiceType> serviceTypeList = orderService.findServiceTypeList();
        return JsonResponse.success(serviceTypeList);
    }

    @GetMapping("/partsType")
    @ResponseBody
    public JsonResponse allPartsType() {
        List<Type> typeList = orderService.findPartsTypeList();
        return JsonResponse.success(typeList);
    }

    @GetMapping("/{typeId:\\d+}/parts")
    @ResponseBody
    public JsonResponse findPartsListByTypeId(@PathVariable Integer typeId) {
        //根据零件类型id 查询下面库存大于0的零部件
        List<Parts> partsList = orderService.findPartsListByTypeId(typeId);
        return JsonResponse.success(partsList);
    }

    /**
     * 订单查询
     *
     * @param licenceNo
     * @param tel
     * @param orderTime
     * @param model
     * @return
     */
    @GetMapping("/search")
    public String findOrderList(@RequestParam(required = false) String licenceNo,
                                @RequestParam(required = false) String tel,
                                @RequestParam(required = false) String orderTime,
                                @RequestParam(required = false) String state,
                                @RequestParam(defaultValue = "1") Integer p,
                                Model model) {
        Map<String, Object> paramMap = new LinkedHashMap<>();
        paramMap.put("licenceNo", licenceNo);
        paramMap.put("tel", tel);
        paramMap.put("orderTime", orderTime);
        paramMap.put("state", state);
        paramMap.put("p", p);
        try {
            PageInfo<Order> page = orderService.findOrdersByParamMap(paramMap);
            for(Order order :page.getList()){
                System.out.println(order);
                System.out.println(order.getStateName());
            }
            model.addAttribute("page", page);

        } catch (ServiceException e) {
            model.addAttribute("message", e.getMessage());
        }
        return "order/orderList";
    }

    /**
     * 订单的详情查询
     *
     * @param orderId
     * @param model
     * @return
     */
    @GetMapping("/{orderId:\\d+}/detail")
    public String orderDetail(@PathVariable Integer orderId, Model model) {
        //查询order详情
        Order order = orderService.findOrder(orderId);
        //查询车辆信息详情
        Car car = orderService.findCarInfoByOrderId(orderId);
        //查询服务信息详情
        ServiceType service = orderService.findServiceTypeByOrderId(orderId);
        //查询订单所需的零件信息详情
        List<Parts> partsList = orderService.findPartsListByOrderId(orderId);
        model.addAttribute("order", order);
        model.addAttribute("car", car);
        model.addAttribute("service", service);
        model.addAttribute("partsList", partsList);
        return "order/orderDetail";
    }

    /**
     * 根据订单id删除订单
     *
     * @param orderId
     * @return
     */
    @GetMapping("/{orderId:\\d+}/del")
    @ResponseBody
    public JsonResponse delOrder(@PathVariable Integer orderId) {
        try {
            orderService.delOrderById(orderId);
            return JsonResponse.success();
        } catch (ServiceException e) {
            return JsonResponse.error(e.getMessage());
        }
    }

    @GetMapping("/{orderId:\\d+}/editPage")
    public String editOrder(@PathVariable Integer orderId, Model model) {
        model.addAttribute("orderId", orderId);
        return "order/editOrder";

    }

    @GetMapping("/{orderId:\\d+}/edit")
    @ResponseBody
    public JsonResponse editOrder(@PathVariable Integer orderId) {
        //查询车辆信息
        Car car = orderService.findCarInfoByOrderId(orderId);
        //查询所定服务类型
        ServiceType chooseServiceType = orderService.findServiceTypeByOrderId(orderId);
        //查询所选的所有配件信息列表
        List<Parts> choosePartsList = orderService.findPartsListByOrderId(orderId);
        OrderEditTransInfo info = new OrderEditTransInfo();
        info.setOrderId(orderId);
        info.setCar(car);
        info.setChooseServiceType(chooseServiceType);
        info.setChoosePartsList(choosePartsList);
        return JsonResponse.success(info);
    }

    @PostMapping("/edit")
    @ResponseBody
    public JsonResponse editOrder(String json) {
        Gson gson = new Gson();
        OrderVo orderVo = gson.fromJson(json, OrderVo.class);
        try {
            orderService.editOrder(orderVo);
            return JsonResponse.success();
        } catch (ServiceException e) {
            return JsonResponse.error(e.getMessage());
        }
    }

    @GetMapping("/{orderId:\\d+}/trans")
    @ResponseBody
    public JsonResponse transOrder(@PathVariable Integer orderId) {
        try {
            orderService.transOrderById(orderId);
            return JsonResponse.success();
        } catch (ServiceException e) {
            return JsonResponse.error(e.getMessage());
        }

    }

    @GetMapping("/img/upload")
    public String uoloadOrder() {
        return "order/uploadPic";
    }

    @PostMapping("/img/upload")
    public String uploadItem(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException, ServletException {
        System.out.println(file.getSize());//获得字节数
        System.out.println(file.getName());//获得文件的表单name属性
        String originalFilename = file.getOriginalFilename();//文件真名
        String fileName = originalFilename;//UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));
        File uploadFile = new File(ConfigUtil.getProperties("upload.path"));
        uploadFile.mkdirs();
        InputStream in = file.getInputStream();
        OutputStream out = new FileOutputStream(new File(uploadFile, fileName));
        IOUtils.copy(in, out);
        out.flush();
        out.close();
        in.close();

        return "order/success";
    }
    @RequestMapping("/download/{fileName:.*}")
    public ResponseEntity<byte[]> download(HttpServletRequest request, @PathVariable String fileName, HttpServletResponse response) throws IOException {
        File file = new File(ConfigUtil.getProperties("upload.path"), "\\" + fileName);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        response.setCharacterEncoding("UTF-8");
        //设置下载框显示编码，fileName以ISO8859-1编码 可以显示中文
        headers.setContentDispositionFormData("attachment", new String(fileName.getBytes("utf-8"), "ISO8859-1"));
        System.out.println(fileName);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
                headers, HttpStatus.CREATED);
    }

    @GetMapping("/show/file")
    public String showFileList(Model model) {
        File file = new File(ConfigUtil.getProperties("upload.path"));
        List<String> nameList = Arrays.asList(file.list());
        model.addAttribute("nameList",nameList);
        return "order/showFile";
    }

    @GetMapping("/json/test")
    @ResponseBody
    public JsonResponse findCarInfoWithOrderId(@RequestParam String orderTime){
        System.out.println(orderTime);
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("orderTime",orderTime);
        paramMap.put("p",1);
        PageInfo<Order> page = orderService.findOrdersByParamMap(paramMap);
        return JsonResponse.success(page);

    }

}