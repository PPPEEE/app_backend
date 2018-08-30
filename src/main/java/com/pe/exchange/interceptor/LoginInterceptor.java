package com.pe.exchange.interceptor;

import com.alibaba.fastjson.JSON;
import com.pe.exchange.common.Result;
import com.pe.exchange.common.Results;
import com.pe.exchange.redis.RedisOps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
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

	}

	/*
	 * 进入controller层之前拦截请求
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
		String token = request.getHeader("token");
		/*if(!StringUtils.isEmpty(token)||!stringRedisTemplate.hasKey(token)){
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=utf-8");
			try(PrintWriter writer = response.getWriter();) {
				Result unauthorized = Results.unauthorized();
				writer.print(JSON.toJSONString(unauthorized));

			} catch (IOException e) {
				//log.error("返回错误码异常",e);
			}
			return false;
		}*/
		return true;
	}

}
