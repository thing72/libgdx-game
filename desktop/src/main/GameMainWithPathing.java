package main;

import app.GameScreen;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.google.inject.Guice;
import com.google.inject.Injector;
import configuration.StandAloneConfig;
import entity.pathfinding.EdgeRegistration;

public class GameMainWithPathing {
    public static void main(String[] arg) {
        Injector injector = Guice.createInjector(new StandAloneConfig());
        GameScreen gameScreen = injector.getInstance(GameScreen.class);
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        EdgeRegistration edgeRegistration = injector.getInstance(EdgeRegistration.class);
        edgeRegistration.edgeRegistration();

        config.height = 500;
        config.width = 500;
        new LwjglApplication(gameScreen, config);
    }
}
