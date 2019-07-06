package PhiEngine.input;

import PhiEngine.event.InputEvent;

import java.awt.event.KeyListener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

/**
 * Created by Alex on 07/12/2017.
 */
public class Input {
    private static Map<String,Axis> axiis = new HashMap<>();

    public static double getAxis(String axisName){
        Axis a = axiis.get(axisName);
        if (a == null)
            return Double.NaN;
        else
            return a.value();
    }

    public static void addInputDevice(GameInput input){
        input.addListener(Input::acceptInput);
    }

    private static void acceptInput(InputEvent e){
        axiis.values().stream().filter(a->a.update(e.getInputCode(),e.getInputValue())).findFirst();
    }
    public static void createAxis(String name, int inputCode,int invertedInputCode){
        axiis.put(name,new Axis(inputCode,invertedInputCode));
    }


}

class Axis{
    int inputCode;
    double inputCodeValue;
    int invertedInputCode;
    double invertedInputCodeValue;

    Axis(int inputCode,int invertedInputCode){

        this.inputCode = inputCode;
        this.invertedInputCode = invertedInputCode;
    }
    boolean update(int code, double value){
        if(code == inputCode)
            inputCodeValue = value;
        else if(invertedInputCode == code)
            invertedInputCodeValue = -value;
        else return false;
        return true;
    }

    double value(){
        return inputCodeValue + invertedInputCodeValue;
    }
}