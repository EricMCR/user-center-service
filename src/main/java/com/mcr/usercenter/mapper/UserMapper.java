package com.mcr.usercenter.mapper;

import com.mcr.usercenter.entity.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author mcr98
* @description 针对表【user(用户)】的数据库操作Mapper
* @createDate 2022-05-05 21:54:58
* @Entity generator.domain.User
*/

@Mapper
public interface UserMapper extends BaseMapper<User> {

}




