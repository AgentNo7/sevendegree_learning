package com.sevendegree.service;

import com.github.pagehelper.PageInfo;
import com.sevendegree.common.ServerResponse;
import com.sevendegree.pojo.Shipping;

/**
 * Created by aqiod on 2018/1/3.
 */
public interface IShippingService {

    ServerResponse add(Integer userId, Shipping shipping);

    ServerResponse<String> del(Integer userId, Integer shippingId);

    ServerResponse<String> update(Integer userId, Shipping shipping);

    ServerResponse<Shipping> select(Integer userId, Integer shippingId);

    ServerResponse<PageInfo> list(Integer userId, int pageNumber, int pageSize);
}
