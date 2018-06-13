package com.example.demo.controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Code;
import com.example.demo.entity.Kid;
import com.example.demo.entity.User;
import com.example.demo.entity.result.ExceptionMsg;
import com.example.demo.entity.result.Response;
import com.example.demo.entity.result.ResponseData;
import com.example.demo.service.CodeService;
import com.example.demo.service.KidService;
import com.example.demo.service.UserService;
import com.example.demo.util.FileUtil;

@RestController
@RequestMapping("/users")
public class UserController extends BaseController {

	@Resource
	UserService userService;

	@Resource
	KidService kidService;

	@Resource
	CodeService codeService;

	/**
	 * Get all registed users.
	 * 
	 * @return JSON-formatted user list.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<?> getUserList() {
		List<User> users = userService.getUserList();
		if (users.isEmpty()) {
			String message = "Users not found!";
			return new ResponseEntity<String>(message, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	/**
	 * Create a new user.
	 * 
	 * @param user
	 * @return JSON-formatted user info.
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody User user) {
		User registUser = userService.findByPhone(user.getPhone());
		if (null != registUser) {
			String message = "Phone used!";
			return new ResponseEntity<String>(message, HttpStatus.IM_USED);
		}
		userService.save(user);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	/**
	 * Get user info via phone number.
	 * 
	 * @param phone
	 * @return JSON-formatted user info.
	 */
	@RequestMapping(value = "/{phone}", method = RequestMethod.GET)
	public ResponseData getUserByPhone(@PathVariable String phone) {
		User user = userService.findByPhone(phone);
		if (user == null)
			return new ResponseData(ExceptionMsg.USER_NOT_FOUND);
		return new ResponseData(ExceptionMsg.SUCCESS, user);
	}

	/**
	 * Get random verification code via phone number.
	 * 
	 * @param phone
	 * @return 4 digit random verification code.
	 */
	@RequestMapping(value = "/code", method = RequestMethod.GET)
	public ResponseData getCode(@RequestParam(value = "phone", required = true) String phone) {

		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(phone);
		if (false == m.matches()) {
			// Illegal phone number
			return new ResponseData(ExceptionMsg.UNFORMAT_PHONE_NUMBER);
		}

		User registUser = userService.findByPhone(phone);
		if (null != registUser) {
			// Phone number is registed
			return new ResponseData(ExceptionMsg.PhoneUsed);
		}

		// Generate random verification code
		String code = generateRandomCode();
		Code c = new Code();
		c.setCode(code);
		c.setPhone(phone);
		codeService.saveCode(c); // Persist Code
		return new ResponseData(ExceptionMsg.SUCCESS, c.getCode());
	}

	/**
	 * Validate code via phone number.
	 * 
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/code", method = RequestMethod.POST)
	public Response validateCode(@RequestBody Code code) {
		String validateCode = codeService.findByPhone(code.getPhone());
		if (validateCode.isEmpty()) {
			return new Response(ExceptionMsg.FAILED);
		} else {
			return new Response();
		}
	}

	/**
	 * Upload photos of users'.
	 * 
	 * @param photo
	 * @return
	 */
	@RequestMapping(value = "/photos", method = RequestMethod.POST)
	public ResponseEntity<?> uploadPhoto(@RequestParam("name") String name,
			@RequestParam("file") MultipartFile[] files) {

		for (int i = 0; i < files.length; i++) {
			try {
				FileUtil.uploadFile(files[i].getBytes(), String.valueOf(i) + files[i].getOriginalFilename());
				System.out.println("file:" + files[i].getOriginalFilename() + String.valueOf(i));
			} catch (IOException e) {
				logger.error("Upload file failed!");
				return new ResponseEntity<Response>(new Response(ExceptionMsg.FAILED), HttpStatus.BAD_REQUEST);
			}
		}

		return new ResponseEntity<Response>(new Response(), HttpStatus.OK);
	}

	/**
	 * Using post method request /user/kids interface. Content-type is
	 * application/json.
	 * 
	 * @param kid
	 * 
	 * @return
	 */
	@RequestMapping(value = "/kids", method = RequestMethod.POST)
	public ResponseEntity<?> createKid(@RequestBody Kid kid) {
		User registeredUser = userService.findByPhone(kid.getParentPhone());
		if (null == registeredUser) {
			String message = "User not found!";
			return new ResponseEntity<String>(message, HttpStatus.NOT_FOUND);
		}
		kidService.save(kid);
		return new ResponseEntity<Kid>(kid, HttpStatus.OK);
	}

	/**
	 * Upload photos of kids'.
	 * 
	 * @param name
	 * @param files
	 * @return
	 */
	@RequestMapping(value = "/kids/photos", method = RequestMethod.POST)
	public ResponseEntity<?> uploadKidsPhoto(@RequestParam("name") String name,
			@RequestParam("file") MultipartFile[] files) {
		for (int i = 0; i < files.length; i++) {
			try {
				FileUtil.uploadFile(files[i].getBytes(), String.valueOf(i) + files[i].getOriginalFilename());
				System.out.println("file:" + files[i].getOriginalFilename() + String.valueOf(i));
			} catch (IOException e) {
				logger.error("Upload file failed!");
				return new ResponseEntity<Response>(new Response(ExceptionMsg.FAILED), HttpStatus.BAD_REQUEST);
			}
		}

		return new ResponseEntity<Response>(new Response(), HttpStatus.OK);
	}

	/**
	 * Generate random code for user checking.
	 * 
	 * @return
	 */
	private String generateRandomCode() {
		Set<Integer> set = new HashSet<>();
		Random random = new Random();
		while (set.size() < 4) {
			set.add(random.nextInt(10));
		}
		StringBuilder builder = new StringBuilder();
		for (int i : set) {
			builder.append(String.valueOf(i));
		}
		return builder.toString();
	}
}
