package PhiEngine.collider;

import PhiEngine.comp.GameObject;
import PhiEngine.event.CollisionEvent;
import PhiEngine.geom.Vector2D;
import PhiEngine.time.Time;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Alex on 24/02/2018.
 */
public class FlexCollider extends Collider {
    public FlexCollider(GameObject g) {
        super(g);
        Point a = new Point(Vector2D.valueOf(0, 0));
        Point b = new Point(Vector2D.valueOf(20, 0));
        Point c = new Point(Vector2D.valueOf(10, 20));
        Node root = new Node(a,
                new SpringConnecter(new Node(b), 40, .5),
                new SpringConnecter(new Node(c,
                        new SpringConnecter(new Node(b), 30, 1)
                ), 12, 1)
        );
        n =root;
        points=List.of(a,b,c);
    }
    private final List<Point> points;
    private final Node n;

    private FlexCollider(GameObject gameObject, Node n) {
        super(gameObject);
        this.n = n;
points = null;
    }

    @Override
    public Optional<CollisionEvent> collide(Collider other) {
        if(other instanceof CircleCollider) return Optional.empty();
        Set<Point> contacts = n.contained(other,new HashSet<>());
        for(Point p : contacts)
            p.force = p.force.add(Vector2D.valueOf(0,100/ Time.fixedDelta()));
        if(points.size() == 0) return Optional.empty();
        List<Vector2D> v = contacts.stream().map(Point::value).collect(Collectors.toList());
        CollisionEvent e = new CollisionEvent(this.getGameObject(),other.getGameObject(),v);
        return Optional.of(e);
    }

    @Override
    public boolean contains(Vector2D v) {
        return false;
    }

    @Override
    public void drawCollider(Graphics2D g) {
        n.draw(g);
    }

    @Override
    public void fixedUpdate() {
        n.setForce(Vector2D.valueOf(0, 0));
        n.FixedUpdate(Vector2D.valueOf(0, 0));
        points.forEach(Point::applyForce);
    }

    class Point {
        private Vector2D value;
        Vector2D force;

        Point(Vector2D value) {
            this.value = value;
        }

        Vector2D value() {
            return transform().apply(value);
        }

        void applyForce(){
            value = value.add(force,Time.fixedDelta());
        }
    }

    class Node {
        final Point pos;
        private final Connector[] connections;

        public Node(Point pos, Connector... connections) {

            this.pos = pos;
            this.connections = connections;
            for (Connector c : connections)
                c.setParent(this);
        }

        void FixedUpdate(Vector2D parentForce) {
            for (Connector c : connections) {
                parentForce = parentForce.add(c.fixedUpdate());
            }
            pos.force = pos.force.add(parentForce);
        }

        void draw(Graphics2D g) {
            for (Connector c : connections)
                c.draw(g);
        }

        void setForce(Vector2D v) {
            pos.force = v;
            for (Connector c : connections)
                c.pos.setForce(v);
        }

        Set<Point> contained(Collider c, Set<Point> containedPoints) {
            if (c.contains(pos.value())) containedPoints.add(pos);
            for (Connector con : connections)
                con.pos.contained(c, containedPoints);
            return containedPoints;
        }
    }

    abstract class Connector {

        protected Node parent;
        Node pos;

        Connector(Node pos) {

            this.pos = pos;
        }

        void setParent(Node parent) {
            this.parent = parent;
        }

        abstract Vector2D fixedUpdate();

        abstract void draw(Graphics2D g);
    }

    class SpringConnecter extends Connector {

        private final double length;
        private double springConstant;

        SpringConnecter(Node pos, double length, double springConstant) {

            super(pos);
            this.length = length;
            this.springConstant = springConstant;
        }

        @Override
        Vector2D fixedUpdate() {
            Vector2D toParent = parent.pos.value().sub(pos.pos.value());
            double len = toParent.mag();
            double x = 1 - length/len ; //TODO check if almost 0
            pos.FixedUpdate(toParent.scaled(x * springConstant));
            return toParent.scaled(-x * springConstant);


        }

        @Override
        void draw(Graphics2D g) {
            Vector2D start = parent.pos.value();
            Vector2D end = pos.pos.value();
            Vector2D toParent = start.sub(end);
            double len = toParent.mag();
            double x = 1 - length /  len;
            Color c = Color.getHSBColor(x>0?10f/36:0, 1, (float) Math.abs(x));
            g.setColor(c);
            g.drawLine((int) start.x, (int) start.y, (int) end.x, (int) end.y);
            pos.draw(g);
        }
    }
}