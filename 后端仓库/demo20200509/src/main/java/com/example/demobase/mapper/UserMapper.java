package com.example.demobase.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demobase.entity.User;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 */
@Service("UserMapper")
public interface UserMapper extends BaseMapper<User> {
    /**插入新的用户*/
    int insertBySome(User entity);

    User selectByEmail(String email);
}