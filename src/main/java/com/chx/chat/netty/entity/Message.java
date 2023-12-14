package com.chx.chat.netty.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Author: 无敌代码写手
 * @CreateTime: 2023年12月07日
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Message implements Serializable {
    private String msgId;

    private Integer type;

    private Integer ack;

    private MessageContent msg;
}
