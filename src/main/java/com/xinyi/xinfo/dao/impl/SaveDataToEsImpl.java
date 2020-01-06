package com.xinyi.xinfo.dao.impl;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.transport.TransportClient;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xinyi.xinfo.constant.Constant;
import com.xinyi.xinfo.dao.SaveData;
import com.xinyi.xinfo.utils.ElasticSearchUtils;

/**
 * 功能说明:
 * author: haonan.bian
 * date: 2018/10/17 15:54
 * Copyright (C)1997-2018 深圳信义科技 All rights reserved.
 */
public class SaveDataToEsImpl implements SaveData {

    TransportClient transportClient;
    /* 索引库名 */
    private String index;
    /* 类型名称 */
    private String type;

    public SaveDataToEsImpl(String index, String type) {
        this.index = index;
        this.type = type;
    }

    @Override
    public String saveAsJSON(JSONArray MotorVehicleJSONArray) {
        transportClient = ElasticSearchUtils.getConnection();
        BulkRequestBuilder bulkRequest = transportClient.prepareBulk();
        for (int j = 0; j < MotorVehicleJSONArray.size(); j++) {
            JSONObject MotorVehicleJSONObject = MotorVehicleJSONArray.getJSONObject(j);
            bulkRequest.add(
                    transportClient.prepareIndex(
                            index,
                            type,
                            MotorVehicleJSONObject.getString(Constant.SYSTEM_PRIMARYKEY)
                    ).setSource(MotorVehicleJSONObject.toJSONString()));
        }
        BulkResponse rp = bulkRequest.get();
        return rp.hasFailures() != false ? Constant.SYSTEM_RESULT_SUCCESS : Constant.SYSTEM_RESULT_FALIED;
    }
}
