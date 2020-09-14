package com.demo.good.config.filter;

import com.alibaba.fastjson.JSON;
import com.demo.good.common.CommonResult;
import com.demo.good.config.PathConfig;
import com.demo.good.util.RedisUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import static com.demo.good.constants.Constants.TOKEN;



/**
 * 使用token进行api调用
 */
@Component
public class LoginFilter implements Filter {

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        // 暂时先全部通过
        if (true) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            String requestURI = request.getRequestURI();
            String token = request.getHeader(TOKEN);
            String substr = requestURI.substring(requestURI.lastIndexOf("/"));
            ServletContext context = request.getServletContext();
            ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);
            PathConfig pathConfig = ctx.getBean(PathConfig.class);
            RedisUtil redisUtil = ctx.getBean(RedisUtil.class);
            List<String> paths = pathConfig.getPaths();

            if (!paths.contains(substr)) {
                if (token != null) {
                    if (redisUtil.get(token) != null) {
                        filterChain.doFilter(request, response);
                    } else {
                        responseData(response);
                    }
                } else {
                    responseData(response);
                }
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }

    }

    private void responseData(HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(CommonResult.unauthorized("")));
    }


}