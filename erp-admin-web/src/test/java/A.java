import com.haohua.erp.entity.Permission;
import com.haohua.erp.service.EmployeeService;
import com.haohua.erp.service.PremissionService;
import com.haohua.erp.service.RoleService;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class A {

    @Test
    public void  s(){
        String a = "123123";
       String b =  DigestUtils.md5Hex(a);
        System.out.println(b);
    }
@Test
    public void ListTest(){
        List<String> aList = new ArrayList<>();
        List<String> stringList = new ArrayList<>();
        stringList.add("11");
        stringList.add("22");

        List<String> stringList2 = new ArrayList<>();
        stringList2.add("33");
        stringList2.add("44");
      /*  List<Boolean> booleanList = new ArrayList<>();
        booleanList.add(false);
        booleanList.add(true);*/

        aList.addAll(stringList);
        aList.addAll(stringList2);
        for (String s:aList){
            System.out.println(s);
        }
    }

    @Test
    public void textConsumer() throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-dubbo-consumer.xml");
        context.start();

        System.out.println("容器启动成功");

        PremissionService premissionService= (PremissionService) context.getBean("premissionService");


        List<Permission> permissionList = premissionService.findPremissionList();
        for(Permission permission:permissionList){
            System.out.println( permission.getPermissionCode() );
        }
        System.in.read();
    }
}
