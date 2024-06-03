package GameState;

public class ControlCenter {
    private static GameStateManager gsm = new GameStateManager();
    public static boolean isAudioOn = true;

    public static GameStateManager getGameStateManager() {
        return gsm;
    }
}

