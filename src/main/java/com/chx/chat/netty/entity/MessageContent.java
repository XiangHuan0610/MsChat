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
public class MessageContent implements Serializable {
    private Long sid;

    private Long rid;

    private String content;
}
