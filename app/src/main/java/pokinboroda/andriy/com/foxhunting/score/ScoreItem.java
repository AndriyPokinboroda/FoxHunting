package pokinboroda.andriy.com.foxhunting.score;

import java.io.Serializable;

public class ScoreItem implements Comparable<ScoreItem>, Serializable {

    private int score;
    private int level;
    private String playerName;

    public ScoreItem(int score, int level, String playerName) {
        this.score = score;
        this.level = level;
        this.playerName = playerName;
    }

    public int getScore() {
        return score;
    }

    public int getLevel() {
        return level;
    }

    public String getPlayerName() {
        return playerName;
    }

    @Override
    public int compareTo(ScoreItem another) {
        if (score > another.score) return -1;
        else if (score < another.score) return 1;
        else return 0;
    }
}