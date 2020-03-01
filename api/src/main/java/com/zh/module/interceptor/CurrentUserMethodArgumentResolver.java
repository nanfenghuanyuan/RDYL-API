package com.zh.module.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.zh.module.annotation.CurrentUser;
import com.zh.module.entity.Users;
import com.zh.module.exception.NoTokenException;
import com.zh.module.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

/**
 * 在Controller的方法参数中使用此注解，该方法在映射时会注入当前登录的User对象
* @author lina 
* @date 2017-12-23
* @version V1.0
 */
@Component
@Slf4j
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {
	public static CurrentUserMethodArgumentResolver currentUserMethodArgumentResolver;
	@Autowired
	private UsersService usersService;

	@PostConstruct
	public void init(){
		currentUserMethodArgumentResolver = this;
		currentUserMethodArgumentResolver.usersService = this.usersService;
	}

	public CurrentUserMethodArgumentResolver() {
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		if (parameter.getParameterType().isAssignableFrom(Users.class) && parameter.hasParameterAnnotation(CurrentUser.class)) {
			return true;
		}
		return false;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		HttpServletRequest httpServletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
		log.info("地址--->" + httpServletRequest.getRequestURI());
		String token = webRequest.getHeader("token");
		// 执行认证
		if (token == null) {
			throw new NoTokenException("无token，请重新登录");
		}
		// 获取 token 中的 user id
		String userId;
		try {
			userId = JWT.decode(token).getAudience().get(0);
		} catch (JWTDecodeException j) {
			throw new RuntimeException("401");
		}
		Users user = currentUserMethodArgumentResolver.usersService.selectByPrimaryKey(Integer.valueOf(userId));
		if (user == null) {
			throw new NoTokenException("用户不存在，请重新登录");
		}
		// 验证 token
		JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
		try {
			jwtVerifier.verify(token);
		} catch (JWTVerificationException e) {
			throw new RuntimeException("401");
		}
		return user;
	}

}
