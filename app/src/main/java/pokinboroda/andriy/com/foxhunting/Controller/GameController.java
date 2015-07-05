package pokinboroda.andriy.com.foxhunting.Controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

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

    private class OnFieldClickListener implements OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view,
                                int position, long id) {
            GameAreaField field = gameModel
                    .fields[positionToX(position)][positionToY(position)];
            field.setState(GameAreaField.States.SHOWED);

            if (field.getValue() == GameAreaField.FIELD_FOX) {
                gameModel.score += GameModel.SCORE_BONUS;
                gameModel.huntedFoxes++;
                gameModel.power++;
            } else {
                if (field.getValue() == GameAreaField.FIELD_ZERO) {
                    showCompatibleFields(positionToX(position),
                                         positionToY(position));
                }
                gameModel.score += field.getValue();
                gameModel.power--;
            }

            gameView.updateView();

            if (isWin()) {
                win();
            } else if (isLose()) {
                lose();
            }
        }
    }

    private boolean isWin() {
        return (gameModel.foxes <= gameModel.huntedFoxes);
    }

    private boolean isLose() {
        return (gameModel.power <= 0);
    }

    private void win() {
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

    }

    private void lose() {
        gameView.setGameAreaFieldClickListener(null);

        AlertDialog.Builder alertDialog = new AlertDialog
                .Builder(activity);

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
                                newGame();
                            }
                        })
                .setCancelable(false)
                .create()
                .show();
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
        initializeGameModel();

        gameView.updateView();
        gameView.setGameAreaFieldClickListener(new OnFieldClickListener());

    }
    private void initializeGameModel() {
        setModelDefaults();

        initializeGameAreaFields();
    }

    private void setModelDefaults() {
        gameModel.level = GameModel.LEVEL_DEFAULT;
        gameModel.power = GameModel.POWER_DEFAULT;
        gameModel.score = GameModel.SCORE_DEFAULT;
        gameModel.foxes = GameModel.FOXES_DEFAULT;
        gameModel.huntedFoxes = GameModel.HUNTED_FOXES_DEFAULT;
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
        /* Set foxes */
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

    private void showCompatibleFields(int xCoordinate, int yCoordinate) {
        int dimension = GameModel.AREA_DIMENSION;
        int x;
        int y;

        /* open vertical and horizontal */
        for (int k = 0; k < dimension; k++) {
            if (k != xCoordinate) {
                gameModel.fields[k][yCoordinate]
                        .setState(GameAreaField.States.SHOWED);
            }
            if (k != yCoordinate) {
                gameModel.fields[xCoordinate][k]
                        .setState(GameAreaField.States.SHOWED);
            }
        }

        /* open diagonals */
        /* left top part */
        for (x = xCoordinate - 1, y = yCoordinate - 1; x >= 0 && y >= 0;
             x--, y--) {
            gameModel.fields[x][y].setState(GameAreaField.States.SHOWED);
        }
        /* right top part */
        for (x = xCoordinate + 1, y = yCoordinate - 1; x < dimension && y >= 0;
             x++, y--) {
            gameModel.fields[x][y].setState(GameAreaField.States.SHOWED);
        }
        /* left bottom part */
        for (x = xCoordinate - 1, y = yCoordinate + 1; x >= 0 && y < dimension;
             x--, y++) {
            gameModel.fields[x][y].setState(GameAreaField.States.SHOWED);
        }
        /* right bottom part */
        for (x = xCoordinate + 1, y = yCoordinate + 1;
             x < dimension && y < dimension; x++, y++) {
            gameModel.fields[x][y].setState(GameAreaField.States.SHOWED);
        }
    }

    private int positionToY(int position) {
        return (position < GameModel.AREA_DIMENSION)
                ? 0 : (int) Math.floor(position / GameModel.AREA_DIMENSION);
    }

    private int positionToX(int position) {
        return (position < GameModel.AREA_DIMENSION)
                ? position : position % GameModel.AREA_DIMENSION;
    }
}
