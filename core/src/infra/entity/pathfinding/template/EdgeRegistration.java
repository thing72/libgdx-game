package infra.entity.pathfinding.template;

import com.badlogic.gdx.math.Vector2;
import com.google.inject.Inject;
import infra.entity.block.EmptyBlock;
import infra.entity.block.SolidBlock;

public class EdgeRegistration {

  @Inject EdgeStore edgeStore;
  @Inject EntityStructureFactory entityStructureFactory;
  @Inject TemplateEdgeGeneratorFactory templateEdgeGeneratorFactory;

  @Inject
  EdgeRegistration() {}

  public void edgeRegistration() {
    this.greedyRegisterEdges();
    this.templateEdgeRegistration();
  }

  public void templateEdgeRegistration() {

    TemplateEdgeGenerator templateEdgeGenerator =
        templateEdgeGeneratorFactory.create(
            new RelativeVertex(
                entityStructureFactory.createEntityStructure(),
                new RelativeCoordinates(0, 0),
                new Vector2(0, 0)));
    templateEdgeGenerator.generate();
  }

  public void greedyRegisterEdges() {
    EntityStructure moveLeftEntityStructure = entityStructureFactory.createEntityStructure();
    moveLeftEntityStructure.registerRelativeEntity(new RelativeCoordinates(0, 0), EmptyBlock.class);
    moveLeftEntityStructure.registerRelativeEntity(
        new RelativeCoordinates(-1, 0), EmptyBlock.class);
    moveLeftEntityStructure.registerRelativeEntity(
        new RelativeCoordinates(0, -1), SolidBlock.class);
    //    moveLeftEntityStructure.registerRelativeBlock(new RelativeCoordinates(-1, -1),
    // SolidBlock.class);
    RelativeVertex moveLeftFrom =
        new RelativeVertex(
            moveLeftEntityStructure, new RelativeCoordinates(0, 0), new Vector2(0, 0));
    RelativeVertex moveLeftTo =
        new RelativeVertex(
            moveLeftEntityStructure, new RelativeCoordinates(-1, 0), new Vector2(0, 0));
    GreedyEdge moveLeftGreedyEdge =
        new GreedyEdge(moveLeftEntityStructure, moveLeftFrom, moveLeftTo);

    EntityStructure moveRightEntityStructure = entityStructureFactory.createEntityStructure();
    moveRightEntityStructure.registerRelativeEntity(
        new RelativeCoordinates(0, 0), EmptyBlock.class);
    moveRightEntityStructure.registerRelativeEntity(
        new RelativeCoordinates(1, 0), EmptyBlock.class);
    moveRightEntityStructure.registerRelativeEntity(
        new RelativeCoordinates(0, -1), SolidBlock.class);
    //    moveRightEntityStructure.registerRelativeBlock(new RelativeCoordinates(1, -1),
    // SolidBlock.class);
    RelativeVertex moveRightFrom =
        new RelativeVertex(
            moveRightEntityStructure, new RelativeCoordinates(0, 0), new Vector2(0, 0));
    RelativeVertex moveRightTo =
        new RelativeVertex(
            moveRightEntityStructure, new RelativeCoordinates(1, 0), new Vector2(0, 0));
    GreedyEdge moveRightGreedyEdge =
        new GreedyEdge(moveRightEntityStructure, moveRightFrom, moveRightTo);

    EntityStructure moveCenterEntityStructure = entityStructureFactory.createEntityStructure();
    moveCenterEntityStructure.registerRelativeEntity(
        new RelativeCoordinates(0, 0), EmptyBlock.class);
    moveCenterEntityStructure.registerRelativeEntity(
        new RelativeCoordinates(0, -1), SolidBlock.class);
    RelativeVertex moveCenterFrom =
        new RelativeVertex(
            moveCenterEntityStructure, new RelativeCoordinates(0, 0), new Vector2(0, 0));
    RelativeVertex moveCenterTo =
        new RelativeVertex(
            moveCenterEntityStructure, new RelativeCoordinates(0, 0), new Vector2(0, 0));
    GreedyEdge centerGreedyEdge =
        new GreedyEdge(moveCenterEntityStructure, moveCenterFrom, moveCenterTo);

    EntityStructure moveDownEntityStructure = entityStructureFactory.createEntityStructure();
    moveDownEntityStructure.registerRelativeEntity(new RelativeCoordinates(0, 0), EmptyBlock.class);
    moveDownEntityStructure.registerRelativeEntity(
        new RelativeCoordinates(0, -1), EmptyBlock.class);
    RelativeVertex moveDownFrom =
        new RelativeVertex(
            moveDownEntityStructure, new RelativeCoordinates(0, 0), new Vector2(0, 0));
    RelativeVertex moveDownTo =
        new RelativeVertex(
            moveDownEntityStructure, new RelativeCoordinates(0, -1), new Vector2(0, 0));
    GreedyEdge moveDownGreedyEdge =
        new GreedyEdge(moveDownEntityStructure, moveDownFrom, moveDownTo);

    this.edgeStore.add(moveRightGreedyEdge);
    this.edgeStore.add(moveLeftGreedyEdge);
    this.edgeStore.add(centerGreedyEdge);
    this.edgeStore.add(moveDownGreedyEdge);
  }
}
