package com.chx.chat.http;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author: 无敌代码写手
 * @CreateTime: 2023年12月11日
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class HttpAddFriendRequest {
    private Long uid;

    private String code;

    private String content;

}
