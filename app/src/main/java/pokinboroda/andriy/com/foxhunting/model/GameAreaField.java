package pokinboroda.andriy.com.foxhunting.model;

import pokinboroda.andriy.com.foxhunting.R;

/**
 * Created by andriy on 05.07.15.
 */
public class GameAreaField {
    public static final int ICON_FOX = R.drawable.icon_fox;
    public static final int ICON_TREE = R.drawable.icon_tree;
    public static final int ICON_BLANK = R.color.game_area_background;
    public static final int FIELD_FOX = -1;

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
        this.currentState = itemState;

        changeState(itemState);
    }

    public void changeState(States itemState) {

        if (itemState == States.HIDDEN) {
            text = "";
            imageResource = ICON_TREE;
        } else {
            if (value == FIELD_FOX) {
                text = "";
                imageResource = ICON_FOX;
            } else {
                text = value + "";
                imageResource = ICON_BLANK;
            }
        }
    }

    public void setValue(int newValue) {
        this.value = newValue;

        if (currentState == States.SHOWED) {
            /* Refresh text and imageResource variables */
            changeState(States.SHOWED);
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
