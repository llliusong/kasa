package com.pine.kasa.common;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;

@Data
public class SessionUser implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(SessionUser.class.getCanonicalName());
    private static final long serialVersionUID = -3305805383829146380L;

    private HttpSession session;
    private Cookie[] cookies;

    public SessionUser(HttpSession session, Cookie[] cookies) {
        this.session = session;
        //this.cookies = new Cookie[]{new Cookie("lol","cVfTS3YytQtk4%2BbXZ2DwM56MOorw6VRNGyENjLYXM0N%2BYMVxVBreY4Wv59%2FQ%0A4qnh%0A")};
        this.cookies = cookies;
        parseCookie();
    }


    public SessionUser() {

    }

    private boolean parseCookie() {
        //final String key = "alsdjsowSksa@#!$";
        String cookie_str = urlDecode(getCookieByName(cookies, Constant.COOKIE_NAME));
        logger.warn("urlDecode: " + cookie_str);

        String en_str = AES.Decrypt(cookie_str, Constant.AES_KEY);
        if (en_str == null) {
            logger.warn("sessionUser decrypt error:" + cookie_str);
            return false;
        }

        String[] arr = en_str.split(",", -1);
        if (arr.length != 8) {
            logger.warn("sessionUser split size error:" + en_str);
            return false;
        }
        setUserId(Integer.valueOf(arr[0]));
        setMobile(String.valueOf(arr[1]));
        setNickname(arr[2]);
        setRoleId(Integer.valueOf(arr[3]));
        return isLogin();
    }

    private String urlDecode(String s) {
        try {
            return java.net.URLDecoder.decode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    private String getCookieByName(Cookie[] cookies, String name) {

        for (Cookie obj : cookies) {
            if (obj.getName().equals(name)) {
                return obj.getValue();
            }
        }
        return null;
    }

    public boolean isLogin() {
        return getUserId() > 0;
    }

    //    -------------------- setter and getter methods ---------------------


    private Integer userId;

    private String mobile;

    private String nickname;

    private Integer roleId;

}