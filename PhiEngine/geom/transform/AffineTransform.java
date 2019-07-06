package PhiEngine.geom.transform;

import PhiEngine.comp.GameObject;
import PhiEngine.geom.Vector2D;

/**
 * Created by Alex on 13/12/2017.
 */
public class AffineTransform extends Transform {
    private final double[] flatMatrix;

    public static final AffineTransform WT = new AffineTransform(null, 1,0,10,0,1,-500);
    AffineTransform(GameObject gameObject, double...flatMatrix) {
        super(gameObject);
        this.flatMatrix = flatMatrix;
    }

    public AffineTransform concat(Transform t){//FIXME
        Vector2D m1 = applyLocal(t.m11(),t.m21(),0);
        Vector2D m2 = applyLocal(t.m12(),t.m22(),0);
        Vector2D m3 = applyLocal(t.m13(),t.m23(),1);
        flatMatrix[0] = m1.x;
        flatMatrix[1] = m2.x;
        flatMatrix[2] = m3.x;
        flatMatrix[3] = m1.y;
        flatMatrix[4] = m2.y;
        flatMatrix[5] = m3.y;
        return this;
    }
    @Override
    protected Vector2D applyLocal(double x, double y, double z) {
        return Vector2D.valueOf(x*m11()+y*m12()+z*m13(),x*m21()+y*m22()+z*m23());
    }

    @Override
    public double m11() {
        return flatMatrix[0];
    }

    @Override
    public double m12() {
        return flatMatrix[1];
    }

    @Override
    public double m13() {
        return flatMatrix[2];
    }

    @Override
    public double m21() {
        return flatMatrix[3];
    }

    @Override
    public double m22() {
        return flatMatrix[4];
    }

    @Override
    public double m23() {
        return flatMatrix[5];
    }


    @Override
    public Transform inverse() {
        double det = m11()*m22() - m12()*m21();
        double
                M11 = m22()/det,
                M21 = -m21()/det,
                M12 = -m12()/det,
                M22 = m11() /det,
                M13 = (m12()*m23() - m22()*m13())/det,
                M23 = (m21()*m13() - m11()*m23())/det;
        return new AffineTransform(getGameObject(),M11,M12,M13,M21,M22,M23);

    }
}
