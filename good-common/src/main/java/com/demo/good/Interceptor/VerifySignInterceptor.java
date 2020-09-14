package com.demo.good.Interceptor;

import com.demo.good.util.AesEncryptUtil;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static com.demo.good.constants.Constants.SIGNATURE;

@Component
public class VerifySignInterceptor implements HandlerInterceptor {

    /*
    拦截请求过来的参数进行验签
     */

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String sign = request.getHeader(SIGNATURE);
        if (sign != null) {
            Map<String, String[]> params = request.getParameterMap();
            List paramList = Lists.newArrayList();
            for (String key : params.keySet()) {
                if (!key.equals(SIGNATURE)) {
                    String[] values = params.get(key);
                    for (int i = 0; i < values.length; i++) {
                        String value = values[i];
                        paramList.add(value);
                    }
                }
            }
            paramList.sort(Comparator.naturalOrder());
            String joinStr = String.join("|", paramList);
            if (sign.equals(AesEncryptUtil.encrypt(joinStr))) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }


}