package PhiEngine.geom;

import PhiEngine.geom.transform.Transform;
import PhiEngine.geom.transform.Translate;

/**
 * Created by Alex on 25/12/2017.
 */
public abstract class Shape {
    public boolean contains(Vector2D v){
        return contains(v,new Translate(0,0));
    }
    public abstract  boolean contains(Vector2D v, Transform t);
    public abstract Vector2D closest(Vector2D v);
    public abstract Vector2D closest(Transform t ,Vector2D v);
}
