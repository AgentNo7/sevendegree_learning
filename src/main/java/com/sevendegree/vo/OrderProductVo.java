package com.sevendegree.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by aqiod on 2018/1/6.
 */
public class OrderProductVo {

    private List<OrderItemVo> orderItemVoList;

    private BigDecimal productTotalPrice;

    private String imageHost;

    public String getImageHost() {
        return imageHost;
    }

    public void setImageHost(String imageHost) {
        this.imageHost = imageHost;
    }

    public List<OrderItemVo> getOrderItemVoList() {
        return orderItemVoList;
    }

    public void setOrderItemVoList(List<OrderItemVo> orderItemVoList) {
        this.orderItemVoList = orderItemVoList;
    }

    public BigDecimal getProductTotalPrice() {
        return productTotalPrice;
    }

    public void setProductTotalPrice(BigDecimal productTotalPrice) {
        this.productTotalPrice = productTotalPrice;
    }
}
