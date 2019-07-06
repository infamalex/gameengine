package PhiEngine.input;

import PhiEngine.event.InputEvent;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class InputAdapter implements KeyListener, GameInput{
    private List<InputListener> listeners = new ArrayList<>();

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        input(e,1);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        input(e,0);
    }

    private void input(KeyEvent e,double value){
        InputEvent ie = new InputEvent();
        ie.setSource(this);
        ie.setInputValue(value);
        ie.setInputCode(e.getKeyCode());
        listeners.forEach(l->l.onInput(ie));
    }

    @Override
    public void addListener(InputListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(InputListener listener) {
        listeners.remove(listener);
    }

    @Override
    public int getDeviceType() {
        return 0;
    }
}
