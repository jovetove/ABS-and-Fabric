package com.example.demobase.utill;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;


enum FailState{
    status, error_code, error_des
}

enum SeccessState{
    status, token, datas
}

/**
 * 响应模板类
 * @author Administrator
 */
@Slf4j
public class ResponseTemplate {

    private JSONObject jsonObject;

    public ResponseTemplate() throws JSONException {
        jsonObject = new JSONObject();
    }


    @Override
    public String toString(){
        return jsonObject.toString();
    }

    /**
     * 添加新的字段
     * @param key
     * @throws JSONException
     */
    public void addType(String key) throws JSONException {
        jsonObject.put(key, "");
    }

    /**
     * 设置新的字段
     * @param key
     * @param value
     * @throws JSONException
     */
    public void setType(String key,Object value) throws JSONException {
        jsonObject.put(key, value);
    }

    /**
     * 错误状态
     * @return
     * @throws JSONException
     */
    public JSONObject failState() throws JSONException {
        return failState("error");
    }

    /**
     * 构造一个错误模板
     * @param error_des
     * @return JSONObject
     * @throws JSONException
     */
    public JSONObject failState(String error_des) throws JSONException {
        jsonObject.put(FailState.status.toString(),"0");
        jsonObject.put(FailState.error_code.toString(),"");
        jsonObject.put(FailState.error_des.toString(),error_des);
        return jsonObject;
    }

    /**
     * 构造一个空的成功模板
     * @return JSONObject
     * @throws JSONException
     */
    public JSONObject seccessState() throws JSONException {
        JSONObject data = new JSONObject();
        return seccessState(data);
    }

    /**
     * 输入描述信息
     * @param des 描述信息
     * @return JSONObject
     * @throws JSONException
     */
    public JSONObject seccessState(String des) throws JSONException {
        JSONObject data = new JSONObject();
        data.put("des",des);
        return seccessState(data);
    }

    /**
     * 设置成功状态
     * @param data 响应的data
     * @return  JSONObject
     * @throws JSONException
     */
    public JSONObject seccessState(JSONObject data) throws JSONException {
        jsonObject.put(SeccessState.status.toString(),"1");
        jsonObject.put(SeccessState.token.toString(),"");
        jsonObject.put(SeccessState.datas.toString(), data);
        return jsonObject;
    }
}
