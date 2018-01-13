package com.sevendegree.service;

import com.sevendegree.common.ServerResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by aqiod on 2017/12/29.
 */
public interface IFileService {

    String upload(MultipartFile file, String path);

    ServerResponse<String> uploadSame(MultipartFile file, String path, Integer userId);

    ServerResponse list(Integer userId);
}
