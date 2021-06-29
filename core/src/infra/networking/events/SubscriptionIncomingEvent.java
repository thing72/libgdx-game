package infra.networking.events;

import com.google.inject.Inject;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import infra.chunk.ChunkRange;
import infra.common.events.Event;
import infra.networking.NetworkObjects;

public class SubscriptionIncomingEvent extends Event {

  public static String type = "subscription_incoming_event";
  List<ChunkRange> chunkRangeList;
  UUID uuid;

  @Inject
  public SubscriptionIncomingEvent(NetworkObjects.NetworkEvent networkEvent) {
    NetworkObjects.NetworkData data = networkEvent.getData();
    this.uuid = UUID.fromString(networkEvent.getUser());
    this.chunkRangeList = new LinkedList<>();
    for (NetworkObjects.NetworkData child : data.getChildrenList()) {
      chunkRangeList.add(new ChunkRange(child));
    }
  }

  public List<ChunkRange> getChunkRangeList() {
    return chunkRangeList;
  }

  public UUID getUser() {
    return this.uuid;
  }

  @Override
  public String getType() {
    return type;
  }
}
