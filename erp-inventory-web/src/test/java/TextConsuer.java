import com.haohua.erp.service.EmployeeService;
import com.haohua.erp.service.RoleService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;


public class TextConsuer {


    @Test
    public void textConsumer() throws IOException {
        ClassPathXmlApplicationContext  context = new ClassPathXmlApplicationContext("classpath:sping/spring-dubbo.xml");
        context.start();
        System.out.println("容器启动成功");

       RoleService roleService= (RoleService) context.getBean("roleService");
        EmployeeService employeeService = (EmployeeService) context.getBean("employeeService");

        System.out.println(roleService);
        System.out.println(employeeService);
        System.in.read();
    }



}
