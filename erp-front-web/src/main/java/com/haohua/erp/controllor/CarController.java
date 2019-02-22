package com.haohua.erp.controllor;    /*
 * @author  Administrator
 * @date 2018/8/2
 */
import com.haohua.erp.entity.Car;
import com.haohua.erp.entity.Customer;
import com.haohua.erp.service.CarService;
import com.haohua.erp.util.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/car")
public class CarController {

    @Autowired
    private CarService carService;
    @PostMapping("/new")
    @ResponseBody
    public JsonResponse newCarInfo(Car car, Customer customer){

        try{
            carService.insertInfo(car,customer);
             car = carService.findCarWithCustomerInfo(car.getLicenceNo());
            return JsonResponse.success(car);
        }catch (Exception e){
            return  JsonResponse.error();
        }

    }

    @GetMapping("/search")
    @ResponseBody
    public JsonResponse searchLicense(String licenceNo){
        Car car = carService.findCarWithCustomerInfo(licenceNo);
        if (car==null){
          return JsonResponse.error("暂未录入");
        }else{
            return  JsonResponse.success(car);
        }
    }
}
