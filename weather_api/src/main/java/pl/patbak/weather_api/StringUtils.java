package pl.patbak.weather_api;

import org.slf4j.helpers.MessageFormatter;

public class StringUtils {

    private StringUtils() {

    }

    public static String formatMessage(String message, Object... args) {
        return MessageFormatter.arrayFormat(message, args).getMessage();
    }


}
