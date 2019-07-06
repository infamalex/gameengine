package PhiEngine.input;

import PhiEngine.util.Listenable;

public interface GameInput extends Listenable<InputListener>{
    int DEVCODE =0;

    int getDeviceType();


}
