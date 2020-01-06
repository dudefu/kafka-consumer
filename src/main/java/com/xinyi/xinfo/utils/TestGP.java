package com.xinyi.xinfo.utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import com.alibaba.fastjson.JSON;
import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;

import com.xinyi.xinfo.constant.Constant;

public class TestGP {

    public static void main(String[] args) {
//        String[] strings = ("{\"rtn\":\"8\",\"remoteid\":\"test1001\",\"remotename\":\"xatest\",\"msg\":\"resource is unaccessable\"},,GX-38149871,24629,POST,流媒体服务,0,A0010,PG ONE,/videostreaming/prepare,1568101122833,3564c" +
//                "c89-3251-4378-8ebc-bcca53f253be,127.0.0.1:18001,2019-11-20 09:23:55,200,7,44030504001" +
//                "310614431,381,,VEDIO00001,127.0.0.1\n").split(",");
//        String[] strings1 = "登陆,,,,POST,客户端,0,A0009,,,,5d38dc35-a4fb-467e-a518-957cf078b3ec,10.200.199.193,2019-11-29 10:51:50,0,0,,,,G0002,10.200.199.193\n".split(",");
//        String[] strings2 = "1,2,3,4,5".split(",");
//        System.out.println(Arrays.toString(strings));
        Map<Integer,Integer> map = new HashMap<>();
        map.put(3,2);
        map.put(5,3);
        map.put(1,5);
        map.put(2,6);
        map.put(4,8);
        List<Integer> list = new ArrayList<>();
        for (Integer key : map.keySet()) {
            list.add(key);
        }
        Collections.sort(list);
        System.out.println(JSON.toJSONString(list));
        System.out.println(map.get(list.get(list.size()-1)));
    }

    public static boolean compareList(List<String> list1,List<String> list2){
        boolean bool = true ;
        if(list1.size() == list2.size()){
            for (int i = 0; i < list1.size(); i++) {
                boolean b = list1.contains(list2.get(i));
                if(b == false){
                    bool =  false ;
                }
            }
        }else{
            bool = false ;
        }
        return bool ;
    }
    public void test(){
        Connection conn = null;
        CopyManager copyManager = null;
        FileReader reader = null;

        try{
            long starttime = System.currentTimeMillis();
            String filePath = "";
            String tablename = "streaminglog";

            conn = GreenPlumUtils.getConnection();
            copyManager = new CopyManager((BaseConnection)conn);
            reader = new FileReader(new File(filePath));
            System.out.println("copy "+tablename+" "+ Constant.GP_TABLE_COLUMNS.toLowerCase()+" from stdin delimiter as ',' NULL as 'null'");
            copyManager.copyIn("copy "+tablename+" "+Constant.GP_TABLE_COLUMNS.toLowerCase()+" from stdin delimiter as ',' NULL as 'null'",reader);
            long endtime = System.currentTimeMillis();

            System.out.println(endtime-starttime);
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
