package com.sevendegree.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.sevendegree.common.ServerResponse;
import com.sevendegree.dao.CategoryMapper;
import com.sevendegree.pojo.Category;
import com.sevendegree.service.ICategoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;

import java.util.List;
import java.util.Set;

/**
 * Created by aqiod on 2017/12/26.
 */
@Service("iCategoryService")
@Slf4j
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

//    private Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    public ServerResponse addCategory(String categoryName, Integer parentId){
        if(parentId == null || StringUtils.isBlank(categoryName)){
            return ServerResponse.createByErrorMessage("添加品类参数错误");
        }

        Category category = new Category();
        category.setName(categoryName);
        category.setParentId(parentId);
        category.setStatus(true);

        int rowCount = categoryMapper.insert(category);
        if(rowCount > 0){
            return ServerResponse.createBySuccessMessage("添加品类成功");
        }

        return ServerResponse.createByErrorMessage("添加品类失败");
    }

    public ServerResponse updateCategoryName(Integer categoryId, String categoryName){
        if(categoryId == null || StringUtils.isBlank(categoryName)){
            return ServerResponse.createByErrorMessage("更新品类参数错误");
        }
        Category category = new Category();
        category.setId(categoryId);
        category.setName(categoryName);

        int rowCount = categoryMapper.updateByPrimaryKeySelective(category);
        if(rowCount > 0){
            return ServerResponse.createBySuccessMessage("更新品类名称成功");
        }
        return ServerResponse.createByErrorMessage("更新品类名称失败");
    }

    public ServerResponse<List<Category>> getChildParallelCategory(Integer categoryId){
        List<Category> categoryList = categoryMapper.selectCategoryChildByParentId(categoryId);
        if(CollectionUtils.isEmpty(categoryList)){
            log.info("未找到当前分类的子分类");
        }
        return ServerResponse.createBySuccess(categoryList);
    }

    /**
     * 递归查询本节点的ID，和child节点的id
     * @param categoryId
     * @return
     */
    public ServerResponse<List<Integer>> selectCategoryAndChildById(Integer categoryId){
        Set<Category> categorySet = Sets.newHashSet();
        findChildCategory(categorySet, categoryId);

        List<Integer> categoryIdList = Lists.newArrayList();
        if(categoryId != null){
            for(Category category : categorySet){
                categoryIdList.add(category.getId());
            }
        }
        return ServerResponse.createBySuccess(categoryIdList);
    }

    /**
     * 递归查找(myVer) 有一堆加一堆， 深度优先 ，不停创建新对象，消耗堆内存 不包含原始父节点
     * @return
     */
    private Set<Category> findChildCategory2(Integer categoryId){
        List<Category> categoryList = categoryMapper.selectCategoryChildByParentId(categoryId);
        Set<Category> set = new HashSet<>();
        //set.add(categoryMapper.selectByPrimaryKey(categoryId)); //加了这句以后会加上原始父节点 不过会多次调用 重复添加（虽然set加不进去） 如要实现最好再写一个函数添加并调用此函数
        for(Category cate : categoryList){
            set.addAll(findChildCategory2(cate.getId()));
        }
        set.addAll(categoryList);
        return set;
    }

    /**
     * 递归查找(TeachingCode) 有一个加一个，深度优先，有两个参数，递归消耗更多栈内存
     * @return
     */
    private Set<Category> findChildCategory(Set<Category> categorySet,Integer categoryId){
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if(category != null) categorySet.add(category);
        List<Category> categoryList = categoryMapper.selectCategoryChildByParentId(categoryId);
        for(Category categoryItem : categoryList){
            findChildCategory(categorySet, categoryItem.getId());
        }
        return categorySet;
    }
}
