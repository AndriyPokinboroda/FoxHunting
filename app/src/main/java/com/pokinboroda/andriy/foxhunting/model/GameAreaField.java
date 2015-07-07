package com.pokinboroda.andriy.foxhunting.model;

import java.io.Serializable;

import pokinboroda.andriy.com.foxhunting.R;

/**
 * Class represent field on game area.
 *
 * @author Pokiboroda Andriy
 * @version 0.1
 */
public class GameAreaField implements Serializable {

    /** The constant ICON_FOX. */
    public static final int ICON_FOX = R.drawable.icon_fox;

    /** The constant ICON_TREE. */
    public static final int ICON_TREE = R.drawable.icon_tree;

    /** The constant ICON_BLANK. */
    public static final int ICON_BLANK = R.color.game_area_background;

    /** The constant FIELD_FOX. */
    public static final int FIELD_FOX = -1;

    /** The constant FIELD_ZERO. */
    public static final int FIELD_ZERO = 0;

    /** Field can be in two states HIDDEN and SHOWED. */
    public enum States {

        /**  Field closed, any information doesn't showed. */
        HIDDEN,

        /** Field showed, display fox or some value. */
        SHOWED
    }

    /** field value, can be FIELD_FOX. */
    private int value;

    /** text for view representation. */
    private String text;

    /** image resource for view representation. */
    private int imageResource;
    /** store current field state. */
    private States currentState;

    /**
     * Instantiates a new Game area field.
     *
     * @param mValue the field value
     */
    public GameAreaField(final int mValue) {
        this(mValue, States.HIDDEN);
    }

    /**
     * Instantiates a new Game area field.
     *
     * @param mValue the field value
     * @param itemState the field state
     */
    public GameAreaField(final int mValue, final States itemState) {
        this.value = mValue;

        setState(itemState);
    }

    /**
     * Sets state.
     *
     * @param newState the new state
     */
    public final void setState(final States newState) {
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

    /**
     * Gets state.
     *
     * @return the state
     */
    public final States getState() {
        return currentState;
    }

    /**
     * Sets value.
     *
     * @param newValue the new value
     */
    public final void setValue(final int newValue) {
        this.value = newValue;

        if (this.currentState == States.SHOWED) {
            /* Refresh text and imageResource variables */
            setState(States.SHOWED);
        }
    }

    /**
     * Gets text.
     *
     * @return the text
     */
    public final String getText() {
        return text;
    }

    /**
     * Gets image.
     *
     * @return the image
     */
    public final int getImage() {
        return imageResource;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public final int getValue() {
        return value;
    }
}
