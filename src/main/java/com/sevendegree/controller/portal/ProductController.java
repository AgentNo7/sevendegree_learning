package com.sevendegree.controller.portal;

import com.github.pagehelper.PageInfo;
import com.sevendegree.common.ServerResponse;
import com.sevendegree.service.IProductService;
import com.sevendegree.vo.ProductDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by aqiod on 2017/12/30.
 */
@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductService iProductService;

    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse<ProductDetailVo> detail(Integer productId) {
        return iProductService.getProductDetail(productId);
    }

    @RequestMapping(value =" /{productId}", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<ProductDetailVo> detailRESTful(@PathVariable("productId") Integer productId) {
        return iProductService.getProductDetail(productId);
    }

    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse<PageInfo> List(@RequestParam(value = "keyword", required = false) String keyword,
                                         @RequestParam(value = "categoryId", required = false) Integer categoryId,
                                         @RequestParam(value = "pageNumber", defaultValue = "1")  int pageNumber,
                                         @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                         @RequestParam(value = "orderBy", defaultValue = "") String orderBy) {
        return iProductService.getProductByKeywordCategory(keyword, categoryId, pageNumber, pageSize, orderBy);
    }

//    @RequestMapping("list.do")
//    @ResponseBody
//    public ServerResponse<PageInfo> List(@RequestParam(value = "keyword", required = false) String keyword,
//                                         @RequestParam(value = "categoryId", required = false) Integer categoryId,
//                                         @RequestParam(value = "pageNumber", defaultValue = "1")  int pageNumber,
//                                         @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
//                                         @RequestParam(value = "orderBy", defaultValue = "") String orderBy) {
//        return iProductService.getProductByKeywordCategory(keyword, categoryId, pageNumber, pageSize, orderBy);
//    }
}
