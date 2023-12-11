package com.chx.chat.annotate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {
    private boolean required;

    /**
     * 重写initialize方法获取注解实例
     * @param ca
     */
    @Override
    public void initialize(IsMobile ca) {
        // 重注解实例中获信息
        required = ca.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // value就是要校验的数据了
        if (value != null && required) {
            // 手机号校验规则
            System.out.println(value);
            String regexp= "^(((\\+\\d{2}-)?0\\d{2,3}-\\d{7,8})|((\\+\\d{2}-)?(\\d{2,3}-)?([1][3,4,5,7,8][0-9]\\d{8})))$";
            boolean matches = Pattern.matches(regexp, value);
            System.out.println(matches);
            return matches;
        }
        return false;
    }
}
