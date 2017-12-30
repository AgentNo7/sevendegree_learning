package com.sevendegree.controller.backend;

import com.sevendegree.common.Const;
import com.sevendegree.common.ServerResponse;
import com.sevendegree.pojo.User;
import com.sevendegree.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by aqiod on 2017/12/24.
 */
@RequestMapping("manage/user")
public class UserManageController {

    @Autowired
    private IUserService iUserService;

    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session){
        ServerResponse<User> response = iUserService.login(username, password);
        if(response.isSuccess()){
            User user = response.getData();
            if(user.getRole() == Const.Role.ROLE_ADMIN) {
                session.setAttribute(Const.CURRENT_USER, user);
                return response;
            }
            return ServerResponse.createByErrorMessage("不是管理员，无法登录");
        }
        return response;
    }

}
