package seedu.address.logic;

public class PlayMode {
    private boolean isPlayMode = false;

    public PlayMode() {
    }

    public void turnOff() {
        isPlayMode = false;
    }

    public void turnOn() {
        isPlayMode = true;
    }

    public boolean isPlayMode() {
        return isPlayMode;
    }
}
