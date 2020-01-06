package com.xinyi.xinfo.dao;

import java.util.HashMap;

/**
 * 向mysql保存数据量
 */
public interface SaveDataCount {
    /**
     * 向mysql保存数据量
     */
    void saveDataCount(HashMap<String, Integer> map);
}
