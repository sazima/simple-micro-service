package com.imooc.controller;

import com.imooc.dto.UserDTO;
import com.imooc.redis.RedisClient;
import com.imooc.response.LoginResponse;
import com.imooc.response.Response;
import com.imooc.thrift.ServiceProvider;
import com.imooc.thrift.user.UserInfo;
import org.apache.thrift.TException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Random;

@Controller
public class UserController {
    @Autowired
    private ServiceProvider serviceProvider;

    @Autowired
    private RedisClient redisClient;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Response login(@RequestParam("username")String username,
                      @RequestParam("password")String password){

        // 验证用户名密码
         UserInfo userInfo = null;
        try {
            userInfo = serviceProvider.getUserService().getUserByName(username);
        } catch (TException e) {
            e.printStackTrace();
            return Response.USERNAME_OR_PASSWORD_ERROR;
        }
        if(userInfo == null){
            return Response.USERNAME_OR_PASSWORD_ERROR;
        }
        if(!userInfo.getPassword().equals(password)){  // 注: 实例密码不加密
            return Response.USERNAME_OR_PASSWORD_ERROR;
        }
        // 生成token
        String token = genToken();
        // 缓存用户
        redisClient.set(token, toDTO(userInfo), 3600);
        return new LoginResponse(token);
    }

    private String genToken() {
        return randomCode("0123456789abcdefghigklmnopqrstuvwxyz", 32);
    }

    private String randomCode(String s, int size) {
        StringBuilder result = new StringBuilder(size);
        Random random = new Random();
        for(int i=1; i<size; i++){
         int loc = random.nextInt(s.length());
         result.append(s.charAt(loc));
        }
        return result.toString();
    }
    private UserDTO toDTO(UserInfo userInfo){
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(userInfo, userDTO);
        return userDTO;
    }
}
