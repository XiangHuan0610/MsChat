package com.chx.chat.netty.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.redis.core.index.PathBasedRedisIndexDefinition;

import java.io.Serializable;

/**
 * @Author: 无敌代码写手
 * @CreateTime: 2023年12月08日
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CommunicationACK implements Serializable {
    private String msgId;

    private Boolean confirm = null;
}
