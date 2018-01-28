package com.sevendegree.controller.portal;

import com.google.common.collect.Maps;
import com.sevendegree.common.Const;
import com.sevendegree.common.ResponseCode;
import com.sevendegree.common.ServerResponse;
import com.sevendegree.controller.common.UserUtil;
import com.sevendegree.pojo.User;
import com.sevendegree.service.IFileService;
import com.sevendegree.service.IUserService;
import com.sevendegree.util.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * Created by aqiod on 2018/1/13.
 */
@Controller
@RequestMapping("/file/")
public class FileController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IFileService iFileService;

    @RequestMapping("upload.do")
    @ResponseBody
    public ServerResponse upload(@RequestParam(value = "upload_file", required = false) MultipartFile file, HttpServletRequest request) {
        System.out.println("upload Controller method start");
        User user = UserUtil.checkUserStatus(request);

        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }
        if (file == null || file.isEmpty()) {
            return ServerResponse.createByErrorMessage("没有选择文件");
        }
        String path = PropertiesUtil.getProperty("storage.dir", "") + user.getUsername();//request.getSession().getServletContext().getRealPath("/upload");
        System.out.println("impl start");
        ServerResponse<String> serverResponse = iFileService.uploadSame(file, path, user.getId());
        if (!serverResponse.isSuccess()) {
            return serverResponse;
        }
        String targetFileName = serverResponse.getData();
         String url = PropertiesUtil.getProperty("storage.prefix") + user.getUsername() + "/" + targetFileName;
        Map fileMap = Maps.newHashMap();
        fileMap.put("uri", targetFileName);
        fileMap.put("url", url);
        return ServerResponse.createBySuccess(fileMap);
    }

    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse list(HttpSession session, HttpServletResponse response, HttpServletRequest request) {
        User user = UserUtil.checkUserStatus(request);//(User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }
        ServerResponse serverResponse = iFileService.list(user.getId());
        if (serverResponse.getData().equals("用户暂无文件")) {
            session.setAttribute("fileList", null);
        }
        else
            session.setAttribute("fileList", serverResponse.getData());
        try {
            response.sendRedirect("/main.jsp");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return serverResponse;
    }
}
