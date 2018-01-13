package com.sevendegree.dao;

import com.sevendegree.pojo.Shipping;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShippingMapper {
    int deleteByPrimaryKey(Integer id);

    int deleteByPrimaryKeyAndUserId(@Param("userId") Integer userId, @Param("id") Integer id);

    int insert(Shipping record);

    int insertSelective(Shipping record);

    Shipping selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Shipping record);

    int updateByPrimaryKey(Shipping record);

    int updateByShipping(Shipping shipping);

    Shipping selectByShippingIdAndUserID(@Param("shippingId") Integer shippingId, @Param("userId") Integer userId);

    List<Shipping> selectByUserID(@Param("userID") Integer userID);

}