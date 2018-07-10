package com.example.jac.place.app.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DoubleUtils {
    public static final double DELTA_2_DIGITS = 0.0001d;
    public static double round2Digits(double v) {

        BigDecimal bd;
        if(isPositive(v))
            bd = new BigDecimal(v+DELTA_2_DIGITS).setScale(2, RoundingMode.HALF_UP);
        else
            bd = new BigDecimal(v-DELTA_2_DIGITS).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static boolean isPositive(double value) {
        return BigDecimal.valueOf(value).compareTo(BigDecimal.ZERO) > 0;
    }


    public  static long round2Long(double v) {
        if (v > 0)
            return Math.round(v + 0.00001);

        return Math.round(v - 0.00001);
    }

}
