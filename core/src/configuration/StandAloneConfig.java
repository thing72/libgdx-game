package configuration;

import app.Game;
import app.update.UpdateTask;
import app.update.StandAloneUpdateTask;
import common.events.EventConsumer;
import common.events.SoloEventConsumer;

public class StandAloneConfig extends MainConfig {
    @Override
    protected void configure() {
        super.configure();
        bind(Game.class).asEagerSingleton();
        bind(EventConsumer.class).to(SoloEventConsumer.class).asEagerSingleton();
        bind(UpdateTask.class).to(StandAloneUpdateTask.class);
    }
}
