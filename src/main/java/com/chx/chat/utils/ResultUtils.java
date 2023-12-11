package com.chx.chat.utils;

import org.springframework.validation.BindingResult;

import java.util.concurrent.atomic.AtomicReference;

public class ResultUtils {
    public static AtomicReference<String> verify(BindingResult result){
        AtomicReference<String> defaultMessage = new AtomicReference<>("");
        // 1.获取错误
        result.getFieldErrors().forEach((item) -> {
            // 2.获取到错误提示
            defaultMessage.set(item.getDefaultMessage());
        });

        return defaultMessage;
    }
}