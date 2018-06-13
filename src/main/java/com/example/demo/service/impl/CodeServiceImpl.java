package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Code;
import com.example.demo.repository.CodeRepository;
import com.example.demo.service.CodeService;

@Service
public class CodeServiceImpl implements CodeService {

	@Autowired
	CodeRepository codeRepository;
	
	@Override
	public void saveCode(Code code) {
		codeRepository.save(code);
	}

	@Override
	public String findByPhone(String phone) {
		Code code = codeRepository.findByPhone(phone);
		return code.getCode();
	}

}
