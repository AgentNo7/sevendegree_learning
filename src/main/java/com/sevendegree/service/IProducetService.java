package com.sevendegree.service;

import com.github.pagehelper.PageInfo;
import com.sevendegree.common.ServerResponse;
import com.sevendegree.pojo.Product;
import com.sevendegree.vo.ProductDetailVo;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by aqiod on 2017/12/28.
 */
public interface IProducetService {

    ServerResponse saveOrUpdateProduct(Product product);

    ServerResponse<String> setSaleStatus(Integer productId, Integer status);

    ServerResponse<ProductDetailVo> manageProductDetail(Integer productId);

    ServerResponse getProductList(int pageNumber, int pageSize);

    ServerResponse<PageInfo> searchProduct(String productName, Integer productId, int pageNumber, int pageSize);

}
