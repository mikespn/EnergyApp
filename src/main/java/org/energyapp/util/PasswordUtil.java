//package org.energyapp.util;
//
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Component;
//
//@Component
//public class PasswordUtil {
//
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    public PasswordUtil(){
//        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
//    }
//
//    public String encodePassword(String rawPassword){
//        return bCryptPasswordEncoder.encode(rawPassword);
//    }
//
//    public boolean matches(String rawPassword, String encodePassword){
//        return bCryptPasswordEncoder.matches(rawPassword, encodePassword);
//    }
//}
