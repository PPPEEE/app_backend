package com.pe.exchange.utils;

import java.util.Random;

public class VeriCodeUtils {
    public static String random(int d) {
        if (d < 1) {
            return "0";
        } else {
            StringBuilder str = new StringBuilder();

            for(int i = 0; i < d; ++i) {
                str.append((new Random()).nextInt(10));
            }

            return str.toString();
        }
    }
}
