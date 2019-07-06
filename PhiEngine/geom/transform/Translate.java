package PhiEngine.geom.transform;

import PhiEngine.comp.GameObject;
import PhiEngine.geom.Vector2D;

/**
 * Created by Alex on 13/12/2017.
 */
public class Translate extends Transform{

    private final Vector2D position;

    public Translate(GameObject g, Vector2D position) {
        super(g);
        this.position = position;
    }

    public Translate(Vector2D position){
        this(null, position);
    }

    public Translate(double dx, double dy){
        this(Vector2D.valueOf(dx,dy));
    }

    @Override
    protected Vector2D applyLocal(double x, double y, double z) {
        return Vector2D.valueOf(position.x()+x,position.y+y);
    }

    @Override
    public double m13() {
        return position.x();
    }

    @Override
    public double m23() {
        return position.y;
    }
}
