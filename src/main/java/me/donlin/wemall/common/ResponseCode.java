package me.donlin.wemall.common;

/**
 * @Author: Donlin
 * @Date: Created in 16:24 2018/7/7
 * @Version: 1.0
 * @Description: 该枚举类定义服务端响应的状态代码
 */
public enum ResponseCode {

    SUCCESS(0, "SUCCESS"),
    ERROR(1, "ERROR"),
    ILLEGAL_ARGUMENT(2, "ILLEGAL_ARGUMENT"),
    NEED_LOGIN(10, "NEED_LOGIN");

    private final int code;
    private final String desc;

    ResponseCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }
    public String getDesc() {
        return desc;
    }

}

