package com.bolsadeideas.springboot.form.app.interceptors;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component("TiempoTranscurridoInterceptor")
public class TiempoTranscurridoInterceptor implements HandlerInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(TiempoTranscurridoInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		//Si se quiere que se intercepte sólo los métodos get definidos, debe hacerse desde acá (excluir el /form post)
		if(request.getMethod().equalsIgnoreCase("post")) {
			return true;
		}
		
		if(handler instanceof HandlerMethod) {
			HandlerMethod metodo = (HandlerMethod) handler;
			logger.info("Es un método del controlador: " + metodo.getMethod().getName());
		}
		
		logger.info("TiempoTranscurridoInterceptor:preHandle() Entrando...");
		logger.info("Interceptando: " + handler);
		long tiempoInicio = System.currentTimeMillis();
		request.setAttribute("tiempoInicio", tiempoInicio);
		
		Random random = new Random();
		Integer demora = random.nextInt(500);
		Thread.sleep(demora);
		
		//Si tuviera un filtro por ejemplo para validar usuario logueado, y se encuentra que no está logueado, 
		// se debería retornar false pero haciendo un redirect a una ruta definida
		//response.sendRedirect(request.getContextPath().concat("/login"));
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		//Si se quiere que se intercepte sólo los métodos get definidos, debe hacerse desde acá (excluir el /form post)
		if(request.getMethod().equalsIgnoreCase("post")) {
			return;
		}
		
		long tiempoFin = System.currentTimeMillis();
		long tiempoInicio = (Long)request.getAttribute("tiempoInicio");
		long tiempoTranscurrido = tiempoFin - tiempoInicio;
		
		//if(modelAndView != null) { //Para que no tenga en cuenta esto cuando son otros recursos de la app (ejemplo: css,.ja)
		if(handler instanceof HandlerMethod && modelAndView != null) {   //Esto valida que sean métodos de controller
			modelAndView.addObject("tiempoTranscurrido", tiempoTranscurrido);
			logger.info("Seteando tiempoTranscurrido en postHandle " + tiempoTranscurrido + " ms");
		}
		
		logger.info("tiempoTranscurrido " + tiempoTranscurrido + " ms");
		logger.info("TiempoTranscurridoInterceptor:postHandle() Saliendo...");
		
	}

}
