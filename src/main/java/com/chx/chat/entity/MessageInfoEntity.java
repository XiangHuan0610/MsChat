package com.chx.chat.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
@TableName("mms_message_info")
public class MessageInfoEntity implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String msgId;

    private Long sid;

    private Long rid;

    private Integer type;

    private String content;

    private LocalDateTime sendTime;

    private LocalDateTime createTime;

    @TableLogic
    private Integer delFlag;

}
