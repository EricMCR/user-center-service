package com.mcr.usercenter;

import com.mcr.usercenter.mapper.UserMapper;
import com.mcr.usercenter.entity.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@SpringBootTest
class UserCenterApplicationTests {

	@Autowired
	private UserMapper userMapper;

	@Test
	void testDigest() throws NoSuchAlgorithmException {
		List<User> userList = userMapper.selectList(null);
		userList.forEach(System.out::println);
	}

	@Test
	void contextLoads() {
	}

}
