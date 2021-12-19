package entity.controllers.actions;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import entity.collision.ladder.EntityLadderContact;

public class ClimbUpMovementAction implements EntityAction {
    EntityLadderContact entityLadderContact;

    public ClimbUpMovementAction(EntityLadderContact entityLadderContact) {
        this.entityLadderContact = entityLadderContact;
    }

    @Override
    public void apply(Body body) {
        body.setLinearVelocity(new Vector2(0, 5));
    }

    @Override
    public Boolean isValid(Body body) {
        return this.entityLadderContact.isOnLadder(body);
    }
}
