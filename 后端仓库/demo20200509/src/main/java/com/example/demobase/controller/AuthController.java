package com.example.demobase.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.demobase.annotation.PassToken;
import com.example.demobase.annotation.RunTimer;
import com.example.demobase.annotation.UserLoginToken;
import com.example.demobase.entity.User;
import com.example.demobase.mapper.UserMapper;
import com.example.demobase.service.TokenServiceImpl;
import com.example.demobase.utill.ResponseTemplate;
import com.example.demobase.service.MailServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Administrator
 */
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired //(required=false)
    private UserMapper userMapper;

    @Autowired
    private MailServiceImpl mailServiceImpl;

    @Autowired
    private TokenServiceImpl tokenServiceImpl;

    /**
     * 电子邮件确认
     * @param token 用户token 包含密码和name
     * @return 确认的消息
     */
    @PassToken
    @RequestMapping(value = "/checkEmail", method = RequestMethod.GET)
    public String chackEmail(String token) throws JSONException {
        String userId = JWT.decode(token).getAudience().get(0);
        User user = userMapper.selectById(userId);
        // 验证 token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new RuntimeException("确认失败");
        }
        user.setActiveStatus(true);
        userMapper.updateById(user);
        ResponseTemplate template = new ResponseTemplate();
        JSONObject json = template.seccessState("确认成功");
        return json.toString();
    }

    /**
     * 用户注册 要求具备密码 用户名 邮箱等
     * @param params
     */
    @PassToken
    @RunTimer
    @RequestMapping(value = "/sign", method = RequestMethod.POST)
    public String sign(@RequestBody @Validated User params) throws JSONException {
        log.info("收到注册信息" + params.toString());
        // 响应模板
        ResponseTemplate template = new ResponseTemplate();
        User user = null;
        try {
            user = userMapper.selectByEmail(params.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String token = null;
        // 如果已存在该用户 则
        if (user != null) {
            template.setType("info","已存在该用户，需要激活");
            token = tokenServiceImpl.getToken(user);
        } else {
            try {
                userMapper.insert(params);
                template.setType("info","插入信息成功，需要验证");
                User user1 = userMapper.selectByEmail(params.getEmail());
                token = tokenServiceImpl.getToken(user1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String content = "请将这个网址复制到游览器，确认您的邮件 \n 127.0.0.1/auth/checkEmail?token=" + token;
        try {
            mailServiceImpl.sendTextMail(params.getEmail(), "确认邮件", content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", "邮件已发送到邮箱" + params.getEmail());
        jsonObject.put("url", content);
        JSONObject jsonObject2 = template.seccessState(jsonObject);
        return jsonObject2.toString();
    }

    @UserLoginToken
    @RequestMapping(value = "/unSign",method = RequestMethod.POST)
    public String unSign(@Validated User params) throws JSONException {
        ResponseTemplate template = new ResponseTemplate();
        User user = userMapper.selectById(params.getId());
        if(user.getEmail() == params.getEmail() &&
                user.getPassword() == params.getPassword() &&
                user.getName() == params.getName()){
            userMapper.deleteById(user.getId());
        }else {
            JSONObject jsonObject = template.failState("登录失败,用户不存在");
            return jsonObject.toString();
        }
        JSONObject jsonObject = template.seccessState("注销成功!");
        return jsonObject.toString();
    }


    /**
     * 用户邮箱和密码登录
     * @param user
     * @return 返回 token
     * @throws JSONException
     */
    @RunTimer
    @PassToken
    @PostMapping("/login")
    public Object login(@RequestBody @Validated User user) throws JSONException {
        log.info("用户登录信息:" + user.toString());
        ResponseTemplate template = new ResponseTemplate();
        User userForBase = userMapper.selectByEmail(user.getEmail());
        if(userForBase == null){
            JSONObject jsonObject = template.failState("登录失败,用户不存在");
            return jsonObject.toString();
        }else {
            if (!userForBase.getActiveStatus()){
                JSONObject jsonObject = template.failState("登录失败,账户未激活");
                return jsonObject.toString();
            }else if(!userForBase.getPassword().equals(user.getPassword())){
                JSONObject jsonObject = template.failState("登录失败,密码错误");
                return jsonObject.toString();
            }else {
                String token = tokenServiceImpl.getToken(userForBase);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("token", token);
                jsonObject.put("user", userForBase);
                JSONObject jsonObject2 = template.seccessState(jsonObject);
                return jsonObject.toString();
            }
        }
    }
}
