package com.xinyi.xinfo.constant;

import java.util.Properties;

import com.xinyi.xinfo.utils.PropertiesUtils;

public class Constant {

    private final static Properties prop = PropertiesUtils.getProperties();
    public final static String PROPERTY_FILE_NAME = "application.properties";

    //系统配置
    /**
     * 多少秒取一次数据（单位秒）
     */
    public final static String SYSTEM_BATCH_TIME = prop.getProperty("system.batch.time");
    /**
     * 每个批次多少条数据
     */
    public final static String SYSTEM_BATCH_SIZE = prop.getProperty("system.batch.size");
    /**
     * 将数据保存到那些存储 elsaticsearch = es,  HBase = hbase,  greenplum=gp
     */
    public final static String SYSTEM_PERSISTS = prop.getProperty("system.persists");
    /**
     * 判断非空字段
     */
    public final static String SYSTEM_REQUIREMENTS = prop.getProperty("system.requirements");
    public final static String SYSTEM_PRIMARYKEY = prop.getProperty("system.primarykey");
    public final static String SYSTEM_SPLIT_FLAG = prop.getProperty("system.split.flag");
    public final static String SYSTEM_RESULT_SUCCESS = "SUCCESS";
    public final static String SYSTEM_RESULT_FALIED = "FALIED";



    //elasticsearch 相关配置
    public final static String ELASTICSEARCH_SERVERS = prop.getProperty("elasticsearch.servers");
    public final static int ELASTICSEARCH_PORT = Integer.parseInt(prop.getProperty("elasticsearch.port"));
    public final static String ELASTICSEARCH_CLUSTER_NAME = prop.getProperty("elasticsearch.cluster.name");
    public final static String ELASTICSEARCH_CLIENT_TRANSPORT_SNIFF = prop.getProperty("elasticsearch.client.transport.sniff");
    public final static String ELASTICSEARCH_INDEX = prop.getProperty("elasticsearch.motorimsi.index");
    public final static String ELASTICSEARCH_TYPE = prop.getProperty("elasticsearch.motorimsi.type");


    //kafka 相关配置
    public final static String KAFKA_BOOTSTRAP_SERVERS = prop.getProperty("kafka.bootstrap.servers");
    public static String KAFKA_TOPIC = prop.getProperty("kafka.motorimsi.topic");
    public final static String KAFKA_GROUPID = prop.getProperty("kafka.consumer.groupid");


    //hbase 相关配置
    public final static String HBASE_MASTER = prop.getProperty("hbase.master");
    public final static String HBASE_ROOTDIR = prop.getProperty("hbase.rootdir");
    public final static String HBASE_ZOOKEEPER_QUORUM = prop.getProperty("hbase.zookeeper.quorum");
    public final static String HBASE_ZOOKEEPER_PROPERTY_CLIENTPORT = prop.getProperty("hbase.zookeeper.property.clientPort");

    //车牌表名
    public final static String HBASE_VEHICLE_TABLE_NAME = prop.getProperty("hbase.imsi.table.name");
    //车牌列族名
    public final static String HBASE_VEHICLE_TABLE_FAMILY = prop.getProperty("hbase.imsi.table.family");


    //GreenPlum 相关配置
    public final static String GP_CLASS_NAME = prop.getProperty("gp.class.name");
    public final static String GP_URL = prop.getProperty("gp.url");
    public final static String GP_USERNAME = prop.getProperty("gp.username");
    public final static String GP_PASSWORD = prop.getProperty("gp.password");
    public final static String GP_TABLE_NAME = prop.getProperty("gp.table.name");
    public final static String GP_TABLE_PATH = prop.getProperty("gp.table.path");
    public final static String GP_TABLE_COLUMNS = prop.getProperty("gp.table.columns");
    public final static String GP_TABLE_COPYIN = prop.getProperty("gp.table.copyin");
    public final static String GP_TABLE_TEMP_SUFFIX = prop.getProperty("gp.table.temp.suffix");

    //mysql 相关配置
    public final static String MYSQL_CLASS_NAME = prop.getProperty("mysql.class.name");
    public final static String MYSQL_URL = prop.getProperty("mysql.url");
    public final static String MYSQL_USERNAME = prop.getProperty("mysql.username");
    public final static String MYSQL_PASSWORD = prop.getProperty("mysql.password");
    /**查询设备id对应的类型*/
    public final static String MYSQL_SQL_QUERY_TYPE = prop.getProperty("msyql.sql.query_type");
    /**查询当前日是否有数据插入*/
    public final static String MYSQL_SQL_QEURY_DATA_COUNT = prop.getProperty("mysql.sql.qeury_data_count");
    /**插入每天新增数据量*/
    public final static String MYSQL_SQL_INSERT_TOTAL = prop.getProperty("mysql.sql.insert_total");
    /**更新每天新增数据量*/
    public final static String MYSQL_SQL_UPDATE_TOTAL = prop.getProperty("mysql.sql.update_total");
    /**插入截止当天数据总量*/
    public final static String MYSQL_SQL_INSERT_DATA_COUNT = prop.getProperty("mysql.sql.insert_data_count");
    /**更新截止当日数据总量*/
    public final static String MYSQL_SQL_UPDATE_DATA_COUNT = prop.getProperty("mysql.sql.update_data_count");




    public final static String DEVICE_TYPE = prop.getProperty("device.type");

}
