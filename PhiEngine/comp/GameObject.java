package PhiEngine.comp;

import PhiEngine.annotations.EField;
import PhiEngine.geom.Affine;
import PhiEngine.geom.transform.AffineTransform;
import PhiEngine.geom.transform.Rotate;
import PhiEngine.geom.transform.Transform;
import PhiEngine.util.EFieldHelper;
import PhiEngine.util.Field;
import PhiEngine.util.Fields;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Alex on 06/11/2017.
 */
public class GameObject implements Cloneable,Serializable{
    private static Logger GOBJECT_LOGGER = Logger.getLogger(GameObject.class.getName());
    private List<Component> additionalComponents, components;
    private AffineTransform transform =  new Rotate(0).asAffine();
    public Field<AffineTransform> transformField = Fields.field(this,(Function<GameObject,AffineTransform>&Serializable) g->g.transform,(BiConsumer<GameObject,AffineTransform>&Serializable) (g, n)->g.transform =n);
    private transient World world;

    public Observable[] getFields() {
        return EFieldHelper.getFields(this);
    }


    public GameObject(){
        List<Field> fields = new ArrayList<>();
        components = List.of();
    }

    public void addComponent(Component c){
        if(additionalComponents == null)
            additionalComponents = new ArrayList<>();
        additionalComponents.add(c);
    }

    private Stream<Component> components(){
        return Stream.concat(Stream.of((Component)transform), additionalComponents == null?Stream.empty():additionalComponents.stream());
    }

    public <T extends Component> Optional<T> getComponent(Class<? extends T> type){
        return (Optional<T>) components().filter(type::isInstance).findFirst();
    }

    public <T extends Component> List<T> getComponents(Class<? extends T> type){
        return (List<T>) components().filter(type::isInstance).collect(Collectors.toList());
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public void callFixedUpdate(){
        additionalComponents.forEach(Component::fixedUpdate);
    }

    void setWorld(World world){
        this.world = world;
    }

    public World getWorld() {
        return world;
    }
}
