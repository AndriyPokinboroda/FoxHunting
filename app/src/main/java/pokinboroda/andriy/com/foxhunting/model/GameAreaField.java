package pokinboroda.andriy.com.foxhunting.model;

import java.io.Serializable;

import pokinboroda.andriy.com.foxhunting.R;

/**
 * Created by andriy on 05.07.15.
 */
public class GameAreaField implements Serializable {
    public static final int ICON_FOX = R.drawable.icon_fox;
    public static final int ICON_TREE = R.drawable.icon_tree;
    public static final int ICON_BLANK = R.color.game_area_background;
    public static final int FIELD_FOX = -1;
    public static final int FIELD_ZERO = 0;

    public enum States {
        HIDDEN,
        SHOWED
    }

    private int value;
    private String text;
    private int imageResource;
    private States currentState;

    public GameAreaField(int value) {
        this(value, States.HIDDEN);
    }

    public GameAreaField(int value, States itemState) {
        this.value = value;

        setState(itemState);
    }

    public void setState(States newState) {
        this.currentState = newState;

        if (newState == States.HIDDEN) {
            text = "";
            this.imageResource = ICON_TREE;
        } else {
            if (value == FIELD_FOX) {
                this.text = "";
                this.imageResource = ICON_FOX;
            } else {
                this.text = value + "";
                this.imageResource = ICON_BLANK;
            }
        }
    }

    public States getState() {
        return currentState;
    }

    public void setValue(int newValue) {
        this.value = newValue;

        if (this.currentState == States.SHOWED) {
            /* Refresh text and imageResource variables */
            setState(States.SHOWED);
        }
    }

    public String getText() {
        return text;
    }

    public int getImage() {
        return imageResource;
    }

    public int getValue() {
        return value;
    }
}
