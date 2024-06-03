package GameState;

public class GameSettings {
    private static boolean soundOn = true;
    private static boolean soundOff = false;

    public static boolean isSoundOn() {
        return soundOn;
    }
    public static boolean isSoundOff() {
        return soundOff;
    }


    public static void setSoundOn(boolean soundOn) {
        GameSettings.soundOn = soundOn;
    }
    public static void setSoundOff(boolean soundOff) {
        GameSettings.soundOff = soundOff;
    }



}
