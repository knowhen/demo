package com.example.demo.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {
	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void test() throws Exception {
		
		User user = new User();
		user.setPhone("13333333333");
		user.setPassword("123456");
		user.setArea("郑州市二七区");
		user.setRelationship("父亲");
		userRepository.save(user);

		Assert.assertEquals(1, userRepository.findAll().size());
		Assert.assertEquals("父亲", userRepository.findById(0).getRelationship());
	}
}
