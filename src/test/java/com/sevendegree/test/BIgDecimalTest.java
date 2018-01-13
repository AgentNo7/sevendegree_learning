package com.sevendegree.test;

import org.junit.Test;

import java.math.BigDecimal;

/**
 * Created by aqiod on 2018/1/1.
 */
public class BIgDecimalTest {


    @Test
    public void test1(){
        System.out.println(0.05+0.01);
        System.out.println(1.0-0.42);
        System.out.println(4.015*100);
        System.out.println(123.3/100);
    }
    @Test
    public void test2(){
        BigDecimal bigDecimal1 = new BigDecimal(0.05);
        BigDecimal bigDecimal2 = new BigDecimal(0.01);
        System.out.println(bigDecimal1.add(bigDecimal2));
    }
    @Test
    public void test3(){
        BigDecimal bigDecimal1 = new BigDecimal("0.05");
        BigDecimal bigDecimal2 = new BigDecimal("0.01");
        System.out.println(bigDecimal1.add(bigDecimal2));
    }



}
