package com.mcr.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mcr.usercenter.entity.domain.SafeUser;
import com.mcr.usercenter.entity.domain.User;
import com.mcr.usercenter.entity.vo.LoginVo;
import com.mcr.usercenter.mapper.UserMapper;
import com.mcr.usercenter.result.BaseResult;
import com.mcr.usercenter.service.UserService;
import com.mcr.usercenter.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* @author mcr98
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2022-05-05 21:54:58
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    private UserMapper userMapper;

    /**
     * 盐值，混淆密码
     */
    private static final String SALT = "machaoran";

    @Override
    public BaseResult userRegister(String userAccount, String password, String checkPassword) {
        // 校验
        if (StringUtils.isAnyBlank(userAccount, password, checkPassword)) {
            return BaseResult.getFailedResult(400, "账号或密码不能为空");
        }
        if (userAccount.length() < 4) {
            return BaseResult.getFailedResult(400, "账号长度不能小于4位");
        }
        if (password.length() < 8 || checkPassword.length() < 8) {
            return BaseResult.getFailedResult(400, "密码长度不能小于8位");
        }
        // 账户不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            return BaseResult.getFailedResult(400, "账号不能包含特殊字符");
        }
        // 密码和校验密码相同
        if (!password.equals(checkPassword)) {
            return BaseResult.getFailedResult(400, "两次输入密码不一致");
        }
        // 账户不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        long count = this.count(queryWrapper);
        if (count > 0) {
            return BaseResult.getFailedResult(400, "当前账号已被注册");
        }
        // 加密密码
        String encryptedPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
        // 插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setPassword(encryptedPassword);
        this.save(user);
        return BaseResult.getSuccessResult();
    }

    @Override
    public BaseResult userLogin(String userAccount, String password, HttpServletRequest request) {
        // 校验
        if (StringUtils.isAnyBlank(userAccount, password)) {
            return BaseResult.getFailedResult(400, "账号或密码不能为空");
        }
        if (userAccount.length() < 4) {
            return BaseResult.getFailedResult(400, "账号长度不能小于4位");
        }
        if (password.length() < 8) {
            return BaseResult.getFailedResult(400, "密码长度不能小于8位");
        }
        // 账户不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            return BaseResult.getFailedResult(400, "账号不能包含特殊字符");
        }
        // 加密密码
        String encryptedPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
        // 查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("password", encryptedPassword);
        User user = userMapper.selectOne(queryWrapper);
        // 用户名或密码错误
        if (user == null) {
            return BaseResult.getFailedResult(400, "账号或密码错误");
        }

        // 生成脱敏用户对象
        SafeUser safeUser = new SafeUser(user);

        // 生成token
        Map<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(user.getId()));
        map.put("userAccount", user.getUserAccount());
        map.put("userRole", String.valueOf(user.getUserRole()));
        String token = JWTUtils.getToken(map);

        // 生成登录信息对象
        LoginVo loginVo = new LoginVo(safeUser, token);
        return BaseResult.getSuccessResult(loginVo);
    }

    /**
     * 用户脱敏
     *
     * @param originalUser
     * @return
     */
    @Override
    public User getSafeUser(User originalUser) {
        User safeUser = new User();
        safeUser.setId(originalUser.getId());
        safeUser.setUsername(originalUser.getUsername());
        safeUser.setUserAccount(originalUser.getUserAccount());
        safeUser.setAvatarUrl(originalUser.getAvatarUrl());
        safeUser.setGender(originalUser.getGender());
        safeUser.setPhone(originalUser.getPhone());
        safeUser.setEmail(originalUser.getEmail());
        safeUser.setStatus(originalUser.getStatus());
        safeUser.setUserRole(originalUser.getUserRole());
        safeUser.setCreateTime(originalUser.getCreateTime());

        return safeUser;
    }
}




