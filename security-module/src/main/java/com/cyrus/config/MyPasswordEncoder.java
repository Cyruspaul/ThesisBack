package com.cyrus.config;

import com.cyrus.util.MD5;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MyPasswordEncoder extends BCryptPasswordEncoder {


    public MyPasswordEncoder() {
        this(-1);
    }

    /**
     * @param strength the log rounds to use, between 4 and 31
     */
    public MyPasswordEncoder(int strength) {

    }

    //进行密码加密
    public String encode(CharSequence rawPassword) {
        return MD5.encrypt(rawPassword.toString());
    }

    //密码比对encodedPassword为已经加密的密码存入数据库
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        log.debug("PASSWORD ENCODER ---------------" + MD5.encrypt(rawPassword.toString()));
        return encodedPassword.equals(MD5.encrypt(rawPassword.toString()));
    }

}
