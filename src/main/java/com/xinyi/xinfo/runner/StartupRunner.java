package com.xinyi.xinfo.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import com.xinyi.xinfo.constant.Constant;
import com.xinyi.xinfo.consumer.DataConsumer;

import java.util.ArrayList;
import java.util.List;

@Order(1)
public class StartupRunner implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(StartupRunner.class);

    @Override
    public void run(String... args){
        /**
         * eg: streaminglog,streaminglog_1574991501027,streaminglog_157479797979797
         * args[0] --- topic
         * args[1] --- tableName
         * args[2] --- groupId
         * args[3] --- server
         */
        if(args.length == 0){
//            logger.info("startup runner");
//            DataConsumer dataConsumer = new DataConsumer(Constant.KAFKA_TOPIC,Constant.GP_TABLE_NAME,
//                    Constant.KAFKA_GROUPID,Constant.KAFKA_BOOTSTRAP_SERVERS);
//            Thread dataConsumerThread = new Thread(dataConsumer);
//            dataConsumerThread.start();
//            logger.info("start kafka consumer listening over ");
            logger.warn("main方法缺少运行参数（topic,tableName,groupId,server）");
            System.exit(0);
        }else{
            DataConsumer dataConsumer = new DataConsumer(args[0],args[1],args[2],args[3]);
            Thread dataConsumerThread = new Thread(dataConsumer);
            dataConsumerThread.start();
        }
    }
}
