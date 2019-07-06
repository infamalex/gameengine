package PhiEngine.geom.transform;

import PhiEngine.comp.GameObject;
import PhiEngine.geom.Vector2D;

/**
 * Created by Alex on 06/11/2017.
 */
public class Rotate extends Transform {
    private double theta;
    private double cos, sin;


    public Rotate(double theta){
        this(theta,null);
    }
    public Rotate(double theta,GameObject gameObject) {

        super(gameObject);
        setAngle(theta);
    }

    public void setAngle(double theta){

        this.theta = theta;
        if(Math.abs(theta%1)<1e-5)
            switch (Math.floorMod(Math.round(theta),360)){
                case 0:
                    cos = 1;
                    sin = 0;
                    break;
                case 90:
                    cos = 0;
                    sin = 1;
                    break;
                case 180:
                    cos=-1;
                    sin=0;
                    break;
                case 270:
                    cos = 0;
                    sin= -1;
                    break;
                    default:
                        double rads = Math.toRadians(theta);
                        cos = Math.cos(rads);
                        sin = Math.sin(rads);

            }
    }

    @Override
    protected Vector2D applyLocal(double x, double y, double z) {
        return Vector2D.valueOf(cos*x+sin*y,-sin*x+cos*y);
    }


    @Override
    public double m11() {
        return cos;
    }

    @Override
    public double m12() {
        return sin;
    }

    @Override
    public double m21() {
        return -sin;
    }

    @Override
    public double m22() {
        return cos;
    }
}
