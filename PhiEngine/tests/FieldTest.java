package PhiEngine.tests;

import PhiEngine.util.DoubleField;
import PhiEngine.util.Field;
import PhiEngine.util.Fields;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;


/**
 * Created by Alex on 07/12/2017.
 */
public class FieldTest {

    @Test
    public void testField(){
        Field<String> field = Fields.field("Hello",Fields.get(),Fields.get());
        assertEquals("Hello",field.get());

    }

    @Test
    public void testDoubleField(){
        DoubleField d = new DoubleField(9.8D);
        assertEquals(9.8D,d.get());
    }
}
