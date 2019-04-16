package util;

import java.text.SimpleDateFormat;

public class Log {

    private static java.util.Date date = new java.util.Date();
    private static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

    public static void WriteLine(MessageType mType, String text) {

        switch(mType) {
            case INFO:
                System.out.println("[INFO] [" + sdf.format(date) + "] " + text);

            break;

            case ERROR:
                System.out.println("[ERROR] [" + sdf.format(date) + "] " + text);
            break;
        }

    }
}
