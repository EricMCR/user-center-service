package com.mcr.usercenter.entity.domain;

import lombok.Data;

import java.util.Date;

/**
 * 脱敏用户
 *
 * @author mcr
 */
@Data
public class SafeUser {

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 用户头像
     */
    private String avatarUrl;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 用户状态 0-正常
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 用户角色 0-普通用户 1-管理员
     */
    private Integer userRole;

    public SafeUser(User user) {
        this.setId(user.getId());
        this.setUsername(user.getUsername());
        this.setUserAccount(user.getUserAccount());
        this.setAvatarUrl(user.getAvatarUrl());
        this.setGender(user.getGender());
        this.setPhone(user.getPhone());
        this.setEmail(user.getEmail());
        this.setStatus(user.getStatus());
        this.setCreateTime(user.getCreateTime());
        this.setUserRole(user.getUserRole());
    }
}
