package com.imooc.course.user.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.imooc.course.thrift.user.dto.UserDTO;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * 用户验证用户登录状态
 */
public abstract class LoginFilter implements Filter {
    private static Cache<String, UserDTO> cache = CacheBuilder.newBuilder().maximumSize(1000).expireAfterWrite(3, TimeUnit.MINUTES).build();

    protected abstract String userEdgeServiceAddr();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String token = httpServletRequest.getParameter("token");
        if (StringUtils.isBlank(token)) {
            Cookie[] cookies = httpServletRequest.getCookies();
            if (cookies != null) {
                for (Cookie c : cookies) {
                    if (c.getName().equals("token")) {
                        token = c.getValue();
                    }
                }
            }
        }
        UserDTO userDTO = null;
        if (StringUtils.isNotBlank(token)) {
            // 从缓存中读取
            userDTO = cache.getIfPresent(token);
            if (userDTO == null) {
                // 从接口中读取
                userDTO = requestUserInfo(token);
                // 写入缓存
                cache.put(token, userDTO);
            }
        }
        if (userDTO == null) {
            httpServletResponse.sendRedirect("http://user-edge-service:7911/user/login");
            return;
        }
        login(httpServletRequest, httpServletResponse, userDTO);
        chain.doFilter(httpServletRequest, httpServletResponse);
    }

    protected abstract void login(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, UserDTO userDTO);

    /**
     * 调用user-edge-service的http接口, 根据token获取信息
     *
     * @param token
     * @return
     */
    private UserDTO requestUserInfo(String token) {
//        String url = "http://localhost:7911/user/authentication";
        String url = "http://" + userEdgeServiceAddr() + "/user/authentication";
        @SuppressWarnings("deprecation")
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        InputStream inputStream = null;
        UserDTO userDTO = null;
        try {
            HttpResponse response = client.execute(get);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                throw new RuntimeException("request use failed! StatusLine : " + response.getStatusLine());
            }
            inputStream = response.getEntity().getContent();
            byte[] temp = new byte[1024];
            StrBuilder sb = new StrBuilder();
            int len = 0;
            while ((len = inputStream.read(temp)) > 0) {
                sb.append(new String(temp, 0, len));
            }
            userDTO = new ObjectMapper().readValue(sb.toString(), UserDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return userDTO;
    }


    @Override
    public void destroy() {

    }
}
