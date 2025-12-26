package com.studying.bankAnalyzer.utils;

import com.studying.bankAnalyzer.Constants;
import com.studying.bankAnalyzer.OSName;

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

    public static OSName GetPlatformOS() {
        var os = System.getProperty("os.name").toLowerCase();
        if (os.contains("windows")) {
            return OSName.WINDOWS;
        }
        if (os.contains("linux")) {
            return OSName.LINUX;
        }
        if (os.contains("mac")) {
            return OSName.MACOSX;
        }

        return OSName.LINUX;
    }

    public static String GetFigletByOS(OSName os) {
        return switch (os) {
            case WINDOWS -> Constants.Figlet.Windows;
            case LINUX -> Constants.Figlet.Linux;
            case MACOSX -> Constants.Figlet.MacOSX;
            default -> Constants.Figlet.Windows;
        };
    }
}
