package com.mcr.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mcr.usercenter.entity.domain.SafeUser;
import com.mcr.usercenter.entity.domain.User;
import com.mcr.usercenter.entity.request.UserLoginRequest;
import com.mcr.usercenter.entity.request.UserRegisterRequest;
import com.mcr.usercenter.result.BaseResult;
import com.mcr.usercenter.service.UserService;
import com.mcr.usercenter.utils.JWTUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户接口
 *
 * @author mcr
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public BaseResult userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            return BaseResult.getFailedResult(400, "账号或密码不能为空");
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return BaseResult.getFailedResult(400, "账号或密码不能为空");
        }
        return userService.userRegister(userAccount, userPassword, checkPassword);
    }

    @PostMapping("/login")
    public BaseResult userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            return BaseResult.getFailedResult(400, "账号或密码不能为空");
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return BaseResult.getFailedResult(400, "账号或密码不能为空");
        }
        return userService.userLogin(userAccount, userPassword, request);
    }

    @PostMapping("/search")
    public BaseResult searchUsers(String username, HttpServletRequest request) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like("username", username);
        }
        List<SafeUser> userList = userService.list(queryWrapper).stream().map(SafeUser::new).collect(Collectors.toList());
        return BaseResult.getSuccessResult(userList);
    }

    @PostMapping("/delete")
    public boolean deleteUser(@RequestBody long id, HttpServletRequest request) {
        if (!isAdmin(request)) {
            return false;
        }
        if (id <= 0) {
            return false;
        }
        return userService.removeById(id);
    }

    /**
     * 是否为管理员
     *
     * @param request
     * @return
     */
    private boolean isAdmin(HttpServletRequest request) {
        int userRole = JWTUtils.getUserRole(request);
        return userRole == 1;
    }
}
