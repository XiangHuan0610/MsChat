package com.chx.chat.config;

import com.chx.chat.netty.entity.Message;
import com.chx.chat.netty.entity.MessageContent;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author: 无敌代码写手
 * @CreateTime: 2023年12月10日
 */
public class Programmer {
    private static LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
    public static void main(String[] args) {
        queue.add(1);
        System.out.println(queue.contains(1));
        System.out.println(queue.size());
    }
}
