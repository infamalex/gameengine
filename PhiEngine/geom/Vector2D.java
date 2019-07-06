package PhiEngine.geom;


import java.io.Serializable;

/**
 * Created by Alexander on 10/02/17.
 */
public final class Vector2D implements Comparable<Vector2D>, Serializable{

    private final static Vector2D RIGHT = new Vector2D(1,0),LEFT = new Vector2D(-1,0),
    UP = new Vector2D(0,1), DOWN = new Vector2D(0,-1);


    public final double x;
    public final double y;

    private Vector2D(double x, double y){

        this.x = x;
        this.y = y;
    }

    //TODO possibly add caching method
    public static Vector2D valueOf(double x, double y){
        return new Vector2D(x,y);
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public Vector2D add(Vector2D other){
        return new Vector2D(x + other.x(), y + other.y);
    }
    public Vector2D add(Vector2D other, double scale){
        return new Vector2D(x + scale*other.x(), y + scale*other.y);
    }

    public Vector2D sub(Vector2D other){
        return new Vector2D(x - other.x(), y - other.y);
    }

    public double dot(Vector2D other){
        return x*other.x + y*other.y;
    }

    public double mag(){
        return Math.sqrt(x*x+y*y);
    }
    public double magSqu(){
        return x*x+y*y;
    }

    public double angle(){
        return Math.atan2(y,x);
    }
    public double angle(Vector2D other){
        double total = angle()-other.angle();
        return GeoMath.floorMod(total +3 * Math.PI, 2*Math.PI) - Math.PI;
    }

    public Vector2D rotate(double theta){
        double quart = 2*theta/(Math.PI);
        long roundQuart = Math.round(quart);
        if(GeoMath.approxEquals(quart, roundQuart))
            switch (Math.floorMod(roundQuart,4)){
                case 0: return this;
                case 1: return new Vector2D(y,-x);
                case 2: return new Vector2D(-x,-y);
                case 3: return new Vector2D(-y,x);
            }
        double newX = x*Math.cos(theta) + y*Math.sin(theta);
        double newY = y*Math.cos(theta) - x*Math.sin(theta);
        return  new Vector2D(newX, newY);
    }

    public Vector2D normalised(){
        return scaled(1/mag());
    }
    public Vector2D scaled(double scale){
        return new Vector2D(scale*x,scale*y);
    }

    @Override
    public int compareTo(Vector2D o) {
        if(GeoMath.approxEquals(x,o.x) && GeoMath.approxEquals(y,o.y)) return 0;
        else if(magSqu() > o.magSqu()) return 1;
        else return -1;
    }

    public static Vector2D Lerp(Vector2D start, Vector2D end, double delta){
        return Ray2D.forPoints(start,end).forT(delta);
    }

    @Override
    public String toString() {
        return String.format("(%5.5f,%5.5f)",x,y);
    }

    @Override
    public boolean equals(Object obj) {
        if(super.equals(obj))return true;

        if(! (obj instanceof Vector2D))
            return false;
        Vector2D other = (Vector2D)obj;
        return GeoMath.approxEquals(x,other.x) && GeoMath.approxEquals(y,other.y);
    }

    public boolean isFinite(){
        return Double.isFinite(x) && Double.isFinite(y);
    }
}
