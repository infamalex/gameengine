package PhiEngine.comp;

import PhiEngine.geom.Ray2D;
import PhiEngine.geom.Vector2D;
import PhiEngine.geom.transform.Translate;
import PhiEngine.time.Time;

/**
 * Created by Alex on 10/12/2017.
 */
public class RigidBody extends AbstractComponent{
    public void addVel(Vector2D vel) {
        this.vel = this.vel.add(vel);
    }

    public Vector2D getVel() {
        return vel;
    }

    public void setVel(Vector2D vel) {
        this.vel = vel;
    }

    private Vector2D vel;
    public RigidBody(GameObject gameObject, double mass) {
        super(gameObject);
        vel = Vector2D.valueOf(0,0);
    }


    @Override
    public void fixedUpdate() {
        Vector2D pos = getGameObject().transformField.get().getLocalPosition();
        vel= vel.add(Vector2D.valueOf(0,-getGameObject().getWorld().gravity.get()),Time.fixedDelta());
        Vector2D newPos = pos.add(vel,Time.fixedDelta());
        getGameObject().transformField.set(new Translate(newPos).asAffine());
    }
}
