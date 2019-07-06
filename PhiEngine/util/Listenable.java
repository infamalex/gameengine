package PhiEngine.util;

/**
 * Created by Alex on 03/05/2018.
 */
public interface Listenable<T> {
    void addListener(T listener);
    void removeListener(T listener);
}
