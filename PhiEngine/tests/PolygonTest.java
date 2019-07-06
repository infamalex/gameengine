package PhiEngine.tests;

import PhiEngine.geom.Polygon2;
import PhiEngine.geom.Vector2D;
import org.junit.BeforeClass;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Alex on 25/12/2017.
 */
public class PolygonTest {
    static Polygon2 triangle = Polygon2.create(0,0, 10,0,5,10);

@Test
    public void testClosest(){
        assertEquals(Vector2D.valueOf(5,10),triangle.closest(Vector2D.valueOf(5,20)));
    }
}
