package com.sevendegree.service;

import com.sevendegree.common.ServerResponse;
import com.sevendegree.vo.CartVo;

/**
 * Created by aqiod on 2018/1/1.
 */
public interface ICartService {

    ServerResponse<CartVo> add(Integer userId,Integer productId,Integer count);

    ServerResponse<CartVo> update(Integer userId,Integer productId,Integer count);

    ServerResponse<CartVo> delete(Integer userId, String productIds);

    public ServerResponse<CartVo> list(Integer userId);

    ServerResponse<CartVo> selectOrUnSelectAll(Integer userId, Integer checked, Integer productId);

    ServerResponse<Integer> getCartProductCount(Integer userId);
}
