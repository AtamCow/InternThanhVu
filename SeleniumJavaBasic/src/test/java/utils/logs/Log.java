package utils.logs;


import org.testng.log4testng.Logger;


public class Log {
    //Initialize Log4j instance
    private static Logger Log =  Logger.getLogger(Log.class);

    //Info Level Logs
    public static void info(String message) {
        Log.info(message);
    }

    //Warn Level Logs
    public static void warn(String message) {
        Log.warn(message);
    }

    //Error Level Logs
    public static void error(String message) {
        Log.error(message);
    }

    //Fatal Level Logs
    public void fatal (String message) {
        Log.fatal(message);
    }

    //Debug Level Logs
    public void debug (String message) {
        Log.debug(message);
    }
}