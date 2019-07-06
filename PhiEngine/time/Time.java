package PhiEngine.time;

/**
 * Created by Alex on 10/12/2017.
 */
public class Time {
    private static double timeScale = 0;
    private static double fd =1D / 240D;

    public static double fixedDelta(){
        return timeScale*fd;
    }

    public static double delta(){
        return 1/30D ;
    }

    public static synchronized void setTimeScale(double timeScale) {
        Time.timeScale = timeScale;
    }

    public static double timeScale() {
        return timeScale;
    }
}
