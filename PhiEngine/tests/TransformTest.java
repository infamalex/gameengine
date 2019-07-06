package PhiEngine.tests;

import PhiEngine.comp.Game;
import PhiEngine.comp.World;
import PhiEngine.geom.Vector2D;
import PhiEngine.geom.transform.AffineTransform;
import PhiEngine.geom.transform.Rotate;
import PhiEngine.geom.transform.Transform;
import PhiEngine.geom.transform.Translate;
import org.junit.BeforeClass;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Alex on 13/12/2017.
 */
public class TransformTest {

    @BeforeClass
    public static void init(){
        Game.activeWorld = new World();
        Game.activeWorld.worldTransform.set(new Translate(0,0).asAffine());
    }
@Test
    public void testWorldTransform(){
        assertEquals(Game.activeWorld.worldTransform.get().m22(),-1D);
        assertEquals(-1D,Game.activeWorld.worldTransform.get().apply(1,1).y);
//        assertEquals(1D,Game.activeWorld.worldTransform.get().applyLocal(1,1).y);
    }
    @Test
    public void testPosition(){
        Transform t = new Translate(5D,4D);
        assertEquals(5D,t.getPosition().x );
        assertEquals(-4D,t.apply(0,0).y );
    }
    @Test
    public void testDefault(){
        Transform t = new Translate(0,0);
        assertEquals(1D,t.m11() );
        assertEquals(0D,t.m12());
        assertEquals(0D,t.m13());
        assertEquals(0D,t.m21());
        assertEquals(1D,t.m22() );
        assertEquals(0D,t.m23() );

        assertEquals(-1D,t.apply(1,1).y);
        assertEquals(1D,t.applyLocal(1,1).y);
    }

    @Test
    public void testAffine(){
        AffineTransform t = new Rotate(90).asAffine();
        assertEquals(Vector2D.valueOf(0,1),t.apply(Vector2D.valueOf(1,0)));
    }
}
