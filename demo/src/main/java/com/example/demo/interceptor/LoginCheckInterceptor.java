package com.example.demo.interceptor;





import com.alibaba.fastjson.JSONObject;

import com.example.demo.config.GetTokenId;
import com.example.demo.entity.Result;
import com.example.demo.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Slf4j
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        String method = request.getMethod();
        if (("GET".equals(method))) {
            // 执行针对的GET请求的逻辑
            return true;
        }

        String url = request.getRequestURL().toString();
        log.info("请求的url：{}", url);

        String jwt = request.getHeader("token");

        if (!StringUtils.hasLength(jwt)) {
            log.info("请求头token为空，返回未登录信息");
            Result error = Result.error("NOT_LOGIN");
            //手动转换 对象==json -> fastJSON
            String NOTLogin = JSONObject.toJSONString(error);
            response.getWriter().write(NOTLogin);
            return false;

        }
        try {
            Map<String,Object> claims = JwtUtils.parseJWT(jwt);
            Integer id = Integer.parseInt(claims.get("id").toString());
            GetTokenId.setUserId(id);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("解析令牌失败");
            Result error = Result.error("NOT_LOGIN");
            //手动转换 对象==json -> fastJSON
            String NOTLogin = JSONObject.toJSONString(error);
            response.getWriter().write(NOTLogin);
            return false;
        }
        log.info("令牌合法，放行");
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        GetTokenId.shutdown();
    }
}
