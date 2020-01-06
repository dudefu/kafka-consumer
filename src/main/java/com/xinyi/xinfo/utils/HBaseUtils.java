package com.xinyi.xinfo.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import com.xinyi.xinfo.constant.Constant;


public class HBaseUtils {
    private static Map<String, String> confMap = new HashMap<>();
    private static Configuration hbaseConfiguration = null;
    private static Connection hbaseConnection = null;




    public static Connection getHBaseConnection() {
        if (hbaseConnection == null) {
            hbaseConfiguration = HBaseConfiguration.create();
            hbaseConfiguration.set("hbase.master", Constant.HBASE_MASTER);
            hbaseConfiguration.set("hbase.rootdir", Constant.HBASE_ROOTDIR);
            hbaseConfiguration.set("hbase.zookeeper.quorum", Constant.HBASE_ZOOKEEPER_QUORUM);
            hbaseConfiguration.set("hbase.zookeeper.property.clientPort", Constant.HBASE_ZOOKEEPER_PROPERTY_CLIENTPORT);
            try {
                hbaseConnection = ConnectionFactory.createConnection(hbaseConfiguration);
            } catch (IOException e) {
                //log.error("获取hbase 连接异常！");
                hbaseConnection = null;
            }
        }
        return hbaseConnection;
    }
}
