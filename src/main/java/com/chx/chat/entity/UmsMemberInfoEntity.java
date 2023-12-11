package com.chx.chat.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.chx.chat.annotate.IsMobile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * @Author: 无敌代码写手
 * @CreateTime: 2023年12月07日
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@TableName("ums_member_info")
public class UmsMemberInfoEntity {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    private String name;

    @NotBlank(message = "手机不能为空")
    @IsMobile(message = "手机号格式不正确")
    private String upt;

    @NotBlank(message = "密码不能为空")
    @TableField(value = "pas")
    @Size(min=3, max=16,message = "密码长度为3-16")
    private String password;

    private String gender;

    private String headImg;

    private LocalDateTime createTime;

    private LocalDateTime loginTime;

    private LocalDateTime updateTime;

    @TableLogic
    private Integer delFlag;

}
