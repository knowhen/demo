package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Kid;
import com.example.demo.repository.KidRepository;
import com.example.demo.service.KidService;

@Service
public class KidServiceImpl implements KidService {

	@Autowired
	KidRepository kidRepository;

	@Override
	public void save(Kid kid) {
		kidRepository.save(kid);
	}
}
