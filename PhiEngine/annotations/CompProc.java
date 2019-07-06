package PhiEngine.annotations;

import javafx.beans.property.Property;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.TypeElement;
import java.util.Set;

/**
 * Created by Alex on 30/10/2017.
 */
public class CompProc {

    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for(TypeElement ann : annotations){

        }
        return false;
    }
}
