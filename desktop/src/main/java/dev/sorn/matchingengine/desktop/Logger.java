package dev.sorn.matchingengine.desktop;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public static void info(String msg) {
        log("INFO", msg);
    }

    public static void warn(String msg) {
        log("WARN", msg);
    }

    public static void error(String msg) {
        log("ERROR", msg);
    }

    private static void log(String level, String msg) {
        String timestamp = SDF.format(new Date());
        String thread = Thread.currentThread().getName();
        System.out.printf("[%s] [%s] [%s] %s%n", timestamp, thread, level, msg);
    }
}