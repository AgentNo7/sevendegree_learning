package com.sevendegree.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class ExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        log.error("{} Exception",httpServletRequest.getRequestURL(), e);
        ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());

        //当使用jackson2.x的时候使用MappingJaskson2JsonView,项目中使用1.9.12
        modelAndView.addObject("status", ResponseCode.ERROR.getCode());
        modelAndView.addObject("mag", "接口异常，详情查看服务端日志信息");
        modelAndView.addObject("data", e.toString());
        return modelAndView;
    }
}
