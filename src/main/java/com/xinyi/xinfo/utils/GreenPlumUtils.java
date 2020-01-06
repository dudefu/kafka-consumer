package com.xinyi.xinfo.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import com.xinyi.xinfo.constant.Constant;

public class GreenPlumUtils {

    public static Connection getConnection(){
        try{
            Class.forName(Constant.GP_CLASS_NAME);
            Connection connection = DriverManager.getConnection(Constant.GP_URL, Constant.GP_USERNAME, Constant.GP_PASSWORD);
            return connection;
        }catch (Exception e){
            e.printStackTrace();
        }
        return  null;
    }
}
