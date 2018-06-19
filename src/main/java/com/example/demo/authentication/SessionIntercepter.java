package com.example.demo.authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import com.example.demo.entity.User;

public class SessionIntercepter implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HttpSession session = request.getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute("user");
            if (user != null) {
                return true;
            }
        }
        String url = "redirect:???";
        request.getRequestDispatcher(url).forward(request, response);
        return false;
    }

}
