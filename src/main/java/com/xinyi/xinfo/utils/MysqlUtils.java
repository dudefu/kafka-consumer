package com.xinyi.xinfo.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import com.xinyi.xinfo.constant.Constant;

public class MysqlUtils {
    public static Connection getConnection(){
        try{
            Class.forName(Constant.MYSQL_CLASS_NAME);
            Connection connection = DriverManager.getConnection(Constant.MYSQL_URL, Constant.MYSQL_USERNAME, Constant.MYSQL_PASSWORD);
            return connection;
        }catch (Exception e){
            e.printStackTrace();
        }
        return  null;
    }
}
