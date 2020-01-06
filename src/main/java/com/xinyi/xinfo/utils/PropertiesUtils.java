package com.xinyi.xinfo.utils;

import java.io.InputStream;
import java.util.Properties;
import com.xinyi.xinfo.constant.Constant;

public class PropertiesUtils {

    public static Properties getProperties(){
        try{
            Properties props = new Properties();
            InputStream in = PropertiesUtils.class.getClassLoader().getResourceAsStream(Constant.PROPERTY_FILE_NAME);
            props.load(in);
            return props;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static String  getParam(String key) {
        Properties props = new Properties();
        try {
            InputStream in = PropertiesUtils.class.getClassLoader().getResourceAsStream(Constant.PROPERTY_FILE_NAME);
            props.load(in);
            in.close();
            String value = props.getProperty(key);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
