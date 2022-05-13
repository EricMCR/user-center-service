package com.mcr.usercenter.service;

import com.mcr.usercenter.entity.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * 用户服务测试
 *
 * @author MCR
 */
@SpringBootTest
class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    public void testAddUser() {
        User user = new User();

        user.setUsername("EricMa");
        user.setUserAccount("123");
        user.setAvatarUrl("https://lh3.googleusercontent.com/ogw/ADea4I5QTHnwMFh3iNOeNjRY2WwUkTUophtprg9jOC8E=s64-c-mo");
        user.setGender(0);
        user.setPassword("123456");
        user.setPhone("123");
        user.setEmail("mcr@163.com");

        boolean result = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(result);
    }

//    @Test
//    void userRegister() {
//        String userAccount = "Eric";
//        String userPassword = "";
//        String checkPassword = "123456";
//        long result = userService.userRegister(userAccount, userPassword, checkPassword);
//        Assertions.assertEquals(-1, result);
//
//        userAccount = "mcr";
//        userPassword = "123456";
//        result = userService.userRegister(userAccount, userPassword, checkPassword);
//        Assertions.assertEquals(-1, result);
//
//        userAccount = "Eric";
//        result = userService.userRegister(userAccount, userPassword, checkPassword);
//        Assertions.assertEquals(-1, result);
//
//        userAccount = "Er ic";
//        result = userService.userRegister(userAccount, userPassword, checkPassword);
//        Assertions.assertEquals(-1, result);
//
//        checkPassword = "123456789";
//        result = userService.userRegister(userAccount, userPassword, checkPassword);
//        Assertions.assertEquals(-1, result);
//
//        userAccount = "123";
//        result = userService.userRegister(userAccount, userPassword, checkPassword);
//        Assertions.assertEquals(-1, result);
//
//        userAccount = "Eric";
//        userPassword = "12345678";
//        checkPassword = "12345678";
//        result = userService.userRegister(userAccount, userPassword, checkPassword);
//        Assertions.assertTrue(result > 0);
//    }
}