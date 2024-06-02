package GameState;

public class ControlCenter {
    private static GameStateManager gsm = new GameStateManager();


    public static GameStateManager getGameStateManager() {
        return gsm;
    }
}

