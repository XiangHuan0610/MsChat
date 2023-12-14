package com.chx.chat.netty.util;

import com.chx.chat.netty.entity.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bouncycastle.asn1.x500.AttributeTypeAndValue;


import java.util.Collection;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

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

    public static LinkedBlockingQueue<Message> blockingQueueParse(String json) throws JsonProcessingException {
        TypeReference<LinkedBlockingQueue<Message>> typeReference =  new TypeReference<LinkedBlockingQueue<Message>>(){};
        LinkedBlockingQueue<Message> messages = mapper.readValue(json, typeReference);
        return messages;
    }

    public static List<Message> messageCollectionParse(List<String> caches) throws JsonProcessingException {
        String json = message(caches);
        TypeReference<List<Message>> messages = new TypeReference<>(){};
        List<Message> msg = mapper.readValue(json, messages);
        return msg;
    }
}
