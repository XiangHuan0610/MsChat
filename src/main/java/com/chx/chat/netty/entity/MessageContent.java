package com.chx.chat.netty.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.cglib.core.Local;

import java.io.Serializable;
import java.time.LocalDateTime;

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

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime sendTime;

}
