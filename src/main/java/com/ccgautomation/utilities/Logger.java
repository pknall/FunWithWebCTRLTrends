package com.ccgautomation.utilities;

import com.ccgautomation.Configuration;
import com.controlj.green.addonsupport.AddOnInfo;
import java.io.PrintWriter;
import java.io.Writer;

public class Logger {

    private static final String SEPARATOR = " - ";

    private String className;
    private static final PrintWriter LOGGER;
    private static LogLevel level = Configuration.defaultLogLevel;

    static {
        Writer dateStampLogger = null;
        try {
            dateStampLogger = AddOnInfo.getAddOnInfo().getDateStampLogger();
        }
        catch (Throwable nfe) {}

        if (dateStampLogger != null) {
            LOGGER = new PrintWriter(dateStampLogger);
        } else {
            LOGGER = new PrintWriter(System.err);
        }
    }

    public Logger(String className) {
        this.className = className;
    }

    private void logIt(String message, LogLevel messageLevel) {
        StringBuilder sb = new StringBuilder();
        //sb.append(",");
        sb.append(messageLevel.name());
        sb.append(SEPARATOR);
        sb.append(className);
        sb.append(SEPARATOR);
        sb.append(message);
        LOGGER.println(sb);
    }

    public void log(String message, LogLevel messageLevel) {
        if (messageLevel.ordinal() <= this.level.ordinal()) {
            logIt(message, messageLevel);
        }

    }

    public void info(String message) {
        if (LogLevel.INFO.ordinal() <= this.level.ordinal()) { logIt(message, LogLevel.INFO); }
    }

    public void trace(String message) {
        if (LogLevel.TRACE.ordinal() <= this.level.ordinal()) { logIt(message, LogLevel.TRACE); }
    }

    public void debug(String message) {
        if (LogLevel.DEBUG.ordinal() <= this.level.ordinal()) { logIt(message, LogLevel.DEBUG); }
    }

    public void error(String message) {
        if (LogLevel.ERROR.ordinal() <= this.level.ordinal()) { logIt(message, LogLevel.ERROR); }
    }

    public void fatal(String message) {
        if (LogLevel.FATAL.ordinal() <= this.level.ordinal()) { logIt(message, LogLevel.FATAL); }
    }
}

