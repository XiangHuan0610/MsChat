package com.chx.chat.http;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Author: 无敌代码写手
 * @CreateTime: 2023年12月08日
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class HttpLoginRequest  implements Serializable {
    private String upt;

    private String password;
}
