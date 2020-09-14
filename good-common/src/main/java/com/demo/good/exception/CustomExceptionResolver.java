package com.demo.good.exception;

import com.demo.good.util.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.demo.good.common.ErrorEnum;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常
 * Create by yaoping.ni
 */
@Component
@Slf4j
public class CustomExceptionResolver implements HandlerExceptionResolver {


    private final String message="重复插入";
    /**
     * 记录错误日志信息
     * @param request
     * @param e
     */
    private void recordError(HttpServletRequest request, Exception e){
        // log记录异常信息
        StringBuffer sb = new StringBuffer();
        sb.append("系统发生异常：");
        sb.append(DateTimeUtil.getFormatDateTime());
        sb.append(" ，来源：");
        sb.append(request.getRemoteAddr());
        sb.append(" ，请求地址：");
        sb.append(request.getRequestURI());
        sb.append(" ，异常信息：");
        sb.append(e.getMessage());
        log.error(sb.toString());
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse response, Object o, Exception e) {
        ModelAndView mv = new ModelAndView(new MappingJackson2JsonView());
        Map<String, String> map = new HashMap<>();
        map.put("code", "500");
        if (e instanceof DataIntegrityViolationException) {
            map.put("message", e.getMessage());
            if(e.getMessage().indexOf("Duplicate entry")>0){
                map.put("message",message);
            }
        }
        else if(e instanceof APIException){
            map.put("code", ((APIException) e).getCode().toString());
            map.put("message", ((APIException) e).getMsg());
        }
        else {
            map.put("message", ErrorEnum.SERVICE_EXCEPTION.getMsg());
        }
        mv.addAllObjects(map);
        recordError(httpServletRequest,e);
        return mv;
    }
}

