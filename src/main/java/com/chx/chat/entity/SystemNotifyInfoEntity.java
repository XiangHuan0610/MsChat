package com.chx.chat.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.chx.chat.service.SystemNotifyInfoService;
import com.chx.chat.utils.TimeUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.redis.core.index.PathBasedRedisIndexDefinition;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: 无敌代码写手
 * @CreateTime: 2023年12月11日
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@TableName("mms_notify_info")

public class SystemNotifyInfoEntity implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long sid;

    private Long rid;


    private Integer type;

    private String title;

    private String content;

    private Integer state;

//    @JSONField(format = TimeUtil.DATE_TIME)
//    @JsonFormat(pattern = TimeUtil.DATE_TIME, timezone="GMT+8")
@JsonSerialize(using = LocalDateTimeSerializer.class)
@JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

//    @JSONField(format = TimeUtil.DATE_TIME)
//    @JsonFormat(pattern = TimeUtil.DATE_TIME, timezone="GMT+8")
@JsonSerialize(using = LocalDateTimeSerializer.class)
@JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;
    @TableLogic

    private Integer delFlag;

}
