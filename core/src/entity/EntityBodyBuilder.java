package entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.google.inject.Inject;
import common.Coordinates;
import entity.block.Block;
import entity.collision.EntityPoint;
import entity.collision.ground.GroundPoint;
import entity.collision.ground.GroundSensorPoint;
import entity.collision.ladder.LadderPoint;

public class EntityBodyBuilder {

    @Inject
    public EntityBodyBuilder() {
    }

    public Body createEntityBody(World world, Coordinates coordinates) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(
                coordinates.getXReal() * Entity.coordinatesScale,
                coordinates.getYReal() * Entity.coordinatesScale);

        Body theBody = world.createBody(bodyDef);
        theBody.setUserData(this);
        PolygonShape shape = new PolygonShape();

        //    shape.setAsBox(0.2f, 0.3f); // Entity.coordinatesScale / 2.1f
        shape.setAsBox(
                Entity.staticWidth / 2f,
                Entity.staticHeight / 2f,
                new Vector2(
                        -(Entity.coordinatesScale - Entity.staticWidth) / 2f,
                        -(Entity.coordinatesScale - Entity.staticHeight) / 2f),
                0);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.restitution = 0;
        Fixture bodyFixture = theBody.createFixture(fixtureDef);

        Filter filter = new Filter();
        filter.categoryBits = 1;
        filter.maskBits = 2;
        bodyFixture.setFilterData(filter);

        bodyFixture.setUserData(new EntityPoint(theBody));

        FixtureDef jumpFixtureDef = new FixtureDef();
        PolygonShape jumpShape = new PolygonShape();
        jumpShape.setAsBox(
                Entity.staticWidth / 2f,
                5f,
                new Vector2(-Entity.staticWidth / 8f, -Entity.staticHeight / 2f - 3f),
                0);
        jumpFixtureDef.shape = jumpShape;
        jumpFixtureDef.isSensor = true;

        Fixture jumpFixture = theBody.createFixture(jumpFixtureDef);
        jumpFixture.setUserData(new GroundSensorPoint(theBody));
        theBody.setFixedRotation(true);
        return theBody;
    }

    public Body createSolidBlockBody(World world, Coordinates coordinates) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(
                coordinates.getXReal() * Entity.coordinatesScale,
                coordinates.getYReal() * Entity.coordinatesScale);

        Body theBody = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Block.staticWidth / 2.0f, Block.staticHeight / 2f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0f;
        fixtureDef.restitution = 0;
        Fixture blockFixture = theBody.createFixture(fixtureDef);

        Filter filter = new Filter();
        filter.categoryBits = 2;
        filter.maskBits = 1;
        blockFixture.setFilterData(filter);

        blockFixture.setUserData(new GroundPoint());
        return theBody;
    }

    public Body createEmptyBlockBody() {
        return null;
    }

    public Body createEmptyLadderBody(World world, Coordinates coordinates) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(
                coordinates.getXReal() * Entity.coordinatesScale,
                coordinates.getYReal() * Entity.coordinatesScale);

        Body theBody = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Block.staticWidth / 2.0f, Block.staticHeight / 2f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0f;
        fixtureDef.restitution = 0;
        fixtureDef.isSensor = true;
        Fixture blockFixture = theBody.createFixture(fixtureDef);

        Filter filter = new Filter();
        filter.categoryBits = 2;
        filter.maskBits = 1;
        blockFixture.setFilterData(filter);

        blockFixture.setUserData(new LadderPoint());
        return theBody;
    }
}
