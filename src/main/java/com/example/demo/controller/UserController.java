package com.example.demo.controller;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Kid;
import com.example.demo.entity.User;
import com.example.demo.entity.result.ResponseData;
import com.example.demo.service.KidService;
import com.example.demo.service.UserService;
import com.example.demo.util.FileUtil;
import com.example.demo.util.RandomGenerator;
import com.example.demo.util.RegistrationUtil;

@RestController
@RequestMapping("/users")
public class UserController extends BaseController {

    @Resource
    UserService userService;

    @Resource
    KidService kidService;

    /**
     * Create a new user.
     * 
     * @param user
     * @return JSON-formatted user info.
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseData createUser(@RequestBody User user) {
        ResponseData responseData;
        if (userService.doesUserExist(user.getPhone())) {
            return ResponseData.badRequest();
        }
        User savedUser = userService.save(user);
        responseData = ResponseData.ok();
        return responseData.setDataValue("user", savedUser);
    }

    /**
     * Get random verification code via phone number.
     * 
     * @param phone
     * @return 4 digit random verification code.
     */
    @RequestMapping(value = "/code", method = RequestMethod.GET)
    public ResponseData getVerificationCode(@RequestParam(value = "phone", required = true) String phone) {
        if (!RegistrationUtil.checkPhoneNumber(phone)) {
            logger.error("Illegal phone number");
            return ResponseData.customerError().setDataValue("error", "Illegal phone number");
        }

        if (userService.doesUserExist(phone)) {
            logger.error("User registed");
            return ResponseData.customerError().setDataValue("error:", "User registed");
        }

        // Generate random verification code
        String code = new RandomGenerator().randomCode(4);
        HttpSession session = getSession();
        session.setAttribute(phone, code);
        logger.info("Generate code:" + code);
        return ResponseData.ok().setDataValue("code", code);
    }

    /**
     * Validate code via phone number.
     * 
     * @param code
     * @return
     */
    @RequestMapping(value = "/code", method = RequestMethod.POST)
    public ResponseData validateCode(@RequestBody Map<String, String> codeMap) {
        String phone = "";
        String code = "";
        if (codeMap.containsKey("phone")) {
            phone = codeMap.get("phone");
        }
        if (codeMap.containsKey("code")) {
            code = codeMap.get("code");
        }
        logger.info("Get code: " + code + " and phone: " + phone);
        String verificationCode = (String) getSession().getAttribute(phone);
        if (verificationCode.isEmpty() || !verificationCode.equals(code)) {
            logger.error("Validation failed");
            return ResponseData.badRequest().setDataValue("error", "Validation failed");
        } else {
            return ResponseData.ok();
        }
    }

    /**
     * Upload photos of users'.
     * 
     * @param photo
     * @return
     */
    @RequestMapping(value = "/photos", method = RequestMethod.POST)
    public ResponseData uploadPhoto(@RequestParam("name") String name, @RequestParam("file") MultipartFile[] files) {

        for (int i = 0; i < files.length; i++) {
            try {
                FileUtil.uploadFile(files[i].getBytes(), String.valueOf(i) + files[i].getOriginalFilename());
            } catch (IOException e) {
                logger.error("Upload file failed!");
                return ResponseData.serverInternalError().setDataValue("error", e.toString());
            }
        }
        return ResponseData.ok();
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
    public ResponseData createKid(@RequestBody Kid kid) {
        if (!userService.doesUserExist(kid.getParentPhone())) {
            logger.error("User not found");
            return ResponseData.notFound().setDataValue("error:", "User not found");
        }
        Kid savedKid = kidService.save(kid);
        return ResponseData.ok().setDataValue("kid", savedKid);
    }

    /**
     * Upload photos of kids'.
     * 
     * @param name
     * @param files
     * @return
     */
    @RequestMapping(value = "/kids/photos", method = RequestMethod.POST)
    public ResponseData uploadKidsPhoto(@RequestParam("name") String name,
            @RequestParam("file") MultipartFile[] files) {
        for (int i = 0; i < files.length; i++) {
            try {
                FileUtil.uploadFile(files[i].getBytes(), String.valueOf(i) + files[i].getOriginalFilename());
            } catch (IOException e) {
                logger.error("Upload file failed!");
                return ResponseData.serverInternalError().setDataValue("error", e.toString());
            }
        }
        return ResponseData.ok();
    }

}
