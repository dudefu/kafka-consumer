package com.xinyi.xinfo.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import com.xinyi.xinfo.constant.Constant;
import com.xinyi.xinfo.dao.SaveDataCount;
import com.xinyi.xinfo.utils.MysqlUtils;

public class SaveDataCountImpl implements SaveDataCount {
    /**查询设备id对应的类型*/
    private final String query_type = Constant.MYSQL_SQL_QUERY_TYPE;
    /**查询当前日是否有数据插入*/
    private final String qeury_data_count = Constant.MYSQL_SQL_QEURY_DATA_COUNT;
    /**插入每天新增数据量*/
    private final String insert_total = Constant.MYSQL_SQL_INSERT_TOTAL;
    /**更新每天新增数据量*/
    private final String update_total = Constant.MYSQL_SQL_UPDATE_TOTAL;
    /**插入截止当天数据总量*/
    private final String insert_data_count = Constant.MYSQL_SQL_INSERT_DATA_COUNT;
    /**更新截止当日数据总量*/
    private final String update_data_count = Constant.MYSQL_SQL_UPDATE_DATA_COUNT;



    static Map<String, String> deviceMap = new HashMap();
    final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
    final String date = df.format(new Date());// new Date()为获取当前系统时间
    ResultSet rs;
    Statement st;

    static {
        //配置每个小类对应在mysql 中的英文描述（type）
        String deviceType = Constant.DEVICE_TYPE;
        String device[] = deviceType.split(Constant.SYSTEM_SPLIT_FLAG);
        for (String str : device) {
            deviceMap.put(str.split(":")[0], str.split(":")[1]);
        }
    }

    @Override
    public void saveDataCount(HashMap<String, Integer> map) {
        try {
            Connection conn = MysqlUtils.getConnection();
            Set<String> set = map.keySet();

            String s = set.toString();

            st = conn.createStatement();

            rs = st.executeQuery(query_type.replace("#deviceIds#", s.substring(1, s.length() - 1)));
            Map<String, String> typeMap = new HashMap<String, String>();
            while (rs.next()) {
                //设备id对应的type类型map
                typeMap.put(rs.getString(0), rs.getString(1));
            }
            Map<String, Integer> resultMap = new HashMap<>();
            for (String str : set) {
                String key = typeMap.get(str);
/*                int value = map.get(str);
                if (resultMap.containsKey(key)) {
                    resultMap.put(key, resultMap.get(key) + value);
                } else {
                    resultMap.put(key, value);
                }
*/
                //统计当前批次每类设备采集到的数据量
                resultMap.put(key, (resultMap.get(key) == null ? 0 : resultMap.get(key)) + map.get(str));
            }

            for (String str : resultMap.keySet()) {
                rs = st.executeQuery(qeury_data_count.replaceAll("#type#", str).replaceAll("#date#", date));
                while (rs.next()) {
                    if (rs.getInt(0) > 0) { //总量有数据则update 否则 insert
                        st.execute(insert_data_count.replaceAll("#type#", str).replaceAll("#date#", date).replaceAll("#count#", String.valueOf(resultMap.get(str))));
                    } else {
                        st.execute(update_data_count.replaceAll("#type#", str).replaceAll("#date#", date).replaceAll("#count#", String.valueOf(resultMap.get(str))));
                    }
                    if (rs.getInt(1) > 0) { //当天新增数量有数据则update 否则 insert
                        st.execute(update_total.replaceAll("#type#", str).replaceAll("#date#", date).replaceAll("#count#", String.valueOf(resultMap.get(str))));
                    } else { //插入当天新增的数据量
                        st.execute(String.format(insert_total, str, resultMap.get(str), date));
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
