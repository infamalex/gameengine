package PhiEngine.geom.transform;

import PhiEngine.comp.AbstractComponent;
import PhiEngine.comp.Component;
import PhiEngine.comp.Game;
import PhiEngine.comp.GameObject;
import PhiEngine.geom.Vector2D;
import PhiEngine.util.DoubleField;
import PhiEngine.util.Field;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.Optional;
import java.util.function.UnaryOperator;
import static PhiEngine.util.Fields.*;

/**
 * Created by Alexander on 12/02/17.
 */
public abstract class Transform implements UnaryOperator<Vector2D>,Component {

    private Transform parentTransform;
    public  final Field<Transform> parent = field(this,(UnaryOperator<Transform> & Serializable)t->Game.activeWorld.worldTransform.get(),privateSet());
    private GameObject gameObject;

    public AffineTransform asAffine(){
        return new AffineTransform(getGameObject(),m11(),m12(),m13(),m21(),m22(),m23());
    }
    public Transform(GameObject gameObject) {

        this.gameObject = gameObject;
    }

    public Vector2D getPosition() {
        return apply(0,0);
    }

    public Vector2D getLocalPosition() {
        return applyLocal(0,0);
    }

    Vector2D position;




    protected abstract Vector2D applyLocal(double x, double y, double z);
    public Vector2D applyLocal(double x, double y){
        return this.applyLocal(x,y,1);
    }
    public Vector2D applyLocal(Vector2D point){
        return applyLocal(point.x(),point.y(),1);
    }
    Vector2D apply(double x, double y, double z){
        if(Game.activeWorld !=null && this != Game.activeWorld.worldTransform.get()) //FIXME mess parent code
            return parent.get().apply(applyLocal(x,y,z));
        else return applyLocal(x,y,z);
    }

    public static Transform rotateAround(Vector2D v, double theta){
        return new Translate(v.scaled(-1)).concat(new Rotate(theta)).concat(new Translate(v));
    }
    public Transform concat(Transform other){
        return this.asAffine().concat(other);
    }
    @Override
    public Vector2D apply(Vector2D vector2D) {
        return apply(vector2D.x(),vector2D.y(),1);
    }

    public Vector2D apply(double x, double y) {
        return apply(x,y,1);
    }

    public double m11(){return 1D;}
    public double m12(){return 0D;}
    public double m13(){return 0D;}
    public double m21(){return 0D;}
    public double m22(){return 1D;}
    public double m23(){return 0D;}

    @Override
    public String toString() {
        return String.format("|%5.5f,%5.5f,%5.5f|",m11(),m12(),m13())+
                String.format("|%5.5f,%5.5f,%5.5f|",m21(),m22(),m23())+
                String.format("|%5.5f,%5.5f,%5.5f|",0D,0D,1D);
    }

    @Override
    public GameObject getGameObject() {
        return gameObject;
    }

    public Transform inverse(){
        return asAffine().inverse();
    }
}
