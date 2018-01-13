package com.sevendegree.vo;

import java.math.BigDecimal;

/**
 * j结合了产品和购物车的一个抽象对象
 * Created by aqiod on 2018/1/1.
 */
public class CartProductVo {
    private Integer id;

    private Integer userId;

    private Integer productId;

    private Integer quantity;//购物车中此商品的数量

    private String productName;

    private String productSubtitle;

    private String productMainImge;

    private BigDecimal productPrice;

    private Integer productStoke;

    private BigDecimal productTotalPrice;

    private Integer productStock;

    private Integer productChecked;//商品是否勾选

    private String limitQuantity;//限制数量的返回结果

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLimitQuantity() {
        return limitQuantity;
    }

    public void setLimitQuantity(String limitQuantity) {
        this.limitQuantity = limitQuantity;
    }

    public Integer getProductChecked() {
        return productChecked;
    }

    public void setProductChecked(Integer productChecked) {
        this.productChecked = productChecked;
    }

    public Integer getProductStoke() {
        return productStoke;
    }

    public void setProductStoke(Integer productStoke) {
        this.productStoke = productStoke;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductMainImge() {
        return productMainImge;
    }

    public void setProductMainImge(String productMainImge) {
        this.productMainImge = productMainImge;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getProductStock() {
        return productStock;
    }

    public void setProductStock(Integer productStock) {
        this.productStock = productStock;
    }

    public String getProductSubtitle() {
        return productSubtitle;
    }

    public void setProductSubtitle(String productSubtitle) {
        this.productSubtitle = productSubtitle;
    }

    public BigDecimal getProductTotalPrice() {
        return productTotalPrice;
    }

    public void setProductTotalPrice(BigDecimal productTotalPrice) {
        this.productTotalPrice = productTotalPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
