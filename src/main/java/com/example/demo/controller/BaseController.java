package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.example.demo.entity.result.ExceptionMsg;
import com.example.demo.entity.result.Response;

@Controller
public class BaseController {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	protected Response result(ExceptionMsg msg) {
		return new Response(msg);
	}

	protected Response result() {
		return new Response();
	}

}
