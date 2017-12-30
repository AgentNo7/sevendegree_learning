package com.sevendegree.service;

import com.sevendegree.common.ServerResponse;
import com.sevendegree.pojo.Category;

import java.util.List;

/**
 * Created by aqiod on 2017/12/26.
 */
public interface ICategoryService {

    ServerResponse addCategory(String categoryName, Integer parentId);

    ServerResponse updateCategoryName(Integer categoryId, String categoryName);

    ServerResponse<List<Category>> getChildParallelCategory(Integer categoryId);

    ServerResponse<List<Integer>> selectCategoryAndChildById(Integer categoryId);
}
