package com.chx.chat.netty.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collection;

/**
 * @Author: 无敌代码写手
 * @CreateTime: 2023年12月07日
 */
public class JsonUtil {

    public static final ObjectMapper mapper = new ObjectMapper();

    public static <T> T parse(String content,Class<T> T) throws JsonProcessingException {
        T t = mapper.readValue(content, T);
        return t;

    }

    public static <T> T queueParse(Collection<T> collection) {
        String jsonMsg = "";
        try{
            jsonMsg = mapper.writeValueAsString(collection);
        }catch (Exception e){
            e.printStackTrace();;
        }
        return (T) jsonMsg;
    }

    public static <T> String message(T t) {
        String data = null;
        try {
            data = mapper.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return data;
    }
}
