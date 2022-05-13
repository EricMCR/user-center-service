package com.mcr.usercenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mcr.usercenter.entity.domain.User;
import com.mcr.usercenter.result.BaseResult;

import javax.servlet.http.HttpServletRequest;

/**
 * @author mcr
 * @description 针对表【user(用户)】的数据库操作Service
 * @createDate 2022-05-05 21:54:58
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param userAccount   账户
     * @param userPassword  密码
     * @param checkPassword 校验密码
     * @return
     */
    BaseResult userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * 用户登录
     *
     * @param userAccount  账户
     * @param userPassword 密码
     * @return 返回脱敏后的瑛胡信息
     */
    BaseResult userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户脱敏
     *
     * @param originalUser
     * @return
     */
    User getSafeUser(User originalUser);
}
