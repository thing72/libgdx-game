package entity.controllers.actions;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.google.inject.Inject;

public class StopMovementAction implements EntityAction {

    @Inject
    StopMovementAction() {
    }

    @Override
    public void apply(Body body) {
        body.setLinearVelocity(new Vector2(0, body.getLinearVelocity().y));
    }

    @Override
    public Boolean isValid(Body body) {
        return true;
    }
}
