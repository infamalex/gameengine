package PhiEngine;

import PhiEngine.collider.CircleCollider;
import PhiEngine.collider.Collider;
import PhiEngine.collider.FlexCollider;
import PhiEngine.collider.PolygonCollider;
import PhiEngine.comp.*;
import PhiEngine.event.CollisionEvent;
import PhiEngine.event.InputEvent;
import PhiEngine.geom.Polygon2;
import PhiEngine.geom.Vector2D;
import PhiEngine.geom.transform.AffineTransform;
import PhiEngine.geom.transform.Rotate;
import PhiEngine.geom.transform.Transform;
import PhiEngine.geom.transform.Translate;
import PhiEngine.input.Input;
import PhiEngine.input.InputAdapter;
import PhiEngine.time.Time;
import PhiEngine.util.Field;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.PrimitiveIterator;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

/**
 * Created by Alex on 10/12/2017.
 */
    public class Demo extends JPanel{
        public static final int WIDTH = 1000,HEIGHT =1000;
        public static boolean CLEAR = true;
    public static BufferedImage img = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_ARGB);
    public static void main(String[] args) {
        World world = new World();
        GameObject ball = new GameObject();
        new CircleCollider(ball, 20);
        new RigidBody(ball,1);
        ball.transformField.get().concat(new Translate(100,410));

        GameObject ball2 = new GameObject();
        new FlexCollider(ball2);
        //new FlexCollider(ball2);
        RigidBody r =new RigidBody(ball2,10);
        r.addVel(Vector2D.valueOf(0,90));
        ball2.transformField.get().concat(new Translate(120,310));

        GameObject floor = box();
        floor.transformField.set(new Translate(150,40).asAffine());
        GameObject pent = pent();
        pent.transformField.set(new Translate(250,80).asAffine());
        GameObject wall = box();
        wall.transformField.set(new Rotate(-30).concat(new Translate(0,100)).asAffine());
        world.gameObjects.add(floor);
        world.gameObjects.add(wall);
        world.gameObjects.add(ball);
        world.gameObjects.add(pent);
        world.gameObjects.add(ball2);
        Game.activeWorld = world;
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Demo demo = new Demo();

        InputAdapter adapter = new InputAdapter();
        demo.addKeyListener(adapter);
        frame.addKeyListener(adapter);
        Input.addInputDevice(adapter);
        Input.createAxis("y", KeyEvent.VK_W,KeyEvent.VK_S);
        Input.createAxis("x", KeyEvent.VK_A,KeyEvent.VK_D);

        new AbstractComponent(ball2){
            RigidBody rb = getComponent(RigidBody.class).get();
            @Override
            public void fixedUpdate() {
                rb.addVel(Vector2D.valueOf(-Input.getAxis("x"),Input.getAxis("y")));
            }
        };

        JMenuBar bar = new JMenuBar();
        JMenu action = new JMenu("action");

        JMenuItem play = new JMenuItem("play");
        play.addActionListener(e->Time.setTimeScale(1));
        action.add(play);

        JMenuItem pause = new JMenuItem("pause");
        pause.addActionListener(e->Time.setTimeScale(0));
        action.add(pause);


        JMenuItem half = new JMenuItem("half-speed");
        half.addActionListener(e->Time.setTimeScale(0.5));
        action.add(half);

        JMenuItem rev = new JMenuItem("reverse");
        rev.addActionListener(e->Time.setTimeScale(-1));
        action.add(rev);


        JCheckBoxMenuItem clear = new JCheckBoxMenuItem("clear on redraw",true);
        clear.addActionListener(e->CLEAR =((JCheckBoxMenuItem)e.getSource()).getState() );
        action.add(clear);
        bar.add(action);
        frame.setJMenuBar(bar);

        frame.setContentPane(demo);
        frame.setBounds(100,100,500,500);
        MouseControl l = new MouseControl();
        frame.addMouseListener(l);
        demo.addMouseListener(l);
        demo.addMouseMotionListener(l);
        frame.setVisible(true);
        new Thread(demo::update).start();
        new Thread(()->painter(frame)).start();
    }

    public static GameObject box(){
        GameObject g = new GameObject();
        //g.addComponent(new PolygonCollider(g,Polygon2.create(0,0,-10,100,100,110,110,0)));
        g.addComponent(new PolygonCollider(g,Polygon2.create(0,0,35,100,165,100,200,0)));
        return g;
    }
    public static GameObject pent(){
        GameObject g = new GameObject();
        PrimitiveIterator.OfDouble it = IntStream.range(0, 5).mapToDouble(i -> (2 * Math.PI * i) / 5D).flatMap(x -> DoubleStream.of(Math.sin(x), Math.cos(x))).
                map(x->x*45).
                iterator();
        g.addComponent(new PolygonCollider(g,Polygon2.create(it.next(),it.next(),it.next(),it.next(),it.next(),it.next(),it.next(),it.next(),it.next(),it.next())));
        return g;
    }

    public void update(){
        for(;;){
            Game.activeWorld.gameObjects.forEach(GameObject::callFixedUpdate);


            List<GameObject> objects = Game.activeWorld.collidable;
            for (int i = 0; i < objects.size(); i++) {
                GameObject g = objects.get(i);
                if(g.getComponent(RigidBody.class).isPresent()){
                    Collider c = g.getComponent(Collider.class).get();
                    objects.stream().filter(go->go!=g).map(go->go.getComponent(Collider.class).get().collide(c)).
                            filter(Optional::isPresent).map(Optional::get).peek(System.out::println).forEach(Demo::handle);


                }
            }
            try{
                Thread.sleep((int)(1000/240D));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private static void painter(JFrame f){
        for(;;){
            f.repaint();
        try{
            Thread.sleep((int)(1000*Time.delta()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }}
    }

    private static void handle(CollisionEvent e){
        e.o1.getComponent(Collider.class).ifPresent(c->whoCares(c,e.contact.get(0)));
    }
    private static void whoCares(Collider c, Vector2D contact){
        Vector2D direction = c.transform().getPosition().sub(contact).normalised();// direction of contact normal vector
        //Vector2D direct = Vector2D.valueOf(direct0.x,-direct0.y); //test
        c.getComponent(RigidBody.class).ifPresent(co->{
            double vel = -2*co.getVel().dot(direction);
            //System.out.printf("%f pos:%s con:%s localpos:%s%n",vel,c.transform().getPosition(),contact,c.transform().getLocalPosition());
            //throw new RuntimeException();
            co.addVel(direction.scaled(vel));
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintImage(img.createGraphics());
        g.drawImage(img,0,0,null);
    }

    protected void paintImage(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.WHITE);
        if(CLEAR)
        g.fillRect(0,0, WIDTH,HEIGHT);
        g.transform(new java.awt.geom.AffineTransform(1,0,0,-1,0,0));
        Game.activeWorld.gameObjects.forEach(go->go.getComponent(Collider.class).ifPresent(c->c.drawCollider(g)));

    }
    static void output(){
        try(OutputStream out = Files.newOutputStream(Paths.get("demo.ser"))) {
            ObjectOutputStream oos = new ObjectOutputStream(out);
            for(GameObject g : Game.activeWorld.gameObjects)
                oos.writeObject(g);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class MouseControl extends MouseAdapter{
        Field<AffineTransform> selected;
        Vector2D point;

        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
//            Transform inverse = Game.activeWorld.worldTransform.get().inverse();
            Vector2D pos = Vector2D.valueOf(e.getX(),-e.getY());
            Optional<GameObject> selected = Game.activeWorld.gameObjects.stream().
                    filter(g -> g.getComponent(Collider.class).
                            map(c -> c.contains(pos)).orElse(false)).findFirst();
            switch (e.getButton()){
                case MouseEvent.BUTTON1:
                    this.selected = selected.map(o->o.transformField).orElse(Game.activeWorld.worldTransform);
                    break;
                    case MouseEvent.BUTTON3:
                        Vector2D opos = Game.activeWorld.worldTransform.get().apply(pos);
                        PhiEngine.geom.transform.AffineTransform tr = Transform.rotateAround(pos,30).asAffine();
                        selected.map(s->s.transformField).ifPresent(t->t.set(tr.concat(t.get())));
                        break;
            }
            point = pos;
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            selected = null;
        }
        @Override
        public void mouseDragged(MouseEvent e) {
//            Transform inverse = Game.activeWorld.worldTransform.get().inverse();
            Vector2D newPoint = Vector2D.valueOf(e.getX(),-e.getY());
//            Graphics2D g = img.createGraphics();
//            g.transform(new AffineTransform(1,0,0,-1,0,0));
//            g.drawOval((int)newPoint.x()-5,(int)newPoint.y-5,10,10);

            if(selected == null) return;
            Transform t = new Translate(newPoint.sub(point));
            selected.set(t.concat(selected.get()).asAffine());
            point = newPoint;
        }

    }
}
