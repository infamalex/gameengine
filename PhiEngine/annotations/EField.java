package PhiEngine.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Alex on 30/10/2017.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface EField {
    String[] tags() default {};
}
