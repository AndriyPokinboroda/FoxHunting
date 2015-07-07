package com.pokinboroda.andriy.foxhunting.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;

import java.util.Random;

import pokinboroda.andriy.com.foxhunting.R;
import com.pokinboroda.andriy.foxhunting.model.GameAreaField;
import com.pokinboroda.andriy.foxhunting.model.GameModel;
import com.pokinboroda.andriy.foxhunting.score.ScoreItem;
import com.pokinboroda.andriy.foxhunting.score.ScoreList;
import com.pokinboroda.andriy.foxhunting.view.GameView;

/**
 * This class bind {@code GameModel} and {@code GameView}, manage their
 * compatible work. Also this class add {@code ItemScore} to {@code ScoreList}.
 *
 * @author Pokinboroda Andriy
 * @version 0.1
 * @see com.pokinboroda.andriy.foxhunting.model.GameModel
 * @see com.pokinboroda.andriy.foxhunting.view.GameView
 * @see com.pokinboroda.andriy.foxhunting.score
 */
public class GameController {

    /** Game activity. */
    private Activity activity;

    /** Model of fox hunting game. */
    private GameModel gameModel;

    /** View of fox hunting game. */
    private GameView gameView;

    /** Game score list. */
    private ScoreList scoreList;

    /** Constructor what generate new {@link GameModel}.
     *
     * @param mActivity instance of game activity
     * @param mScoreList instance of scores list
     */
    public GameController(final Activity mActivity,
                          final ScoreList mScoreList) {
        this.activity = mActivity;
        this.gameModel = new GameModel();
        this.scoreList = mScoreList;

        initializeGameModel();

        this.gameView = new GameView(activity, gameModel);
        this.gameView.updateView();
        this.gameView.setGameAreaFieldClickListener(
                new OnFieldOnClickListener());
        this.gameView.setNewGameButtonClickListener(
                new NewGameOnClickListener());
    }

    /** Constructor what use saved {@link GameModel}.
     *
     * @param mActivity instance of game activity
     * @param mScoreList instance of scores list
     * @param mGameModel instance of saved
     */
    public GameController(final Activity mActivity, final ScoreList mScoreList,
                          final GameModel mGameModel) {
        this.activity = mActivity;
        this.gameModel = mGameModel;
        this.scoreList = mScoreList;
        this.gameView = new GameView(activity, gameModel);
        this.gameView.setGameAreaFieldClickListener(
                new OnFieldOnClickListener());
        this.gameView.setNewGameButtonClickListener(
                new NewGameOnClickListener());
    }

    /**
     * Gets game model.
     *
     * @return current state of
     */
    public final GameModel getGameModel() {
        return this.gameModel;
    }


