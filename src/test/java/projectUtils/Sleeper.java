package projectUtils;

import java.util.concurrent.TimeUnit;


public class Sleeper {
    public static void sleep(long duration, TimeUnit unit) {

        long millisToSleep = 0;

        switch (unit) {
            case MILLISECONDS:
                millisToSleep = duration;
                break;
            case SECONDS:
                millisToSleep = duration * ((long) 1000);
                break;
            case MINUTES:
                millisToSleep = duration * ((long) 1000) * ((long) 60);
                break;
            default:
                break;
        }
        try {
            Thread.sleep(millisToSleep);
        } catch (Exception e) { }
    }

    public static void sleepInSeconds(long durationInSeconds) {
        sleep(durationInSeconds, TimeUnit.SECONDS);
    }

    public static void sleepInMillis(long durationInMillis) {
        sleep(durationInMillis, TimeUnit.MILLISECONDS);
    }
}

