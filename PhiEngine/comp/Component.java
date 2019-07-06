package PhiEngine.comp;

import java.io.Serializable;

/**
 * Created by Alex on 06/11/2017.
 * @author Alex
 */
public interface Component extends Serializable, Cloneable{


    default void fixedUpdate(){}

    default void addToGameObject(){
        getGameObject().addComponent(this);
    }

    GameObject getGameObject();
}
