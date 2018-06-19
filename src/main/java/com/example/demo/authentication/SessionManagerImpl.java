package com.example.demo.authentication;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

public class SessionManagerImpl implements SessionManager {
    //private static final Logger logger = LoggerFactory.getLogger(SessionManagerImpl.class);
    private static final String USER_FIELD = "user";

    @Autowired
    private UserRepository userRepository; // Cache the user data int the session object???

    @Override
    public Long getLoggedInUser(HttpSession session) {
        if (session != null) {
            return (Long) session.getAttribute(USER_FIELD);
        } else {
            return null;
        }
    }

    @Override
    public User getLoggedInUserData(HttpSession session) {
        // Consider caching the account data in the session object.
        Long userId = getLoggedInUser(session);
        if (userId != null) {
            return userRepository.findById(userId).get();
        } else {
            return null;
        }
    }

    @Override
    public void setLoggedInUser(HttpSession session, long id) {
        // Check sission and id not null first
        session.setAttribute(USER_FIELD, id);
    }

    @Override
    public void logout(HttpSession session) {
        if (session != null) {
            // This function should also remove any other bound fields in the session
            // object.
            session.removeAttribute(USER_FIELD);
        }
    }


    @Override
    public HttpSession getSessionFromToken(String token) {
        // Check token not null first
        return null;
    }
}
