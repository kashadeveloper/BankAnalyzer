package com.studying.bankAnalyzer.utils;

public class HelperFunctions {
    public static String getColorByAmount(double amount) {
        if (amount >= 100) {
            return "red";
        }
        if (amount >= 60) {
            return "yellow";
        }
        if (amount > 0) {
            return "green";
        }

        return "blue";
    }
}
