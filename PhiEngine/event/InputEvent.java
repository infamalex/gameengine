package PhiEngine.event;

/**
 * Created by Alex on 03/05/2018.
 */
public class InputEvent extends GameEvent{
    private double inputValue;
    private int inputCode;

    public double getInputValue() {
        return inputValue;
    }

    public void setInputValue(double inputValue) {
        this.inputValue = inputValue;
    }

    public int getInputCode() {
        return inputCode;
    }

    public void setInputCode(int inputCode) {
        this.inputCode = inputCode;
    }
}
