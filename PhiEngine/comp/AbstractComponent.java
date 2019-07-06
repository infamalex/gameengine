package PhiEngine.comp;

import java.util.Optional;

/**
 * Created by Alex on 06/11/2017.
 */
public class AbstractComponent implements Component{
    private final GameObject gameObject;

    public AbstractComponent(GameObject gameObject) {
        this.gameObject = gameObject;
        addToGameObject();
    }

    public <T extends Component> Optional<T> getComponent(Class<? extends T> type){
        return gameObject.getComponent(type);
    }

    public final GameObject getGameObject() {
        return gameObject;
    }

}
