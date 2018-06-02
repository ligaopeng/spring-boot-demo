package com.lgp.interceptor;

import com.lgp.entity.token.TokenInfoEntity;
import com.lgp.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 类说明,https://www.jianshu.com/p/2503cde90c55
 *JWT是一种用户双方之间传递安全信息的简洁的、URL安全的表述性声明规范。
 * JWT（Json Web Token）作为一个开放的标准（RFC 7519），定义了一种简洁的、自包含的方法用于通信双方之间以Json对象的形式进行安全性信息传递
 * ，传递时有数字签名所以信息时安全的，
 * JWT使用RSA公钥密钥的形式进行签名。
 *
 * @author lgp
 * @create 2018-05-05 22:37
 */
public class JwtTokenInterceptor implements HandlerInterceptor {

    /**
     * 请求之前
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // TODO: 2018/5/6
        //暂时取消校验
        if (!request.getRequestURI().equals("/api/token")) {
            return true;
        }
        //自动排除生成token的路径，并且如果是options请求是cors跨域请求，设置allow对应头信息
        if(request.getRequestURI().equals("/api/token") || RequestMethod.OPTIONS.toString().equals(request.getMethod())) {
            return true;
        }
        //其它请求获取请求头信息
        final String authHeader = request.getHeader("X-LAuth-Token");
        try {
            //如果没有header信息
            if (authHeader == null || authHeader.trim() == "") {
                throw new Exception("not fount X-LAuth-Token");
            }
            //获取jwt实体对象接口实例
           final Claims claims = Jwts.parser().setSigningKey("LGPAuthV1.0")
                   .parseClaimsJws(authHeader).getBody();
            //从数据库中获取token
            TokenService tokenService = getService(TokenService.class, request);
            TokenInfoEntity tokenInfoEntity = tokenService.getTokenInfoByAppId(claims.getSubject());
            String token = new String(tokenInfoEntity.getToken());
            //如果内存中不存在，提示客户端获取token
            if (StringUtils.isBlank(token)) {
                throw new Exception("not found token info, please get token agin");
            }
            //判断内存中的token是否与客户端传来的一致
            if (!token.equals(authHeader)) {
                throw new Exception("not found token info, please get token agin.");
            }
        } catch (Exception e) {
            throw e;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    /**
     * 根据传入的类型获取spring管理的对应dao
     * @param clazz 类型
     * @param request 请求对象
     * @param <T>
     * @return
     */
    private <T> T getService(Class<T> clazz,HttpServletRequest request) {
        BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
        return factory.getBean(clazz);
    }
}
