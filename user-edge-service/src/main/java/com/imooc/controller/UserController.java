package com.imooc.controller;

import com.imooc.thrift.user.dto.UserDTO;
import com.imooc.redis.RedisClient;
import com.imooc.response.LoginResponse;
import com.imooc.response.Response;
import com.imooc.thrift.ServiceProvider;
import com.imooc.thrift.user.UserInfo;
import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private ServiceProvider serviceProvider;

    @Autowired
    private RedisClient redisClient;

    @RequestMapping(value = "/sendVerifyCode", method = RequestMethod.POST)
    @ResponseBody
    public Response sendVerifyCode(@RequestParam(value = "mobile", required = false) String mobile,
                                   @RequestParam(value = "email", required = false) String email) {
        String message = "code is: ";
        String code = randomCode("0123456789", 6);
        boolean result = false;
        try {
            if (StringUtils.isNotBlank(mobile)) {
                result = serviceProvider.getMessageService().sendMobileMessage(mobile, message + code);
                redisClient.set(mobile, code);
            } else if (StringUtils.isNotBlank(email)) {
                result = serviceProvider.getMessageService().sendEmailMessage(email, message + code);
                redisClient.set(email, code);
            } else {
                return Response.MOBILE_OR_EMAIL_REQUIRED;
            }
        } catch (TException e) {
            e.printStackTrace();
            return Response.exception(e);
        }
        if(!result) {
            return Response.SEND_VERIFY_CODE_FAIED;
        }
        return Response.SUCCESS;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Response login(@RequestParam("username") String username,
                          @RequestParam("password") String password) {

        // 验证用户名密码
        UserInfo userInfo = null;
        try {
            userInfo = serviceProvider.getUserService().getUserByName(username);
        } catch (TException e) {
            e.printStackTrace();
            return Response.USERNAME_OR_PASSWORD_ERROR;
        }
        if (userInfo == null) {
            return Response.USERNAME_OR_PASSWORD_ERROR;
        }
        if (!userInfo.getPassword().equals(password)) {  // 注: 实例密码不加密
            return Response.USERNAME_OR_PASSWORD_ERROR;
        }
        // 生成token
        String token = genToken();
        // 缓存用户
        redisClient.set(token, toDTO(userInfo), 3600);
        return new LoginResponse(token);
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(){
        return "/login";
    }


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public Response register(@RequestParam("username") String username,
                             @RequestParam("password") String password,
                             @RequestParam(value = "mobile", required = false) String mobile,
                             @RequestParam(value = "email", required = false) String email,
                             @RequestParam("verifyCode") String verifyCode) {
        String redisCode = null;
        if (StringUtils.isNotBlank(mobile)) {
            redisCode = redisClient.get(mobile);
        } else if (StringUtils.isNotBlank(email)) {
            redisCode = redisClient.get(email);
        } else {
            return Response.MOBILE_OR_EMAIL_REQUIRED;
        }
        if(!redisCode.equals(verifyCode)) {
            return Response.VERIFYCODE_INVALID;
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setEmail(email);
        userInfo.setUsername(username);
        userInfo.setPassword(password);
        try {
            serviceProvider.getUserService().registerUser(userInfo);
        } catch (TException e) {
            e.printStackTrace();
            return Response.exception(e);
        }
        return Response.SUCCESS;
    }

    @RequestMapping(value = "/authentication", method = RequestMethod.GET)
    @ResponseBody
    public UserDTO authentication(@RequestHeader("token")String token) {
        return redisClient.get(token);
    }

    private String genToken() {
        return randomCode("0123456789abcdefghigklmnopqrstuvwxyz", 32);
    }

    private String randomCode(String s, int size) {
        StringBuilder result = new StringBuilder(size);
        Random random = new Random();
        for (int i = 1; i < size; i++) {
            int loc = random.nextInt(s.length());
            result.append(s.charAt(loc));
        }
        return result.toString();
    }

    private UserDTO toDTO(UserInfo userInfo) {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(userInfo, userDTO);
        return userDTO;
    }

}
