package com.longapp.analysis;

import java.text.DecimalFormat;


public class Utility {

    public static String formatFloat(double value) {
        DecimalFormat df = new DecimalFormat("0.0");
        return df.format(value);
    }
}
