package com.bolsadeideas.springboot.form.app.interceptors;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component("tiempoTranscurridoInterceptor")
public class TiempoTranscurridoIntercept implements HandlerInterceptor{
	
	private static final Logger logger = LoggerFactory.getLogger(TiempoTranscurridoIntercept.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		logger.info("Tiempo transcurridoInterceptor: preHandle() entrando...");
		if(request.getMethod().equalsIgnoreCase("post")) {
			return true;
		}
		if(handler instanceof HandlerMethod) {
			HandlerMethod metodo = (HandlerMethod) handler;
			logger.info("es un metodo del controlador: "+metodo.getMethod().getName());
		}
		long tiempoInicio = System.currentTimeMillis();
		request.setAttribute("tiempoInicio", tiempoInicio);
		Random random = new Random();
		Integer demora = random.nextInt(500);
		Thread.sleep(demora);
		//response.sendRedirect(request.getContextPath().concat("/login"));
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		long tiempoFin = System.currentTimeMillis();
		long tiempoInicio = (Long) request.getAttribute("tiempoInicio");
		long tiempoTranscurrido = tiempoFin - tiempoInicio;
		if(modelAndView != null) {
			modelAndView.addObject("tiempoTranscurrido",tiempoTranscurrido);
		}
		logger.info("Tiempo Transcurrido: "+tiempoTranscurrido+"milisegundos");
		logger.info("Tiempo transcurrido interceptor: postHandle() saliendo ... ");
		
		logger.info("Tiempo transcurridoInterceptor: preHandle() saliendo...");
	}

	
}
