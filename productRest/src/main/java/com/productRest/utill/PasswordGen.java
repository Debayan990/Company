package com.productRest.utill;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordGen {

    public static void main(String[] args) {
        PasswordEncoder passwordEncoder= new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("anu#123"));
        System.out.println(passwordEncoder.encode("deb#123"));
    }
}
