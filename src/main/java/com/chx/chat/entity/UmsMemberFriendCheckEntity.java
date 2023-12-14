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
@TableName("ums_member_friend_check")
public class UmsMemberFriendCheckEntity implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long sid;

    private Long rid;

    private String content;

    private Integer state;

    private LocalDateTime create_time;

    private LocalDateTime update_time;

    @TableLogic
    private Integer delFlag;
}
