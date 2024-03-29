package PhiEngine.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Alex on 14/12/2017.
 */


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface FileTypes {
    String[] extensions();
    boolean caseSensitive() default false;
}
