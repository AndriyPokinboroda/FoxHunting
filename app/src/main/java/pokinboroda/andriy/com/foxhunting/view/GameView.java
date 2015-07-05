package pokinboroda.andriy.com.foxhunting.view;

import android.app.Activity;
import android.widget.AdapterView;
import android.widget.TextView;

import pokinboroda.andriy.com.foxhunting.R;
import pokinboroda.andriy.com.foxhunting.model.GameModel;

/**
 * Created by andriy on 05.07.15.
 */
public class GameView {
    private TextView levelTextView;
    private TextView foxesTextView;
    private TextView powerTextView;
    private TextView scoreTextView;

    private Activity activity;
    private GameModel gameModel;
    private GameAreaView gameAreaView;

    public GameView(Activity activity, GameModel gameModel) {
        this.activity = activity;
        this.gameModel = gameModel;
        this.gameAreaView = new GameAreaView(activity);

        levelTextView = (TextView) activity.findViewById(R.id.level_text);
        foxesTextView = (TextView) activity.findViewById(R.id.foxes_text);
        powerTextView = (TextView) activity.findViewById(R.id.power_text);
        scoreTextView = (TextView) activity.findViewById(R.id.score_text);
    }

    public void updateView() {
        levelTextView.setText(gameModel.level + "");
        foxesTextView.setText(gameModel.huntedFoxes + " / "
                              + gameModel.foxes);
        powerTextView.setText(gameModel.power + "");
        scoreTextView.setText(gameModel.score + "");
        gameAreaView.updateView(gameModel.getGameAreaFields());
    }

    public void setGameAreaFieldClickListener(
            AdapterView.OnItemClickListener clickListener) {
        gameAreaView.setOnFieldClickListener(clickListener);
    }
}
