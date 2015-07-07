package com.pokinboroda.andriy.foxhunting.score;

import java.io.Serializable;

/**
 * Score item represent single record in score list.
 *
 * @author Pokinboroda Andriy
 * @version 0.1
 *
 * @see ScoreList
 */
public class ScoreItem implements Comparable<ScoreItem>, Serializable {

    /** Record score. */
    private int score;

    /** Record level. */
    private int level;

    /** Name of player who set the record. */
    private String playerName;

    /**
     * Instantiates a new Score item.
     *
     * @param mScore the record score
     * @param mLevel the record level
     * @param mPlayerName the player name
     */
    public ScoreItem(final int mScore, final int mLevel,
                     final String mPlayerName) {
        this.score = mScore;
        this.level = mLevel;
        this.playerName = mPlayerName;
    }

    /**
     * Gets score.
     *
     * @return the score
     */
    public final int getScore() {
        return score;
    }

    /**
     * Gets level.
     *
     * @return the level
     */
    public final int getLevel() {
        return level;
    }

    /**
     * Gets player name.
     *
     * @return the player name
     */
    public final String getPlayerName() {
        return playerName;
    }

    @Override
    public final int compareTo(final ScoreItem anotherScoreItem) {
        if (score > anotherScoreItem.score) {
            return -1;
        } else if (score < anotherScoreItem.score) {
            return 1;
        } else {
            return 0;
        }
    }
}
