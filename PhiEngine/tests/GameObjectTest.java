package PhiEngine.tests;

import PhiEngine.collider.CircleCollider;
import PhiEngine.collider.Collider;
import PhiEngine.comp.Game;
import PhiEngine.comp.GameObject;
import PhiEngine.geom.transform.Transform;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by Alex on 13/12/2017.
 */
public class GameObjectTest {
    @Test
    public void getComponent(){
        GameObject g = new GameObject();
        assertFalse(g.getComponent(Collider.class).isPresent());
        g.addComponent(new CircleCollider(g,5));
        assertTrue(g.getComponent(Collider.class).isPresent());
        assertTrue(g.getComponent(Transform.class).isPresent());
    }
}
