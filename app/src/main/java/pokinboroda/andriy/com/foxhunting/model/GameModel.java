package pokinboroda.andriy.com.foxhunting.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andriy on 05.07.15.
 */
public class GameModel {
    public static final int LEVL_DEFAULT = 1;
    public static final int FOXES_DEFAULT = 3;
    public static final int HUNTED_FOXES_DEFAULT = 0;
    public static final int POWER_DEFAULT = 10;
    public static final int SCORE_DEFAULT = 0;

    public static final int AREA_DIMENSION = 10;

    private int level;
    private int foxes;
    private int huntedFoxes;
    private int power;
    private int score;

    private GameAreaField[][] gameAreaFields;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getFoxes() {
        return foxes;
    }

    public void setFoxes(int foxes) {
        this.foxes = foxes;
    }

    public int getHuntedFoxes() {
        return huntedFoxes;
    }

    public void setHuntedFoxes(int huntedFoxes) {
        this.huntedFoxes = huntedFoxes;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<GameAreaField> getGameAreaFields() {
        List<GameAreaField> fields = new ArrayList<>();

        for (int i = 0; i < AREA_DIMENSION; i++) {
            for (int j = 0; j < AREA_DIMENSION; i++) {
                fields.add(gameAreaFields[i][j]);
            }
        }

        return fields;
    }

    public void setGameAreaFields(GameAreaField[][] gameAreaFields) {
        this.gameAreaFields = gameAreaFields;
    }
}
