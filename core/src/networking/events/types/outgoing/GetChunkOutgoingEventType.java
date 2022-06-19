package networking.events.types.outgoing;

import app.user.UserID;
import chunk.ChunkRange;
import common.events.types.EventType;
import networking.NetworkObjects;
import networking.events.interfaces.SerializeNetworkEvent;

public class GetChunkOutgoingEventType extends EventType implements SerializeNetworkEvent {
  static String type = "get_chunk";
  ChunkRange chunkRange;
  UserID userID;

  public GetChunkOutgoingEventType(ChunkRange chunkRange, UserID userID) {
    this.chunkRange = chunkRange;
    this.userID = userID;
  }

  public ChunkRange getChunkRange() {
    return chunkRange;
  }

  @Override
  public String getEventType() {
    return type;
  }

  public UserID getUserID() {
    return this.userID;
  }

  @Override
  public NetworkObjects.NetworkEvent toNetworkEvent() {
    return NetworkObjects.NetworkEvent.newBuilder()
        .setData(this.chunkRange.toNetworkData())
        .setUser(this.userID.toString())
        .setEvent(type)
        .build();
  }
}
