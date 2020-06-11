package com.example.demobase.mapper;

import com.example.demobase.entity.User;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.crypto.Data;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void insertBySome() {
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");// a为am/pm的标记
        Date date = new Date();// 获取当前时间

        System.out.println("现在时间：" + sdf.format(date)); // 输出已经格式化的现在时间（24小时制）

        User user = new User(1,"zjianfa3","123456","zjianfa@123.com",12,false, date);
//        userMapper.insertBySome(user);
        int b = userMapper.insertBySome(user);

        User a = userMapper.selectByEmail("123@123.com");
        System.out.println(a.toString());
    }

    @Test
    void testInsertBySome() {
    }

    @Test
    void selectbyID() {

        User user = userMapper.selectById(7);
        user.setActiveStatus(false);
        userMapper.updateById(user);
    }
}
