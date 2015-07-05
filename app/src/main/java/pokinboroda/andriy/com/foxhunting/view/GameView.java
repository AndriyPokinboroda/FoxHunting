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
        this.gameAreaView = new GameAreaView(activity, gameModel);

        this.levelTextView = (TextView) activity.findViewById(R.id.level_text);
        this.foxesTextView = (TextView) activity.findViewById(R.id.foxes_text);
        this.powerTextView = (TextView) activity.findViewById(R.id.power_text);
        this.scoreTextView = (TextView) activity.findViewById(R.id.score_text);

        updateStatistic();
    }

    public void updateView() {
        updateStatistic();
        this.gameAreaView.updateView();
    }

    private void updateStatistic() {
        this.levelTextView.setText(gameModel.level + "");
        this.foxesTextView.setText(gameModel.huntedFoxes + " / "
                + gameModel.foxes);
        this.powerTextView.setText(gameModel.power + "");
        this.scoreTextView.setText(gameModel.score + "");
    }

    public void setGameAreaFieldClickListener(
            AdapterView.OnItemClickListener clickListener) {
        this.gameAreaView.setOnFieldClickListener(clickListener);
    }
}
