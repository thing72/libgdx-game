package app.client;

import app.UpdateLoop;
import app.render.BaseCamera;
import chunk.Chunk;
import chunk.ChunkRange;
import com.google.inject.Inject;
import common.Clock;
import common.GameStore;
import common.events.EventService;
import common.exceptions.SerializationDataMissing;
import generation.ChunkGenerationManager;
import networking.client.ClientNetworkHandle;
import networking.events.EventTypeFactory;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientUpdateLoop extends UpdateLoop {
    @Inject
    public Clock clock;
    @Inject
    public GameStore gameStore;
    public ExecutorService executor;
    @Inject
    EventService eventService;
    @Inject
    ChunkGenerationManager chunkGenerationManager;

    public ClientUpdateLoop() {
        executor = Executors.newCachedThreadPool();
    }

    @Override
    public void run() {
        this.clock.tick();
        List<Callable<Chunk>> callableChunkList =
                this.gameStore.getChunkOnClock(this.clock.currentTick);

        callableChunkList.addAll(this.chunkGenerationManager.generateActiveEntities());

        try {
            executor.invokeAll(callableChunkList);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.eventService.firePostUpdateEvents();
    }
}
