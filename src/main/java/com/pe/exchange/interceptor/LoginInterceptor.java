package com.pe.exchange.interceptor;

import com.alibaba.fastjson.JSON;
import com.pe.exchange.common.Result;
import com.pe.exchange.common.Results;
import com.pe.exchange.entity.User;
import com.pe.exchange.redis.RedisOps;
import com.pe.exchange.utils.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

	private final static String REQUEST_ID = "requestId";

	@Autowired
	private RedisOps redisOps;
	/*
	 * 视图渲染之后的操作
	 */
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	/*
	 * 处理请求完成后视图渲染之前的处理操作
	 */
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		MDC.remove(REQUEST_ID);
	}

	/*
	 * 进入controller层之前拦截请求
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
		String uuid = UUID.randomUUID().toString();
		MDC.put(REQUEST_ID, uuid);
		String token = request.getHeader("token");
		if(StringUtils.isEmpty(token)||!redisOps.hasKey(token)){
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=utf-8");
			try(PrintWriter writer = response.getWriter()) {
				Result unauthorized = Results.unauthorized();
				writer.print(JSON.toJSONString(unauthorized));

			} catch (IOException e) {
				log.error("返回错误码异常",e);
			}
			return false;
		}
		String s = redisOps.get(token);
		UserUtil.set(JSON.parseObject(s, User.class));
		return true;
	}

}
