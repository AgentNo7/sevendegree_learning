package com.sevendegree.controller.common;

import com.sevendegree.common.Const;
import com.sevendegree.pojo.User;
import com.sevendegree.util.CookieUtil;
import com.sevendegree.util.JsonUtil;
import com.sevendegree.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class SessionExpireFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String loginToken = CookieUtil.readLoginToken(request);
        //如果loginToken不为空，继续拿信息
        if (StringUtils.isNotEmpty(loginToken)) {
            String userJson = RedisUtil.get(loginToken);
            User user = JsonUtil.stringToObj(userJson, User.class);
            if (user != null) {
                //调用expire命令
                RedisUtil.expire(loginToken, Const.RedisCacheExTime.REDIS_SESSION_EXTIME);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
