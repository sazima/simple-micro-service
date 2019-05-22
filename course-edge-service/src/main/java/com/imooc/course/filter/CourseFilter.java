package com.imooc.course.filter;

import com.imooc.course.thrift.user.dto.UserDTO;
import com.imooc.course.user.client.LoginFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//@Component
public class CourseFilter extends LoginFilter {

//    @Value("1.1.1.1")
//    private String userEdgeServiceAddr;

    @Override
    protected String userEdgeServiceAddr() {
//        return userEdgeServiceAddr;
        return null;
    }

    @Override
    protected void login(HttpServletRequest request, HttpServletResponse response, UserDTO userDTO) {
        request.setAttribute("user",userDTO);
    }
}
