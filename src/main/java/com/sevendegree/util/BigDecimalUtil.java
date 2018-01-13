package com.sevendegree.util;

import java.math.BigDecimal;

/**
 * Created by aqiod on 2018/1/1.
 */
public class BigDecimalUtil {

    private BigDecimalUtil(){

    }

    public static BigDecimal add(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.add(b2);
    }

    public static BigDecimal sub(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.subtract(b2);
    }
    public static BigDecimal mul(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.multiply(b2);
    }
    public static BigDecimal div(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        //除不尽的情况
        return b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP);//四舍五入保留两位小数
    }

    public static BigDecimal add(BigDecimal d1, BigDecimal d2) {
        return d1.add(d2);
    }

    public static BigDecimal sub(BigDecimal d1, BigDecimal d2) {
        return d1.add(d2);
    }
    public static BigDecimal mul(BigDecimal d1, BigDecimal d2) {
        return d1.add(d2);
    }

    public static BigDecimal div(BigDecimal d1, BigDecimal d2) {
        //除不尽的情况
        return d1.divide(d2, 2, BigDecimal.ROUND_HALF_UP);//四舍五入保留两位小数
    }

    public static BigDecimal add(int d1, int d2) {
        BigDecimal b1 = new BigDecimal(Integer.toString(d1));
        BigDecimal b2 = new BigDecimal(Integer.toString(d2));
        return b1.add(b2);
    }

    public static BigDecimal sub(int d1, int d2) {
        BigDecimal b1 = new BigDecimal(Integer.toString(d1));
        BigDecimal b2 = new BigDecimal(Integer.toString(d2));
        return b1.subtract(b2);
    }
    public static BigDecimal mul(int d1, int d2) {
        BigDecimal b1 = new BigDecimal(Integer.toString(d1));
        BigDecimal b2 = new BigDecimal(Integer.toString(d2));
        return b1.multiply(b2);
    }
    public static BigDecimal div(int d1, int d2) {
        BigDecimal b1 = new BigDecimal(Integer.toString(d1));
        BigDecimal b2 = new BigDecimal(Integer.toString(d2));
        //除不尽的情况
        return b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP);//四舍五入保留两位小数
    }
}
