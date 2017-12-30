package com.sevendegree.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by aqiod on 2017/12/29.
 */
public interface IFileService {

    String upload(MultipartFile file, String path);
}
