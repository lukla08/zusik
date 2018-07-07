package com.example.jac.place.app.utils;

import android.widget.TextView;

public class StringUtils {

    public static double toDouble(TextView source) {
        return toDouble(source.getText().toString());
    }

    public static double toDouble(String text) {
        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static int toInt(TextView source) {
        return toInt(source.getText().toString());
    }

    public static int toInt(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static long toLong(TextView source) {
        return toLong(source.getText().toString());
    }

    public static long toLong(String text) {
        try {
            return Long.parseLong(text);
        } catch (NumberFormatException e) {
            return 0;
        }
    }


}
