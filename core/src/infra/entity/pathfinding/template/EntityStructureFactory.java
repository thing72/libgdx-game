package infra.entity.pathfinding.template;

import com.google.inject.Inject;
import infra.common.GameStore;

public class EntityStructureFactory {

  @Inject GameStore gameStore;

  public EntityStructure createEntityStructure() {
    return new EntityStructure(this.gameStore);
  }
}
