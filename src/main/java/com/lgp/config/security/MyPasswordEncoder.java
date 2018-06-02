package com.lgp.config.security;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-04-26 22:31
 */
public class MyPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence arg0) {
        return arg0.toString();
    }

    @Override
    public boolean matches(CharSequence arg0, String arg1) {
        return arg1.equals(arg0.toString());
    }
}
