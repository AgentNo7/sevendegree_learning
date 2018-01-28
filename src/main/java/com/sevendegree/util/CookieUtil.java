package com.sevendegree.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class CookieUtil {

    private static final String COOKIE_DOMAIN = ".test.com";
    private static final String COOKIE_MAME = "sevendegree_login_token";

    public static void writeLoginToken(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie(COOKIE_MAME, token);
        cookie.setDomain(COOKIE_DOMAIN);
        cookie.setPath("/");//代表早根目录下的代码能够获取到cookie
        cookie.setHttpOnly(true);
        //单位是秒，-1表示永久
        //如果这个maxage不设置的话，cookie就不会写入硬盘，而是写在内存。只在当前页面有效
        cookie.setMaxAge(60 * 60 * 24 * 90);

        log.info("write cookieName:{}, cookieValue:{} ", cookie.getName(), cookie.getValue());
        response.addCookie(cookie);
    }

    public static String readLoginToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                log.info("read cookieName:{}, cookieValue:{} ", cookie.getName(), cookie.getValue());
                if (StringUtils.equals(cookie.getName(), COOKIE_MAME)) {
                    log.info("return cookieName:{}, cookieValue:{} ", cookie.getName(), cookie.getValue());
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public static void deleteLoginToken(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null){
            for (Cookie cookie : cookies) {
                if (StringUtils.equals(cookie.getName(), cookie.getValue())) {
                    cookie.setDomain(COOKIE_DOMAIN);
                    cookie.setPath("/");
                    cookie.setMaxAge(0);// set 0 , means delete
                    log.info("delete cookieName:{}, cookieValue:{} ", cookie.getName(), cookie.getValue());
                    response.addCookie(cookie);
                    return ;
                }
            }
        }
    }

}
