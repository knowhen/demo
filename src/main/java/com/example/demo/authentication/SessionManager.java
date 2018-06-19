package com.example.demo.authentication;

import javax.servlet.http.HttpSession;

import com.example.demo.entity.User;

public interface SessionManager {
    static final String USER_FIELD = "user";

    static final String SIGN_IN_URL = "/auth/signin";

    /**
     * Get the participant id of the currently logged in user from the user's HTTP
     * session.
     *
     * If the session is null, or if the user is not logged in, this function
     * returns null.
     *
     * @param session
     *            The user's HTTP session, usually obtained from
     *            request.getSession(false);
     * @return the user's participant id, or null if the user is not logged in.
     */
    Long getLoggedInUser(HttpSession session);

    /**
     * Get account data of the currently logged in user.
     *
     * If the session is null, or if the user is not logged in, this function
     * returns null.
     *
     * @param session
     *            The user's HTTP session, usually obtained from
     *            request.getSession(false);
     * @return the user's account data, or null if the user is not logged in.
     */
    User getLoggedInUserData(HttpSession session);

    /**
     * Bind the user's participant id to the user's session.
     *
     * This records that a user has been logged in.
     *
     * @param session
     *            The user's HTTP session, usually obtained from
     *            request.getSession(true);
     * @param id
     *            the user who has been logged in
     */
    void setLoggedInUser(HttpSession session, long id);

    /**
     * Log the user out.
     *
     * If session is null, this function has no effect.
     *
     * @param session
     *            The user's HTTP session, obtainable from
     *            request.getSession(false);
     */
    void logout(HttpSession session);

    /**
     * Get a user's HttpSession from their session token.
     *
     * @param token
     *            the session token. Eg, "JSESSION=abcdef123567890"
     * @return the user's HttpSession, or null if the token is invalid.
     */
    HttpSession getSessionFromToken(String token);
}
