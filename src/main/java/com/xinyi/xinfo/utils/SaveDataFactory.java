package com.xinyi.xinfo.utils;

import com.xinyi.xinfo.dao.SaveData;
import com.xinyi.xinfo.dao.impl.SaveDataToGPImpl;

public class SaveDataFactory {

    private static SaveData sd;

    /**
     * 获取存数据实例
     * @param persists 存储类型
     * @return
     */
    public static SaveData getInstance(String persists) {
        switch (persists) {
            case "gp":
                sd = new SaveDataToGPImpl();break;
            default:
                sd = null;break;
        }
        return sd;
    }
}
