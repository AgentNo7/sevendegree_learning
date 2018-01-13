package com.sevendegree.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.sevendegree.common.ServerResponse;
import com.sevendegree.dao.ShippingMapper;
import com.sevendegree.pojo.Shipping;
import com.sevendegree.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by aqiod on 2018/1/3.
 */
@Service("iuShippingService")
public class ShippingServiceImpl implements IShippingService {

    @Autowired
    private ShippingMapper shippingMapper;

    public ServerResponse add(Integer userId, Shipping shipping) {
        shipping.setUserId(userId);
        int rowCount = shippingMapper.insert(shipping);
        if (rowCount > 0) {
            Map result = Maps.newHashMap();
            result.put("shippingId", shipping.getId());
            return ServerResponse.createBySuccess("新建地址成功", result);
        }
        return ServerResponse.createByErrorMessage("新建地址失败");
    }

    public ServerResponse<String> del(Integer userId, Integer shippingId) {
        int rowCount = shippingMapper.deleteByPrimaryKeyAndUserId(userId, shippingId);
        if (rowCount > 0) {
            return ServerResponse.createBySuccess("删除地址成功");
        }
        return ServerResponse.createByErrorMessage("删除地址失败");
    }

    public ServerResponse<String> update(Integer userId, Shipping shipping) {
        shipping.setUserId(userId);
        int rowCount = shippingMapper.updateByShipping(shipping);
        if (rowCount > 0) {
            return ServerResponse.createBySuccess("更新地址成功");
        }
        return ServerResponse.createByErrorMessage("更新地址失败");
    }

    public ServerResponse<Shipping> select(Integer userId, Integer shippingId) {
        Shipping shipping = shippingMapper.selectByShippingIdAndUserID(shippingId, userId);
        if (shipping != null) {
            return ServerResponse.createBySuccess("查询地址成功", shipping);
        }
        return ServerResponse.createByErrorMessage("无法查询到地址");
    }

    public ServerResponse<PageInfo> list(Integer userId, int pageNumber, int pageSize) {
        PageHelper.startPage(pageNumber, pageSize);
        List<Shipping> shippingList = shippingMapper.selectByUserID(userId);
        if (shippingList != null) {
            PageInfo pageInfo = new PageInfo(shippingList);
            return ServerResponse.createBySuccess("获取多页地址成功", pageInfo);
        }
        return ServerResponse.createByErrorMessage("无法查询到地址");
    }



}
