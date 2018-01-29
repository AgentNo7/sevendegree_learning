package com.sevendegree.controller.common;

import com.sevendegree.pojo.User;
import com.sevendegree.util.CookieUtil;
import com.sevendegree.util.JsonUtil;
import com.sevendegree.util.RedisShardedUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class UserUtil {

    public static User checkUserStatus(HttpServletRequest request) {
        String loginToken = CookieUtil.readLoginToken(request);
        if (StringUtils.isEmpty(loginToken)) {
            return null;
        }
        return JsonUtil.stringToObj(RedisShardedUtil.get(loginToken), User.class);
    }
}
