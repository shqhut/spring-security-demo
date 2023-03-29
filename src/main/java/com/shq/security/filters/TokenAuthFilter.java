package com.shq.security.filters;

import com.shq.security.domain.LoginUser;
import com.shq.security.utils.JwtUtil;
import com.shq.security.utils.RedisCache;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class TokenAuthFilter extends OncePerRequestFilter {

    @Autowired
    RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //从请求头中获取jwtToken
        String jwtToken = request.getHeader("jwtToken");
        if (StringUtils.isEmpty(jwtToken)){
            //jwtToken为空，当前过滤器放行不处理，后续FilterSecurityInterceptor（负责权限校验）会拦截请求
            filterChain.doFilter(request,response);
            return;
        }
        //解析jwtToken获取userId
        Claims claims;
        try {
            claims = JwtUtil.parseJWT(jwtToken);
        } catch (Exception exception) {
            throw new RuntimeException("Token非法！！");
        }
        if (!Objects.isNull(claims)){
            Long userId = Long.valueOf(claims.getSubject());
            //从缓存中获取
            String loginKey = "login"+userId;
            LoginUser loginUser = redisCache.getCacheObject(loginKey);
            if (Objects.isNull(loginUser)){
                throw new RuntimeException("用户未登录！！");
            }
            //todo:整理UsernamePasswordAuthenticationToken三个参数含义
            Authentication authentication = new UsernamePasswordAuthenticationToken(loginUser,null,null);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request,response);
    }
}
