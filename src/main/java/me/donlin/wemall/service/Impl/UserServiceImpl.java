package me.donlin.wemall.service.Impl;

import me.donlin.wemall.common.Const;
import me.donlin.wemall.common.ServerResponse;
import me.donlin.wemall.common.TokenCache;
import me.donlin.wemall.dao.UserMapper;
import me.donlin.wemall.pojo.User;
import me.donlin.wemall.service.IUserService;
import me.donlin.wemall.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @Author: Donlin
 * @Date: Created in 16:08 2018/7/7
 * @Version: 1.0
 * @Description: 用户管理模块服务接口的实现
 */
@Service("iUserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ServerResponse<User> login(String username, String password) {
        int resultCount = userMapper.checkUsername(username);
        if (resultCount == 0){
            return ServerResponse.createByError("用户名不存在");
        }

        // 密码登录MD5加密
        String md5password = MD5Util.MD5EncodeUTF8(password);
        User user = userMapper.selectLogin(username, md5password);
        if (user == null){
            return ServerResponse.createByError("密码错误");
        }

        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess("登陆成功", user);
    }

    @Override
    public ServerResponse<String> register(User user){
        int resultCount = userMapper.checkUsername(user.getUsername());
        if (resultCount > 0){
            return ServerResponse.createByError("用户名已存在");
        }
        resultCount = userMapper.checkEmail(user.getEmail());
        if (resultCount > 0){
            return ServerResponse.createByError("Email已存在");
        }

        user.setRole(Const.Role.ROLE_CUSTOMER);
        // MD5加密
        user.setPassword(MD5Util.MD5EncodeUTF8(user.getPassword()));

        resultCount = userMapper.insert(user);
        if (resultCount == 0){
            return ServerResponse.createByError("注册失败");
        }
        return ServerResponse.createBySuccess("注册成功");
    }

    @Override
    public ServerResponse<String> checkValid(String str, String type){
        if (StringUtils.isNotBlank(type)){
            //开始校验
            if (Const.USERNAME.equals(type)){
                int resultCount = userMapper.checkUsername(str);
                if (resultCount > 0){
                    return ServerResponse.createByError("用户名已存在");
                }
            }
            if (Const.EMAIL.equals(type)){
                int resultCount = userMapper.checkEmail(str);
                if (resultCount > 0){
                    return ServerResponse.createByError("Email已存在");
                }
            }
        }else {
            return ServerResponse.createByError("参数错误");
        }
        return ServerResponse.createBySuccess("校验成功");
    }

    @Override
    public ServerResponse<String> selectQuestion(String username){

        ServerResponse validResponse = this.checkValid(username, Const.USERNAME);
        if (validResponse.isSuccess()){  // 有点疑问？
            return ServerResponse.createByError("用户不存在");
        }
        String question = userMapper.selectQuestionByUsername(username);
        if (StringUtils.isNotBlank(question)){
            return ServerResponse.createBySuccess(question);
        }
        return ServerResponse.createByError("找回密码的问题是空的");
    }

    @Override
    public ServerResponse<String> checkAnswer(String username, String question, String answer){
        int resultCount = userMapper.checkAnswer(username, question, answer);
        if (resultCount > 0){
            //说明问题及问题答案是这个用户的，并且是正确的
            String forgetToken = UUID.randomUUID().toString();
            TokenCache.setKey(TokenCache.TOKEN_PREFIX + username, forgetToken);
            return ServerResponse.createBySuccess(forgetToken);
        }
        return ServerResponse.createByError("问题的答案错误");
    }

    @Override
    public ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken){
        if (StringUtils.isBlank(forgetToken)){
            return ServerResponse.createByError("参数错误，Token需要需要传递");
        }
        ServerResponse validResponse = this.checkValid(username, Const.USERNAME);
        if (validResponse.isSuccess()){
            //用户不存在
            return ServerResponse.createByError("用户不存在");
        }
        String token = TokenCache.getKey(TokenCache.TOKEN_PREFIX + username);
        if (StringUtils.isBlank(token)){
            return ServerResponse.createByError("token无效或已过期");
        }
        if (StringUtils.equals(forgetToken, token)){
            String md5PasswordNew = MD5Util.MD5EncodeUTF8(passwordNew);
            int rowCount = userMapper.updatePasswordByUsername(username, md5PasswordNew);
            if (rowCount > 0){
                return ServerResponse.createBySuccess("修改密码成功");
            }
        }else {
            return ServerResponse.createByError("token错误，请重新获取重置密码的token");
        }
        return ServerResponse.createByError("修改密码失败");
    }

    @Override
    public ServerResponse<String> resetPassword(String passwordOld, String passwordNew, User user){
        //防止横向越权，要校验一下这个用户的旧密码，一定要指定是这个用户，因为我们会查询一个count(1)，如果不指定id，那结果就是true了
        int resultCount = userMapper.checkPassword(MD5Util.MD5EncodeUTF8(passwordOld), user.getId());
        if (resultCount == 0){
            return ServerResponse.createByError("旧密码错误");
        }
        user.setPassword(MD5Util.MD5EncodeUTF8(passwordNew));
        int updateCount = userMapper.updateByPrimaryKeySelective(user);
        if (updateCount > 0){
            return ServerResponse.createBySuccess("密码更新成功");
        }
        return ServerResponse.createByError("密码更新失败");
    }

    @Override
    public ServerResponse<User> updateInformation(User user){
        //username是不能被更新的
        //email也要进行一个校验，校验新的email是不是已经存在，并且存在的email如果相同的话，不能是当前我们这个用户的
        int resultCount = userMapper.checkEmailByUserId(user.getEmail(), user.getId());
        if (resultCount > 0){
            return ServerResponse.createByError("Email已存在，请更换Email再尝试更新");
        }
        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setUsername(user.getUsername());
        updateUser.setEmail(user.getEmail());
        updateUser.setPhone(user.getPhone());
        updateUser.setQuestion(user.getQuestion());
        updateUser.setAnswer(user.getAnswer());

        int updateCount = userMapper.updateByPrimaryKeySelective(updateUser);
        if (updateCount > 0){
            return ServerResponse.createBySuccess("更新个人信息成功", updateUser);
        }
        return ServerResponse.createByError("更新个人信息失败");
    }

    @Override
    public ServerResponse<User> getInformation(Integer userId){
        User user = userMapper.selectByPrimaryKey(userId);
        if (user == null){
            return ServerResponse.createByError("找不到当前用户");
        }
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess(user);
    }

    //backend
    /**
     * 校验用户是否是管理员
     * @param user
     * @return
     */
    @Override
    public ServerResponse checkAdminRole(User user){
        if (user != null && user.getRole().intValue() == Const.Role.ROLE_ADMIN){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }




}
