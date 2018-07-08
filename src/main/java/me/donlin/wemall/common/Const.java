package me.donlin.wemall.common;

/**
 * @Author: Donlin
 * @Date: Created in 17:23 2018/7/7
 * @Version: 1.0
 * @Description: 常量类
 */
public class Const {

    public static final String CURRENT_USER = "currentUser";

    public static final String EMAIL = "email";
    public static final String USERNAME = "username";

    public interface Role{
        int ROLE_CUSTOMER = 0;  //普通用户
        int ROLE_ADMIN = 1;     //管理员
    }
}
