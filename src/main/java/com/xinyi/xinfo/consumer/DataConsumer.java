package com.xinyi.xinfo.consumer;

import java.sql.Connection;
import java.util.*;

import com.xinyi.xinfo.utils.CopyDataToGp;
import com.xinyi.xinfo.utils.GreenPlumUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xinyi.xinfo.constant.Constant;

public class DataConsumer implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(DataConsumer.class);

    private KafkaConsumer<String, String> consumer = null;
    private ConsumerRecords<String, String> msgList;
    private String topic = null;
    private String tableName = null;
    private int columnNum = 0;
    private List<String> fields = null;
    List<String> lastRecordValue = null ;

    private int batchTime = Integer.parseInt(Constant.SYSTEM_BATCH_TIME);
    private int batchSize = Integer.parseInt(Constant.SYSTEM_BATCH_SIZE);

    private String[] persists = Constant.SYSTEM_PERSISTS.split(Constant.SYSTEM_SPLIT_FLAG);
    private String[] requirements = Constant.SYSTEM_REQUIREMENTS.split(Constant.SYSTEM_SPLIT_FLAG);

    private static Connection conn = null ;

    public DataConsumer(String topicName,String tableName,String GroupId,String server) {
        this.conn =  GreenPlumUtils.getConnection();
        Properties props = new Properties();
        props.put("bootstrap.servers", server);
        //props.put("group.id", GroupId);
        props.put("enable.auto.commit", "true");
        props.put("zookeeper.session.timeout.ms", "50000000");
        props.put("zookeeper.sync.time.ms", "100");//同步数据时间间隔
        props.put("auto.commit.interval.ms", "200");

        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());
        props.put("auto.offset.reset", "earliest");
        props.put("group.id", UUID.randomUUID().toString());
        this.consumer = new KafkaConsumer<String, String>(props);
        this.topic = topicName;
        logger.info(topicName);
        this.consumer.subscribe(Arrays.asList(topic));
        this.tableName = tableName ;
        this.columnNum = CopyDataToGp.getColumnNum(tableName,conn);
        this.fields = CopyDataToGp.getFields(tableName,conn);
    }

    @Override
    public void run() {
        int count = 0;
        logger.info("---------开始导入数据---------");
        List<JSONObject> data = new ArrayList<>();
        Map<Integer,JSONObject> map = new HashMap<>();
        String tableColumns = null ;
        CopyDataToGp.deleteTable(tableName,conn);

        try {
            for (; ; ) {
                msgList = consumer.poll(1000);
                if (null != msgList && msgList.count() > 0) {
//                    System.out.println(msgList.count());
                    for (ConsumerRecord<String, String> record : msgList) {
                        String recordValue = record.value();
                        JSONObject jsonObject = JSON.parseObject(recordValue) ;
                        data.add(jsonObject);
                        map.put(jsonObject.size(),jsonObject);
                    }
                    //数据data累积到大于等于10000条时，执行一次插入操作
                    if(data.size() >= 10000){
                        List<Integer> list = new ArrayList<>();
                        for (Integer key : map.keySet()) {
                            list.add(key);
                        }
                        Collections.sort(list);
                        tableColumns = CopyDataToGp.getColumns(map.get(list.get(list.size()-1)),tableName,conn);
                        logger.info("===>>> dataSize : "+data.size());
                        logger.info("开始导入数据... ...");
                        String[] str =  tableColumns.split(",");
                        System.out.println("tableColumnsSize==>>"+str.length);
                        long copyResult = CopyDataToGp.copyData(this.tableName,tableColumns,data,conn);
                        count += copyResult;
                        logger.info("===>>> 已插入数据 : "+count);
                        data.clear();
                    }
//                    for (int i = 0; i < dataTemp.size(); i++) {
//                        if(i == 0){
//                            data.add(dataTemp.get(i));
//                        }
//                        if(i>=1){
//                            if(dataTemp.get(i).size() <= dataTemp.get(i-1).size()) {
//                                data.add(dataTemp.get(i));
//                            }else{
//                                if(i == 440 ){
//                                    System.out.println(i);
//                                }
//                                tableColumns = CopyDataToGp.getColumns(dataTemp.get(i),tableName,conn);
//                                List<JSONObject> jsonObjectList = new ArrayList<>();
//                                jsonObjectList.add(dataTemp.get(i));
//                                long copyResult = CopyDataToGp.copyData(this.tableName,tableColumns,jsonObjectList,conn,i);
//                                count += copyResult;
//                            }
//                        }
//                        //logger.info("==> tableColumns = "+tableColumns);
//
//                        //数据data累积到大于等于10000条时，执行一次导入操作
//                        if(data.size() >= 10000){
//                            logger.info("===>>> dataSize : "+data.size());
//                            logger.info("开始导入数据... ...");
//                            String[] str =  tableColumns.split(",");
//                            System.out.println("tableColumnsSize==>>"+str.length);
//                            long copyResult = CopyDataToGp.copyData(this.tableName,tableColumns,data,conn,i);
//                            count += copyResult;
//                            logger.info("===>>> 已插入数据 : "+count);
//                            data.clear();
//                        }
//                        datas1.add(jsonObject);
//                        if (null != recordValue && !recordValue.equals("")) {
//                            jsonObject = JSON.parseObject(recordValue);
//                            int columnNum = jsonObject.size();
//                            List<String> addfields = new ArrayList<>();
//
////                            logger.info("--------------------------------data-start-------------------------------------");
////                            logger.info((String) JSON.toJSON("===>>> count = " + count + "==>> jsonObject = " + jsonObject));
////                            logger.info("===>>> count = " + count);
////                            logger.info("===>>> columnNum = " + columnNum);
//                            for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
////                                logger.info(entry.getKey() + " : " + entry.getValue());
//                                columnsArray.add(entry.getKey());
//                            }
//                            if(count == 1){
//                                this.lastRecordValue = columnsArray ;
//                            }
////                            logger.info("==> columnsArray = "+columnsArray.toString());
//                            String columnsArrayStr = columnsArray.toString();
//                            tableColumns = "("+columnsArrayStr.substring(1,columnsArrayStr.lastIndexOf("]"))+")";
////                            logger.info("==> tableColumns = "+tableColumns);
////                            logger.info("--------------------------------data-end-------------------------------------");
//
//                            //判断原始字段是否包含现有字段，不包含新增字段
//                            for (int i = 0; i < columnsArray.size(); i++) {
//                                String str = columnsArray.get(i).toLowerCase();
//                                if(!this.fields.contains(str)){
//                                    addfields.add(columnsArray.get(i).toLowerCase());
//                                }
//                            }
//                            int str = addfields.size();
//                            if(str>0){
//                                datas1.add(jsonObject);
//                                CopyDataToGp.addFields(tableName,addfields,conn);
//                                this.fields = CopyDataToGp.getFields(tableName,conn);
////                                logger.info("===>>> 开始导入");
//                                CopyDataToGp.copyData(tableName, tableColumns, datas1,conn);
////                                logger.info("===>>> 导入完成");
//                                datas1.clear();
//                            }else{
//                                boolean bool = lastRecordValue.equals(columnsArray);
//                                if(bool == true){
//                                    datas2.add(jsonObject);
//                                    if(datas2.size() >= batchSize ){
////                                        logger.info("===>>> 开始导入");
//                                        CopyDataToGp.copyData(tableName, tableColumns, datas2,conn);
////                                        logger.info("===>>> 导入完成");
//                                        datas2.clear();
//                                    }
//                                }else{
//                                    datas3.add(jsonObject);
////                                    logger.info("===>>> 开始导入");
//                                    CopyDataToGp.copyData(tableName, tableColumns, datas3,conn);
////                                    logger.info("===>>> 导入完成");
//                                    datas3.clear();
//                                }
//                            }
//                        }
//                        count++;
//                    }
//                    dataTemp.clear();
//                    CopyDataToGp.writeFile(datas1);
//                    logger.info("--------------------------------结束-------------------------------------");
                } else {
                    logger.info("no data");
                }
                //Thread.sleep(batchTime * 1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            logger.info("consumer.close();");
        }
    }

    //无序比较两个list<String> 是否相等
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
}
