package com.example.demobase.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.demobase.entity.User;
import org.assertj.core.util.DateUtil;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 */
@Service("TokenServiceImpl")
public class TokenServiceImpl {

    /**
     * 获取token，采用 用户id 和用户密码进行加密
     * @param user
     * @return 返回token字符串
     */
    public String getToken(User user) {
        String token="";
        token= JWT.create()
                // 将 user id 保存到 token 里面
                .withAudience(String.valueOf(user.getId()))
                // 以 password 作为 token 的密钥
//                .withClaim("date", )
                .sign(Algorithm.HMAC256(user.getPassword()));
        return token;
    }



}