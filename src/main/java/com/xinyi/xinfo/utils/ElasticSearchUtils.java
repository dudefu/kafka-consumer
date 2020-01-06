package com.xinyi.xinfo.utils;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import com.xinyi.xinfo.constant.Constant;

public class ElasticSearchUtils {
    public static TransportClient getConnection(){
        Settings setting = ImmutableSettings.settingsBuilder()
                .put("cluster.name", Constant.ELASTICSEARCH_CLUSTER_NAME)//指定集群名称
                .put("client.transport.sniff", Constant.ELASTICSEARCH_CLIENT_TRANSPORT_SNIFF)//启动嗅探功能
                .build();

        /**
         * 2：创建客户端
         * 通过setting来创建，若不指定则默认链接的集群名为elasticsearch
         * 链接使用tcp协议即9300
         */
        TransportClient transportClient = new TransportClient(setting);
        TransportAddress transportAddress = new InetSocketTransportAddress(Constant.ELASTICSEARCH_SERVERS, Constant.ELASTICSEARCH_PORT);
        transportClient.addTransportAddresses(transportAddress);
        return transportClient;
    }
}
