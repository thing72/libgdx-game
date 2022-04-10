package entity.collision.ladder;

import chunk.world.exceptions.BodyNotFound;
import com.badlogic.gdx.physics.box2d.Body;
import com.google.inject.Inject;
import entity.collision.CollisionPair;
import entity.collision.CollisionService;
import entity.collision.ContactWrapper;
import entity.collision.EntityPoint;
import java.util.HashMap;
import java.util.Map;

public class EntityLadderContact implements ContactWrapper {

  @Inject CollisionService collisionService;

  Map<Body, Integer> ladderContactCounter = new HashMap<>();

  @Override
  public void beginContact(Object source, Object target) throws BodyNotFound {
    EntityPoint entityPoint = (EntityPoint) source;
    this.ladderContactCounter.putIfAbsent(entityPoint.getBody(), 0);
    int ladderCount = this.ladderContactCounter.get(entityPoint.getBody());
    this.ladderContactCounter.put(entityPoint.getBody(), ladderCount + 1);
  }

  @Override
  public void endContact(Object source, Object target) throws BodyNotFound {
    EntityPoint entityPoint = (EntityPoint) source;
    this.ladderContactCounter.putIfAbsent(entityPoint.getBody(), 0);
    int ladderCount = this.ladderContactCounter.get(entityPoint.getBody());
    this.ladderContactCounter.put(entityPoint.getBody(), ladderCount - 1);
  }

  public boolean isOnLadder(Body body) {
    return ladderContactCounter.getOrDefault(body, 0) > 0;
  }

  @Override
  public void init() {
    collisionService.addCollisionConsumer(
        new CollisionPair(EntityPoint.class, LadderPoint.class), this);
  }
}
