package com.xinyi.xinfo.dao.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.util.Date;

import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xinyi.xinfo.constant.Constant;
import com.xinyi.xinfo.dao.SaveData;
import com.xinyi.xinfo.utils.GreenPlumUtils;

public class SaveDataToGPImpl implements SaveData {
    private FileOutputStream fileOutputStream = null;
    private FileInputStream fileInputStream = null;
    private final Connection conn = null;

    @Override
    public String saveAsJSON(JSONArray datas) {

         final String[] tableColumnList = Constant.GP_TABLE_COLUMNS.split(Constant.SYSTEM_SPLIT_FLAG);

        try {
            StringBuffer filePath = new StringBuffer(Constant.GP_TABLE_PATH);
            Date dt = new Date();
            //System.out.println(dt.getTime());
            filePath.append(String.valueOf(dt.getTime())).append(Constant.GP_TABLE_TEMP_SUFFIX);

            fileOutputStream = new FileOutputStream(filePath.toString());
            for (int i = 0; i < datas.size(); i++) {
                StringBuffer sb = new StringBuffer("");
                JSONObject jb = datas.getJSONObject(i);
                for (String k : tableColumnList) {
                    sb.append(jb.getString(k)).append(",");
                }
                fileOutputStream.write(sb.deleteCharAt(sb.length() - 1).append("\n").toString().replaceAll("null","").getBytes());
            }
            fileInputStream = new FileInputStream(new File(filePath.toString()));
            Connection conn = GreenPlumUtils.getConnection();
            CopyManager copyManager = new CopyManager((BaseConnection) conn);
            String copyString = Constant.GP_TABLE_COPYIN.replace("#tableName#", Constant.GP_TABLE_NAME).replace("#tableColumns#", Constant.GP_TABLE_COLUMNS.toLowerCase());
            long result = copyManager.copyIn(copyString, fileInputStream);
            //System.out.println(result);

        } catch (Exception e) {
            e.printStackTrace();
            return Constant.SYSTEM_RESULT_FALIED;
        } finally {
            try {
                if (null != fileOutputStream) fileOutputStream.close();
                if (null != fileInputStream) fileInputStream.close();
                if (null != conn) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return Constant.SYSTEM_RESULT_SUCCESS;
    }
}
