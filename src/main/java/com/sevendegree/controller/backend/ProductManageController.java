package com.sevendegree.controller.backend;

import com.google.common.collect.Maps;
import com.sevendegree.common.Const;
import com.sevendegree.common.ResponseCode;
import com.sevendegree.common.ServerResponse;
import com.sevendegree.controller.common.UserUtil;
import com.sevendegree.pojo.Product;
import com.sevendegree.pojo.User;
import com.sevendegree.service.IFileService;
import com.sevendegree.service.IProductService;
import com.sevendegree.service.IUserService;
import com.sevendegree.util.PropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by aqiod on 2017/12/28.
 */
@Controller
@RequestMapping("/manage/product")
public class ProductManageController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IProductService iProductService;

    @Autowired
    private IFileService iFileService;

    @RequestMapping("save.do")
    @ResponseBody
    public ServerResponse productSave(HttpServletRequest request, Product product) {
        User user = UserUtil.checkUserStatus(request);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录管理员");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            //填充增加产品的业务逻辑
            return iProductService.saveOrUpdateProduct(product);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    @RequestMapping("set_sale_status.do")
    @ResponseBody
    public ServerResponse setSaleStatus(HttpServletRequest request, Integer productId, Integer status) {
        User user = UserUtil.checkUserStatus(request);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录管理员");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            //修改状态
            return iProductService.setSaleStatus(productId, status);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse getDetail(HttpServletRequest request, Integer productId) {
        User user = UserUtil.checkUserStatus(request);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录管理员");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            //填充业务
            return iProductService.manageProductDetail(productId);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse getList(HttpServletRequest request, @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        User user = UserUtil.checkUserStatus(request);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录管理员");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            //填充业务
            return iProductService.getProductList(pageNumber, pageSize);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    @RequestMapping("search.do")
    @ResponseBody
    public ServerResponse getProductSearch(HttpServletRequest request, String productName, Integer productId, @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        User user = UserUtil.checkUserStatus(request);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录管理员");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            //填充业务
            return iProductService.searchProduct(productName, productId, pageNumber, pageSize);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    @RequestMapping("upload.do")
    @ResponseBody
    public ServerResponse upload(@RequestParam(value = "upload_file", required = false) MultipartFile file, HttpServletRequest request) {
        User user = UserUtil.checkUserStatus(request);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录管理员");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            //填充业务
            String path = request.getSession().getServletContext().getRealPath("/upload");
            String targetFileName = iFileService.upload(file, path);
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;

            Map fileMap = Maps.newHashMap();
            fileMap.put("uri", targetFileName);
            fileMap.put("url", url);

            return ServerResponse.createBySuccess(fileMap);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    @RequestMapping("richtext_image_upload.do")
    @ResponseBody
    public Map richtextImageUpload(@RequestParam(value = "upload_file", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        Map resultMap = Maps.newHashMap();
        User user = UserUtil.checkUserStatus(request);
        if (user == null) {
            resultMap.put("success", false);
            resultMap.put("msg", "请登录管理员");
            return resultMap;
        }
        //富文本中对于返回值有自己的要求，我们使用的是simditor，所以按照simditor的要求进行返回
        //{
        //  "success":true/false,
        //      "msg":"error message", #optional
        //  "file_path":"[real file path]"
        //}
        if (iUserService.checkAdminRole(user).isSuccess()) {
            //填充业务
            String path = request.getSession().getServletContext().getRealPath("/upload");
            String targetFileName = iFileService.upload(file, path);
            if(StringUtils.isBlank(targetFileName)){
                resultMap.put("success", false);
                resultMap.put("msg", "上传失败");
                return resultMap;
            }

            String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;
            resultMap.put("success", true);
            resultMap.put("msg", "上传成功");
            resultMap.put("file_path", url);

            response.addHeader("Access-Control-Allow-Headers", "X-File_Name");
            return resultMap;
        }else {
            resultMap.put("success", false);
            resultMap.put("msg", "无权限操作");
            return resultMap;
        }
    }

}