    /**
     * Class implement {@link android.view.View.OnClickListener}
     * so can handle click to view. Basically create new game.
     */
    private class NewGameOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(final View v) {
            newGame();
        }
    }


    /**
     * Class handle field on click event.
     * Basically manage all playing process.
     */
    private class OnFieldOnClickListener implements OnItemClickListener {

        @Override
        public void onItemClick(final AdapterView<?> parent, final View view,
                                final int position, final long id) {
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

    /** @return {@code true} if player win the game, otherwise {@code false} */
    private boolean isWin() {
        return (gameModel.foxes <= gameModel.huntedFoxes);
    }

    /** @return {@code true} if player lose the game, otherwise {@code false} */
    private boolean isLose() {
        return (gameModel.power <= 0);
    }

    /**
     * This method should be called if player win the game.
     * In general show dialog with level completion message and offer new level,
     * if answer is positive generate new level.
     */
    private void win() {
        gameView.setGameAreaFieldClickListener(null);

        new AlertDialog.Builder(activity)
                .setMessage(R.string.level_up_message)
                .setPositiveButton(R.string.continue_button, null)
                .setNegativeButton(R.string.exit_button,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog,
                                                final int which) {
                                activity.finish();
                            }
                        })
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(
                                    final DialogInterface dialog) {
                                levelUp();
                            }
                        })
                .setCancelable(false)
                .create()
                .show();

    }

    /**
     * This method should be called if player lose the game.
     * In general show dialog with lose message and offer new game,
     * if answer is positive generate new game.
     * Also if score of the current game in top best, what determined by
     * {@link ScoreList}, ask player name to save the score.
     */
    private void lose() {
        gameView.setGameAreaFieldClickListener(null);

        AlertDialog.Builder alertDialog = new AlertDialog
                .Builder(activity);

        final View dialogLayout = activity.getLayoutInflater()
                .inflate(R.layout.dialog_enter_name, null);

        if (scoreList.getWorstScore().getScore() < gameModel.score) {
            alertDialog.setView(dialogLayout);
        }

        alertDialog
                .setTitle(R.string.lose_message)
                .setPositiveButton(R.string.new_game_button, null)
                .setNegativeButton(R.string.exit_button,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog,
                                                final int which) {
                                activity.finish();
                            }
                        })
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(
                                    final DialogInterface dialog) {

                                if (scoreList.getWorstScore().getScore()
                                        < gameModel.score) {
                                    String name = ((EditText) dialogLayout
                                            .findViewById(R.id.name_edit))
                                            .getText().toString();

                                    name = (name.equals(""))
                                            ? "Unknown player"
                                            : name;

                                    scoreList.addScore(
                                            new ScoreItem(gameModel.score,
                                                    gameModel.level, name));
                                }

                                newGame();
                            }
                        })
                .setCancelable(false)
                .create()
                .show();
    }

    /** Prepare {@link GameModel} to new level and update {@link GameView}. */
    private void levelUp() {
        gameModel.level++;
        gameModel.power = GameModel.POWER_DEFAULT;
        gameModel.foxes++;
        gameModel.huntedFoxes = GameModel.HUNTED_FOXES_DEFAULT;

        initializeGameAreaFields();

        gameView = new GameView(activity, gameModel);
        gameView.setGameAreaFieldClickListener(new OnFieldOnClickListener());
    }

    /**
     * Set {@link GameModel} to default state, generate new game field
     * and update {@link GameView}.
     */
    private void newGame() {
        initializeGameModel();

        gameView = new GameView(activity, gameModel);
        gameView.setGameAreaFieldClickListener(new OnFieldOnClickListener());

    }
    /** Set {@link GameModel} to default state, generate new game field. */
    private void initializeGameModel() {
        setModelDefaults();
        initializeGameAreaFields();
    }
    /** Set statistic fields from {@link GameModel} to default state. */
    private void setModelDefaults() {
        gameModel.level = GameModel.LEVEL_DEFAULT;
        gameModel.power = GameModel.POWER_DEFAULT;
        gameModel.score = GameModel.SCORE_DEFAULT;
        gameModel.foxes = GameModel.FOXES_DEFAULT;
        gameModel.huntedFoxes = GameModel.HUNTED_FOXES_DEFAULT;
    }

    /**
     * Initialize fields from {@link GameModel}.
     * Basically, create new 2D array,
     * place in random way foxes, their count determine by {@link GameModel}
     * field {@code foxes} and appropriate to foxes placement calculate
     * each field value.
     */
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

    /**
     * This method should be called when zero fields is open.
     * Open fields what place in horizontal, vertical and diagonal line with
     * opened zero field.
     *
     * @param xCoordinate x coordinate of zero field
     * @param yCoordinate y coordinate of zero field
     */
    private void showCompatibleFields(final int xCoordinate,
                                      final int yCoordinate) {
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
        for (x = xCoordinate - 1, y = yCoordinate - 1;
             x >= 0 && y >= 0; x--, y--) {
            gameModel.fields[x][y].setState(GameAreaField.States.SHOWED);
        }
        /* right top part */
        for (x = xCoordinate + 1, y = yCoordinate - 1;
             x < dimension && y >= 0; x++, y--) {
            gameModel.fields[x][y].setState(GameAreaField.States.SHOWED);
        }
        /* left bottom part */
        for (x = xCoordinate - 1, y = yCoordinate + 1;
             x >= 0 && y < dimension; x--, y++) {
            gameModel.fields[x][y].setState(GameAreaField.States.SHOWED);
        }
        /* right bottom part */
        for (x = xCoordinate + 1, y = yCoordinate + 1;
             x < dimension && y < dimension; x++, y++) {
            gameModel.fields[x][y].setState(GameAreaField.States.SHOWED);
        }
    }

    /**
     * This method and {@code positionToX()} method created for adapt GridView
     * indexing system to {@link GameModel} 2D indexing system.
     *
     * @param position GridView item position
     * @return y coordinate in {@link GameModel} indexing system.
     */
    private int positionToY(final int position) {
        return (position < GameModel.AREA_DIMENSION)
                ? 0
                : (int) Math.floor(position / GameModel.AREA_DIMENSION);
    }

    /**
     * This method and {@code positionToY()} method created for adapt GridView
     * indexing system to {@link GameModel} 2D indexing system.
     *
     * @param position GridView item position
     * @return x coordinate in {@link GameModel} indexing system.
     */
    private int positionToX(final int position) {
        return (position < GameModel.AREA_DIMENSION)
                ? position
                : position % GameModel.AREA_DIMENSION;
    }
}
