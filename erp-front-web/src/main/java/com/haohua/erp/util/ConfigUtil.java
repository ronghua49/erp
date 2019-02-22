package com.haohua.erp.util;    /*
 * @author  Administrator
 * @date 2018/10/12
 */

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigUtil {
    private  static Properties properties=new Properties();

    static{
        try {
            properties.load(ConfigUtil.class.getClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperties(String key){
        return   (String) properties.get(key);
    }
}
