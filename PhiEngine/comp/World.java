package PhiEngine.comp;

import PhiEngine.annotations.EField;
import PhiEngine.collider.Collider;
import PhiEngine.comp.GameObject;
import PhiEngine.geom.transform.AffineTransform;
import PhiEngine.geom.transform.Transform;
import PhiEngine.util.DoubleField;
import PhiEngine.util.Field;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.io.Serializable;

import static PhiEngine.util.Fields.*;

/**
 * Created by Alex on 08/12/2017.
 */
public class World implements Serializable{
    @EField public final DoubleField gravity = new DoubleField(19);
    @EField public final Field<AffineTransform> worldTransform = field(AffineTransform.WT, get(), get());


    public final ObservableList<GameObject> gameObjects;
    public final FilteredList<GameObject> collidable;

    public World(){
        gameObjects = FXCollections.observableArrayList(GameObject::getFields);
        gameObjects.addListener(this::gameObjectsChanged);
        collidable = gameObjects.filtered(go->go.getComponent(Collider.class).isPresent());
    }

    private void gameObjectsChanged(ListChangeListener.Change<? extends GameObject> change){
        while(change.next()){
            if(change.wasAdded())
                change.getAddedSubList().forEach(go->go.setWorld(this));
        }
    }






}