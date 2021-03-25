import java.util.concurrent.TimeUnit;

public class Task {
    private final String desc;
    private long timeElapsed;

    Task(String s, long l) {
        this.desc = s;
        this.timeElapsed = l;
    }

    Task(String s) {
        this.desc = s;
    }

    @Override
    public String toString() {
        if (timeElapsed != 0) {
            return String.format("%s [done in %d seconds]", desc, convertNanoToMin(timeElapsed));
        } else {
            return String.format("%s", desc);
        }
    }

    public static long convertNanoToMin(long elapsedTime) {
        long l = TimeUnit.SECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS);
        return l;
    }

    public void setTimeElapsed(long timeElapsed) {
        this.timeElapsed = timeElapsed;
    }
}

