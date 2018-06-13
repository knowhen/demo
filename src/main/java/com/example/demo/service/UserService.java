package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.User;

public interface UserService {

	List<User> getUserList();

	User findUserById(long id);
	
	User findByPhone(String phone);

	void save(User user);

	void edit(User user);

	void delete(long id);
}
