package com.tcook.github.favlag.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionControllerAdvice {

	private static final String ERROR = "errorPage";

	@ExceptionHandler(Exception.class)
	public ModelAndView exception(HttpServletRequest req, Exception e) {
		ModelAndView mav = new ModelAndView();
	    mav.addObject("exception", e.getMessage());
	    mav.addObject("url", req.getRequestURL());
	    mav.setViewName(ERROR);
		return mav;
	}
}