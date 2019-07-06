package PhiEngine.util;


import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.io.Serializable;

/**
 * Created by Alex on 06/11/2017.
 */
public abstract class AbstractField<T> implements Observable,ObservableValue<T>,Serializable{
    private transient OnDemandHolder<InvalidationListener> invalidationListeners;
    private transient OnDemandHolder<ChangeListener<? super T>> changeListeners;


    @Override
    public void addListener(InvalidationListener listener) {
        invalidationListeners = OnDemandHolder.add(invalidationListeners,listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        OnDemandHolder.remove(invalidationListeners,listener);
    }

    @Override
    public void addListener(ChangeListener<? super T> listener) {
        changeListeners = OnDemandHolder.add(changeListeners, listener);
    }

    @Override
    public void removeListener(ChangeListener<? super T> listener) {
        OnDemandHolder.remove(changeListeners,listener);
    }

    public void removeAllChangeListeners(){
        changeListeners = null;
    }public void removeAllInvalidationListeners(){
        invalidationListeners = null;
    }

    protected synchronized void invalidate(T oldValue, T newValue){
        OnDemandHolder.stream(invalidationListeners).forEach(i->i.invalidated(this));
        OnDemandHolder.stream(changeListeners).forEach(c->c.changed(this,oldValue,newValue));
    }
}
