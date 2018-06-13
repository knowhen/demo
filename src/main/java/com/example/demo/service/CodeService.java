package com.example.demo.service;

import com.example.demo.entity.Code;

public interface CodeService {

	void saveCode(Code code);
	
	String findByPhone(String phone);
}
