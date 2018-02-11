package com.sevendegree.common.intercepter;

import com.google.common.collect.Maps;
import com.sevendegree.common.Const;
import com.sevendegree.common.ServerResponse;
import com.sevendegree.controller.common.UserUtil;
import com.sevendegree.pojo.User;
import com.sevendegree.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

@Slf4j
public class AuthorityInterceptor implements HandlerInterceptor{

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        log.info("preHandle");
        //拿到Controller中的方法
        HandlerMethod handlerMethod = (HandlerMethod) o;
        //解析HandlerMethod
        String methodName = handlerMethod.getMethod().getName();
        String className = handlerMethod.getMethod().getClass().getSimpleName();

        //解析参数，具体的key和value是什么，打印日志
        StringBuffer requestParamBuffer = new StringBuffer();
        Map paramMap = httpServletRequest.getParameterMap();
        Iterator it = paramMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String mapKey = (String) entry.getKey();
            String mapValue = StringUtils.EMPTY;// = "";
            //requset 返回的map的value是一个String数组
            Object obj = entry.getValue();
            if (obj instanceof String[]) {
                String[] strs = (String[]) obj;
                mapValue = Arrays.deepToString(strs);
            }
            requestParamBuffer.append(mapKey).append("=").append(mapValue);
        }

        if ( (StringUtils.equals(className, "UserManageController") || StringUtils.equals(className, "UserController") ) && StringUtils.equals(methodName, "login")) {
            log.info("拦截器拦截到请求，className:{},methodName:{}", className, methodName);
            //如果拦截到登录不打印参数信息，因为不安全
            return true;
        }
        log.info("拦截器拦截到请求，className:{},methodName:{}，param:{}", className, methodName,requestParamBuffer);
        User user = UserUtil.checkUserStatus(httpServletRequest);
        //校验
        if(user == null || (user.getRole() != Const.Role.ROLE_ADMIN)) {
            //返回false，不调用Controller里的方法
            httpServletResponse.reset();//添加reset()否则报异常getWriter() has already be called for this response
            httpServletResponse.setCharacterEncoding("UTF-8");//这里要设置编码，否则出现乱码
            httpServletResponse.setContentType("application/json;charset=UTF-8");

            PrintWriter out = httpServletResponse.getWriter();

            //由于富文本上传的返回值有特殊要求，故需特殊处理
            if (user == null) {
                if (StringUtils.equals(className, "ProductManageController") && StringUtils.equals(methodName, "richtextImageUpload")) {
                    Map resultMap = Maps.newHashMap();
                    resultMap.put("success", true);
                    resultMap.put("msg", "请登录管理员");
                    out.print(JsonUtil.objToString(resultMap));
                } else {
                    out.print(JsonUtil.objToString(ServerResponse.createByErrorMessage("拦截器拦截，用户未登录")));
                }
            } else{
                if (StringUtils.equals(className, "ProductManageController") && StringUtils.equals(methodName, "richtextImageUpload")) {
                    Map resultMap = Maps.newHashMap();
                    resultMap.put("success", true);
                    resultMap.put("msg", "无权限操作");
                    out.print(JsonUtil.objToString(resultMap));
                } else {
                    out.print(JsonUtil.objToString(ServerResponse.createByErrorMessage("用户无权限操作")));
                }
            }
            out.flush();//在关闭之前需要flush，情况out流数据
            out.close();
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        log.info("postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        log.info("afterCompletion");
    }
}
