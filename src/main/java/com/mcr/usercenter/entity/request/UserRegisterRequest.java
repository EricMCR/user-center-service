package com.mcr.usercenter.entity.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 *
 * @author mcr
 */
@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = -7969466361696586245L;

    private String userAccount;

    private String password;

    private String checkPassword;
}
