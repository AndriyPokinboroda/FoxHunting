package pokinboroda.andriy.com.foxhunting.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andriy on 05.07.15.
 */
public class GameModel {
    public static final int LEVEL_DEFAULT = 1;
    public static final int FOXES_DEFAULT = 3;
    public static final int HUNTED_FOXES_DEFAULT = 0;
    public static final int POWER_DEFAULT = 10;
    public static final int SCORE_DEFAULT = 0;
    public static final int SCORE_BONUS = 100;

    public static final int AREA_DIMENSION = 10;

    public int level;
    public int foxes;
    public int huntedFoxes;
    public int power;
    public int score;

    public GameAreaField[][] fields;


    public List<GameAreaField> getGameAreaFields() {
        List<GameAreaField> listFields = new ArrayList<>();

        for (int i = 0; i < AREA_DIMENSION; i++) {
            for (int j = 0; j < AREA_DIMENSION; i++) {
                listFields.add(fields[i][j]);
            }
        }

        return listFields;
    }
}
