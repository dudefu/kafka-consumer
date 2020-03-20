package com.xinyi.xinfo.utils;

import com.alibaba.fastjson.JSONObject;
import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;
import java.io.*;

/**
 * JDBC写数据到文件中再Copy到postgresql中,
 * 经过测试，大概写入12万条数据2秒+左右
 */
public class CopyDataToGp {

    public static final Logger logger = LoggerFactory.getLogger(CopyDataToGp.class);

    //数据信息一样时使用
//    public static String writeFile(List<JSONObject> list){
//        FileWriter out = null;
//        String filePath = "./"+UUID.randomUUID();
//
//        try{
//            out = new FileWriter(new File(filePath));
//            for(int i=0;i<list.size();i++){
//                Object[] objs = list.get(i).values().toArray();
//                for(int j=0;j<objs.length;j++){
//                    if(objs[j] == null){
//                        out.write("null");
//                    }else{
//                        String str = String.valueOf(objs[j]).replace("\n","") ;
//                        out.write(str);
//                    }
//                    if(j != objs.length - 1){
//                        out.write("^");
//                    }
//                }
//                if(i != list.size() - 1){
//                    out.write("\n");
//                }
//            }
//            out.flush();
//        }catch(Exception ex){
//            ex.printStackTrace();
//        }finally{
//            if(out != null){
//                try {
//                    out.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        return filePath;
//    }

    /**
     * 字段数不统一时处理方法，比如中间某些数据跟第一条数据不一样时:
     * 1、字段数比第一条数据字段数少时，其他缺失字段数值设置为0
     * 2、字段数比第一条数据字段数多时，先往目标表中新增字段，然后再插入数据（忽略已插入的数据）
     */
    public static String writeFile(List<JSONObject> list, String tableColumns) {
        FileWriter out = null;
        String filePath = "./" + UUID.randomUUID();

        try {
            out = new FileWriter(new File(filePath));
            String[] fields = tableColumns.split(",");

            for (int i = 0; i < list.size(); i++) {
                Object[] objs = list.get(i).values().toArray();
                if (objs.length != fields.length) {
                    //判断每条数据长度是否一样，不一样做处理，缺少的值设置为0
                    List<Object> objsTemp = new ArrayList<>();
                    for (Object obj : objs) {
                        objsTemp.add(obj);
                    }
                    List<String> fieldsNew = new ArrayList<>();
                    for (String key : list.get(i).keySet()) {
                        fieldsNew.add(key.toLowerCase());
                    }
                    for (int j = 0; j < fields.length; j++) {
                        boolean b = fieldsNew.contains(fields[j].toLowerCase());
                        System.out.println("====>>" + b);
                        if (!b) {
                            objsTemp.add(j, 0);
                        }
                    }


                    for (int j = 0; j < objsTemp.size(); j++) {
                        if (objsTemp.get(j) == null) {
                            out.write("null");
                        } else {
                            String str = String.valueOf(objsTemp.get(j)).replace("\n", "");
                            out.write(str);
                        }
                        if (j != objsTemp.size() - 1) {
                            out.write("^");
                        }
                    }
                } else {
                    for (int j = 0; j < objs.length; j++) {
                        if (objs[j] == null) {
                            out.write("null");
                        } else {
                            String str = String.valueOf(objs[j]).replace("\n", "");
                            out.write(str);
                        }
                        if (j != objs.length - 1) {
                            out.write("^");
                        }
                    }
                }
                if (i != list.size() - 1) {
                    out.write("\n");
                }
            }
            out.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return filePath;
    }

    public static long copyData(String tablename, String tableColumns, List<JSONObject> list, Connection conn) {
        CopyManager copyManager = null;
        FileReader reader = null;
        String filePath = null;
        long copyResult = 0l;

        try {
            filePath = writeFile(list, tableColumns);

            copyManager = new CopyManager((BaseConnection) conn);
//            reader = new FileReader(new File("./7a5797e6-cf0c-4071-bd23-22dcd758078e"));
            reader = new FileReader(new File(filePath));
            //logger.info("copy "+tablename+" ("+tableColumns+") from stdin delimiter as '^' NULL as 'null'");
            copyResult = copyManager.copyIn("copy " + tablename + " (" + tableColumns + ") from stdin delimiter as '^' NULL as 'null'", reader);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                    //deleteFile(filePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return copyResult;
    }

    public static int getColumnNum(String tableName, Connection conn) {

        String fieldNumSql = "select count(*) from  information_schema.COLUMNS where TABLE_NAME = \'" + tableName + "\'";
        int num = 0;
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(fieldNumSql);
            while (rs.next()) {
                num = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return num;
    }

    public static List<String> getFields(String tableName, Connection conn) {
        String columnSql = "select COLUMN_NAME from  information_schema.COLUMNS where TABLE_NAME = \'" + tableName + "\'";
        List<String> fields = new ArrayList<>();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(columnSql);
            while (rs.next()) {
                fields.add(rs.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fields;
    }

    public static String getColumns(JSONObject jsonObject, String tableName, Connection conn) {
        StringBuilder result = new StringBuilder();
        int count = 0;
        List<String> addFields = new ArrayList<>();

        //获取表字段
        List<String> fields = getFields(tableName, conn);

        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            count++;
            String key = entry.getKey().toLowerCase();
            if (count != jsonObject.entrySet().size()) {
                if (key.equals("id")) {
                    result.append("_id").append(",");
                } else if (key.equals("data")) {
                    result.append("_data").append(",");
                } else if (key.equals("user")) {
                    result.append("_user").append(",");
                } else {
                    result.append(key).append(",");
                }
            } else {
                if (key.equals("id")) {
                    result.append("_id");
                } else if (key.equals("data")) {
                    result.append("_data");
                } else if (key.equals("user")) {
                    result.append("_user");
                } else {
                    result.append(key);
                }
            }

            //判断数据里的字段是否包含在表中字段里，不包含的话，往表中新增字段
            boolean bool = fields.contains(key);
            if (!bool) {
                addFields.add(key);
            }
        }
        if (addFields.size() != 0) {
            addFields(tableName, addFields, conn);
        }
        return result.toString();
    }

    public static boolean addFields(String tableName, List<String> fields, Connection conn) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < fields.size(); i++) {
            sb.append(" ADD " + fields.get(i) + " varchar(255),");
        }
        String columns = sb.toString().substring(0, sb.toString().lastIndexOf(","));
        String columnSql = "ALTER TABLE " + tableName + columns;
        logger.info(columns);
        logger.info(columnSql);
        try {
            Statement st = conn.createStatement();
            boolean rs = st.execute(columnSql);
            return rs;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteTable(String tableName, Connection conn) {
        boolean bool = true;
        String sql = "delete from " + tableName;
        try {
            Statement st = conn.createStatement();
            boolean rs = st.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            bool = false;
        }

        return bool;
    }

    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
            //logger.info("文件已删除!");
            return true;
        } else {
            logger.info("文件不存在！");
            return false;
        }
    }

}
