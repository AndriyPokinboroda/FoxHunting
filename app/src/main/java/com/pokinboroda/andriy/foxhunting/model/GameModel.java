package com.pokinboroda.andriy.foxhunting.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represent fox hunting game model,
 * contain default value for statistic fields.
 * Also could be serialized for saving game state.
 *
 * @author Pokinboroda Andriy
 * @version 0.1
 * @see com.pokinboroda.andriy.foxhunting.view;
 * @see com.pokinboroda.andriy.foxhunting.controller;
 */
public class GameModel implements Serializable {

    /** The default value for field level. */
    public static final int LEVEL_DEFAULT = 1;

    /** The default value for field foxes. */
    public static final int FOXES_DEFAULT = 3;

    /** The default value for field huntedFoxes. */
    public static final int HUNTED_FOXES_DEFAULT = 0;

    /** The default value for field power. */
    public static final int POWER_DEFAULT = 10;

    /** The default value for field score. */
    public static final int SCORE_DEFAULT = 0;

    /** The constant SCORE_BONUS. */
    public static final int SCORE_BONUS = 100;

    /** Use for determine length of on row or column of game area.*/
    public static final int AREA_DIMENSION = 10;

    /** Current game level. */
    public int level;

    /** Count of Foxes what should be found. */
    public int foxes;

    /** The count of foxes what already found(Hunted). */
    public int huntedFoxes;

    /** Current Power. */
    public int power;

    /** Current score. */
    public int score;

    /** Current state of game area(fields). */
    public GameAreaField[][] fields;
}
