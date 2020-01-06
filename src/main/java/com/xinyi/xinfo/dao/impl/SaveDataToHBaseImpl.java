package com.xinyi.xinfo.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xinyi.xinfo.constant.Constant;
import com.xinyi.xinfo.dao.SaveData;
import com.xinyi.xinfo.utils.HBaseUtils;

/**
 * 功能说明:
 * author: haonan.bian
 * date: 2018/10/26 14:36
 * Copyright (C)1997-2018 深圳信义科技 All rights reserved.
 */
public class SaveDataToHBaseImpl implements SaveData {
    private String tableName;
    private String familyName;
    private Connection hbaseConnection;

    private static FastDateFormat fdfWithBar = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
    private static FastDateFormat fdfWithNoBar = FastDateFormat.getInstance("yyyyMMddHHmmss");

    public SaveDataToHBaseImpl(String tableName, String familyName) {
        this.tableName = tableName;
        this.familyName = familyName;
    }

    @Override
    public String saveAsJSON(JSONArray datas) {
        try {
            hbaseConnection = HBaseUtils.getHBaseConnection();
            List<Put> list = new ArrayList();

            Table table = hbaseConnection.getTable(TableName.valueOf(tableName));
            int datasSize = datas.size();
            for (int i = 0; i < datasSize ; i++) {
                JSONObject jb = datas.getJSONObject(i);
                //System.out.println(jb);

                // imsi rowkey设计
                String time = DateFormatUtils.format(Long.valueOf(jb.getString("time")),"yyyy-MM-dd HH:mm:ss");
                String rowKeyPassTime = fdfWithNoBar.format(fdfWithBar.parse(time));
                String rowKey = rowKeyPassTime + "-" + jb.getString("deviceNo") + "-" + jb.getString("imsi")  + "-" + jb.getString("imei")  + 9;

                //Put put = new Put(Bytes.toBytes(jb.getString(Constant.SYSTEM_PRIMARYKEY)));
                Put put = new Put(Bytes.toBytes(rowKey));
                for (String key : jb.keySet()) {
                    if(key.equals( "time")){
                        put.addColumn(Bytes.toBytes(familyName), Bytes.toBytes(key), Bytes.toBytes(time));
                    }else{
                        put.addColumn(Bytes.toBytes(familyName), Bytes.toBytes(key), Bytes.toBytes(jb.getString(key)));
                    }

                }
                list.add(put);
            }
            table.put(list);
        } catch (Exception e) {
            e.printStackTrace();
            return Constant.SYSTEM_RESULT_FALIED;
        } finally {
//            try {
//                if (null != hbaseConnection) hbaseConnection.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }
        return Constant.SYSTEM_RESULT_SUCCESS;
    }
}
