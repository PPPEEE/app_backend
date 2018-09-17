package com.pe.exchange.utils;

public class PasswordUtil {

    public static String encryptPwd(String pwd,String salt){
        return SHA256Util
            .sha256Str(pwd + SHA256Util.sha256Str(salt));
    }
}
