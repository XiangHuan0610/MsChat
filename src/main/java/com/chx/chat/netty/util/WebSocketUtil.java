package com.chx.chat.netty.util;

/**
 * @Author: 无敌代码写手
 * @CreateTime: 2023年12月07日
 */
public class WebSocketUtil {

    public static Long resovlerUri(String uri) {
        if (!uri.contains("/ws")) return null;

        String[] split = uri.split("/ws");
        String[] user = split[1].split("/uid=");
        Long uid = Long.valueOf(user[1]);

        return uid;
    }
}
