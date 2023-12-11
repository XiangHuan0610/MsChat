package com.chx.chat.enums;

public enum AppHttpCodeEnum {
    // 成功
    SUCCESS(200,"操作成功"),
    FAIL(401,"你无权操作,请登录后在操作"),
    VALID_FAIL(402,"valid 校验失败"),
    NO_OPERATOR_AUTH(403,"无权限操作"),
    INSERT_FAIL(405,"插入数据失败"),
    DATA_EXIST(406,"数据已经存在"),
    RATE_LIMIT(409,"服务器限流异常，请稍候再试"),
    SYSTEM_ERROR(500,"出现错误"),
    REQUIRE_OPENAPIKEY(504, "openapiKey不能为空"),
    OPENAPIKEY_ERROR(505,"openapiKey认证失败"),
    OPENAPIKEY_REDISEXIST(506,"sse请求未结束，不可使用"),
    THROWABLE(550,"其他异常"),
    FILE_TYPE_ERROR(507,"文件类型错误，请上传png或jpg文件"),
    FEIGN_FAIL(508,"操作错误"),
    WAREUPDATE_FAIL(509,"库存更新失败"),
    WAREDELETE_FAIL(509,"库存删除失败"),
    EMPTY_IMAGE_FAIL(509,"图片信息为空"),
    CATEGORY_DELETE_FAIL(509,"该分类被引用或者其下面还有分类"),
    ATTR_NOT_EXIST(510,"属性不存在，请在attr表中添加该属性再操作"),
    ATTR_EMPTY(511,"attrId不能为空"),
    PHONE_EXIST(512,"账号已注册"),
    PHONE_NO_REGISTER(513,"账号不存在"),
    LOGIN_ERROR(514,"账号或密码错误"),
    TOKEN_ERROR(514,"token非法"),
    TOKEN_EMPTY(514,"token为空"),
    SKUID_NOT_EXIST(514,"skuid不存在"),

    REGISTER_SUCCESS(200,"注册成功"),

    FILE_SIZE_ERROR(515, "图片最大为5M"),

    INSERT_SUCCESS(200,"新增成功"),

    DEL_SUCCESS(200,"删除成功"),

    UPDATE_SUCCESS(200,"更新成功"),

    LOGIN_SUCCESS(200,"登录成功" ),

    CATEGORY_IS_NULL(500,"分类不存在"),

    PRODUCT_LEAVEL_ERROR(500,"商品只能添加第三级分类"),

    USER_IS_NULL(500,"账号不存在" ),

    USER_IS_NOT_NULL(500,"账号已存在"),

    SEND_SUCCESS(200,"发送成功"),

    VERIFY_CODE_ERROR(500,"验证码错误"),

    VERIFY_CODE_PAST(500,"验证码失效"),
    NO_MISAPPROPRIATION(500,"禁止盗用接口"), UPLOAD_SUCCESS(200, "上传成功"),
    RECHARGE_SUCCESS(200,"充值成功" ), PAY_ERROR(500,"支付失败" ),
    INSUFFICIENT_QUOTA(200,"key额度不足" ), PAY_SCUEESS(200,"支付成功" );



    int code;
    String msg;

    AppHttpCodeEnum(int code, String errorMessage){
        this.code = code;
        this.msg = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
