package com.sevendegree.service.impl;

import com.google.common.collect.Lists;
import com.sevendegree.common.ServerResponse;
import com.sevendegree.dao.FileMapper;
import com.sevendegree.dao.UserMapper;
import com.sevendegree.pojo.User;
import com.sevendegree.service.IFileService;
import com.sevendegree.util.FTPUtil;
import com.sevendegree.util.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Created by aqiod on 2017/12/29.
 */
@Service("iFileService")
@Slf4j
public class FileServiceImpl implements IFileService {

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private UserMapper userMapper;

//    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    public String upload(MultipartFile file, String path) {
        String fileName = file.getOriginalFilename();
        //获取扩展名
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
        String uploadFileName = UUID.randomUUID().toString() + "." + fileExtensionName;
        log.info("开始上传文件，上传的文件名：{}，上传的路径：{}，新文件名：{}", fileName, path, uploadFileName);

        File fileDir = new File(path);
        if (!fileDir.exists()) {
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        File targetFile = new File(path, uploadFileName);

        try {
            file.transferTo(targetFile);
            //文件已经上传成功

            //上传到FTP服务器上
            FTPUtil.uploadFile(Lists.newArrayList(targetFile));

            //删除服务器端文件
            targetFile.delete();
        } catch (IOException e) {
            log.error("上传文件异常", e);
            return null;
        }

        return targetFile.getName();
    }

    public ServerResponse<String> uploadSame(MultipartFile file, String path, Integer userId) {
        System.out.println("uploadSame method");
        String fileName = file.getOriginalFilename();
        log.info("开始上传文件，上传的文件名：{}，上传的路径：{}，新文件名：{}", fileName, path, fileName);

        User user = userMapper.selectByPrimaryKey(userId);
        if (user == null) {
            return ServerResponse.createByErrorMessage("用户不存在");
        }

        File fileDir = new File(path);
        if (!fileDir.exists()) {
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        File targetFile = new File(path, fileName);
        System.out.println("upload start");
        try {
            //文件上传成功
            file.transferTo(targetFile);
            com.sevendegree.pojo.File fileInsert = new com.sevendegree.pojo.File();
            fileInsert.setUserId(userId);
            fileInsert.setFileName(fileName);
            fileInsert.setDesc(fileName + "的描述");
            fileInsert.setUrl(PropertiesUtil.getProperty("storage.prefix") + user.getUsername() + "/" + targetFile.getName());
            int rowCount = fileMapper.insert(fileInsert);
            if (rowCount == 0) {
                return ServerResponse.createByErrorMessage("新建数据失败");
            }
            //上传到FTP服务器上
//            boolean success = FTPUtil.uploadFile(Lists.newArrayList(targetFile));
//            System.out.println("上传" + (success ? "成功" : "失败") );
            //删除服务器端文件
            //targetFile.delete();
        } catch (IOException e) {
            log.error("上传文件异常", e);
            return null;
        }

        return ServerResponse.createBySuccess(targetFile.getName());
    }

    public ServerResponse list(Integer userId) {
        List<com.sevendegree.pojo.File> fileList = fileMapper.selectByUserId(userId);
        if (CollectionUtils.isEmpty(fileList)) {
            return ServerResponse.createBySuccess("用户暂无文件");
        }
        return ServerResponse.createBySuccess(fileList);
    }
}