package pokinboroda.andriy.com.foxhunting.Controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;

import java.util.Random;

import pokinboroda.andriy.com.foxhunting.R;
import pokinboroda.andriy.com.foxhunting.model.GameAreaField;
import pokinboroda.andriy.com.foxhunting.model.GameModel;
import pokinboroda.andriy.com.foxhunting.view.GameView;


/**
 * Created by andriy on 05.07.15.
 */
public class GameController {

    private Activity activity;

    private GameModel gameModel;
    private GameView gameView;

    public GameController(Activity activity) {
        this.activity = activity;
        this.gameModel = new GameModel();
        initializeGameModel();

        this.gameView = new GameView(activity, gameModel);
        this.gameView.updateView();
        this.gameView.setGameAreaFieldClickListener(new OnFieldClickListener());

    }
    public GameController(Activity activity, GameModel gameModel) {
        this.activity = activity;
        this.gameModel = gameModel;
        this.gameView = new GameView(activity,gameModel);
        this.gameView.updateView();
        this.gameView.setGameAreaFieldClickListener(new OnFieldClickListener());
    }

    private void initializeGameModel() {
        setModelDefaults();

        initializeGameAreaFields();
    }

    private void initializeGameAreaFields() {
        int dimension = GameModel.AREA_DIMENSION;
        gameModel.fields = new GameAreaField[dimension][dimension];
        int value;
        int x;
        int y;

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                gameModel.fields[i][j] = new GameAreaField(0);
            }
        }

        Random random = new Random();

        for (int i = 0; i < gameModel.foxes; i++) {
            x = random.nextInt(dimension);
            y = random.nextInt(dimension);

            if (gameModel.fields[x][y].getValue() != GameAreaField.FIELD_FOX) {
                gameModel.fields[x][y].setValue(GameAreaField.FIELD_FOX);
            } else {
                i--;
            }
        }

        /* initialize gameModel.fields */
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (gameModel.fields[j][i].getValue()
                        != GameAreaField.FIELD_FOX) {
                    value = 0;

                    /* check vertical and horizontal */
                    for (int k = 0; k < dimension; k++) {
                        if (gameModel.fields[k][i].getValue()
                                == GameAreaField.FIELD_FOX) {
                            value++;
                        }
                        if (gameModel.fields[j][k].getValue()
                                == GameAreaField.FIELD_FOX) {
                            value++;
                        }
                    }

                    /* check diagonals */
                        /* left top part */
                    for (x = j - 1, y = i - 1; x >= 0 && y >= 0; x--, y--) {
                        if (gameModel.fields[x][y].getValue()
                                == GameAreaField.FIELD_FOX) {
                            value++;
                        }
                    }
                        /* right top part */
                    for (x = j + 1, y = i - 1; x < dimension && y >= 0;
                         x++, y--) {
                        if (gameModel.fields[x][y].getValue()
                                == GameAreaField.FIELD_FOX) {
                            value++;
                        }
                    }
                        /* left bottom part */
                    for (x = j - 1, y = i + 1; x >= 0 && y < dimension;
                         x--, y++) {
                        if (gameModel.fields[x][y].getValue()
                                == GameAreaField.FIELD_FOX) {
                            value++;
                        }
                    }
                        /* right bottom part */
                    for (x = j + 1, y = i + 1; x < dimension && y < dimension;
                         x++, y++) {
                        if (gameModel.fields[x][y].getValue()
                                == GameAreaField.FIELD_FOX) {
                            value++;
                        }
                    }

                    gameModel.fields[j][i].setValue(value);
                }
            }
        }
    }

    private int XYToPosition(int x, int y) {
        return (y == 0) ? x : y * GameModel.AREA_DIMENSION + x;
    }

    private int positionToY(int position) {
        return (position < GameModel.AREA_DIMENSION)
                ? 0 : (int) Math.floor(position / GameModel.AREA_DIMENSION);
    }

    private int positionToX(int position) {
        return (position < gameModel.AREA_DIMENSION)
                ? position : position % GameModel.AREA_DIMENSION;
    }

    private class OnFieldClickListener implements OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view,
                                int position, long id) {
            GameAreaField field = gameModel
                    .fields[positionToX(position)][positionToY(position)];
            field.changeState(GameAreaField.States.SHOWED);

            if (field.getValue() == GameAreaField.FIELD_FOX) {
                gameModel.score += GameModel.SCORE_BONUS;
                gameModel.huntedFoxes++;
                gameModel.power++;
            } else {
                gameModel.score += field.getValue();
                gameModel.power--;
            }

            gameView.updateView();

            if (gameModel.foxes <= gameModel.huntedFoxes) { // win
                gameView.setGameAreaFieldClickListener(null);

                new AlertDialog.Builder(activity)
                        .setMessage(R.string.level_up_message)
                        .setPositiveButton(R.string.continue_button, null)
                        .setNegativeButton(R.string.exit_button,
                                new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                activity.finish();
                            }
                        })
                        .setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                levelUp();
                            }
                        })
                        .setCancelable(false)
                        .create()
                        .show();

            } else if (gameModel.power <= 0) { // lose
                gameView.setGameAreaFieldClickListener(null);

                AlertDialog.Builder alertDialog = new AlertDialog
                        .Builder(activity);

//                final View dialogLayout = activity.getLayoutInflater()
//                        .inflate(R.layout.dialog_enter_name, null);
//
//                if (scoreList.getWorstScore().getScore() < score)
//                    alertDialog.setView(dialogLayout);

                alertDialog
                        .setTitle(R.string.lose_message)
                        .setPositiveButton(R.string.new_game_button, null)
                        .setNegativeButton(R.string.exit_button,
                                new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                activity.finish();
                            }
                        })
                        .setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {

//                                if (scoreList.getWorstScore().getScore() < score) {
//                                    String name = ((EditText) dialogLayout.findViewById(R.id.name_edit)).getText().toString();
//                                    name = (name.equals("")) ? "Unknown player" : name;
//
//                                    scoreList.addScore(new ScoreItem(score, level, name));
//                                }
//
//                                setToDefaults();

                                newGame();
                            }
                        })
                        .setCancelable(false)
                        .create()
                        .show();
            }
        }
    }

    private void levelUp() {
        gameModel.level++;
        gameModel.power = GameModel.POWER_DEFAULT;
        gameModel.foxes++;
        gameModel.huntedFoxes = GameModel.HUNTED_FOXES_DEFAULT;

        gameView.updateView();
        gameView.setGameAreaFieldClickListener(new OnFieldClickListener());
    }

    private void newGame() {
        setModelDefaults();

        gameView.updateView();
        gameView.setGameAreaFieldClickListener(new OnFieldClickListener());

    }

    private void setModelDefaults() {
        gameModel.level = GameModel.LEVEL_DEFAULT;
        gameModel.power = GameModel.POWER_DEFAULT;
        gameModel.score = GameModel.SCORE_DEFAULT;
        gameModel.foxes = GameModel.FOXES_DEFAULT;
        gameModel.huntedFoxes = GameModel.HUNTED_FOXES_DEFAULT;
    }
}
