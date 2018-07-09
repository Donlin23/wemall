package me.donlin.wemall.service;

import me.donlin.wemall.common.ServerResponse;
import me.donlin.wemall.pojo.User;

/**
 * @Author: Donlin
 * @Date: Created in 16:05 2018/7/7
 * @Version: 1.0
 * @Description: 用户管理模块服务接口
 */
public interface IUserService {

    ServerResponse<User> login(String username, String password);

    ServerResponse<String> register(User user);

    ServerResponse<String> checkValid(String str, String type);

    ServerResponse<String> selectQuestion(String username);

    ServerResponse<String> checkAnswer(String username, String question, String answer);

    ServerResponse<String> forgetResetPassword(String username, String passwordNew, String foegetToken);

    ServerResponse<String> resetPassword(String passwordOld, String passwordNew, User user);

    ServerResponse<User> updateInformation(User user);

    ServerResponse<User> getInformation(Integer userId);

    ServerResponse checkAdminRole(User user);
}
