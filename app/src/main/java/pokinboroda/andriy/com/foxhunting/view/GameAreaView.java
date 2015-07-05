package pokinboroda.andriy.com.foxhunting.view;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import pokinboroda.andriy.com.foxhunting.R;
import pokinboroda.andriy.com.foxhunting.model.GameAreaField;
import pokinboroda.andriy.com.foxhunting.model.GameAreaField.States;
import pokinboroda.andriy.com.foxhunting.model.GameModel;

/**
 * Created by andriy on 05.07.15.
 */
public class GameAreaView extends BaseAdapter {

    private Activity activity;
    private GridView gridView;
    private GameModel gameModel;
    private List<GameAreaField> fields;


    public GameAreaView(Activity activity, GameModel gameModel) {
        this.activity = activity;
        this.gameModel = gameModel;
        this.fields = gameModel.getGameAreaFields();
        this.gridView = (GridView) activity.findViewById(R.id.play_area_grid);
        this.gridView.setAdapter(this);
    }
    @Override
    public int getCount() {
        return fields.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View fieldView = activity.getLayoutInflater()
                .inflate(R.layout.field_play_area, parent, false);
        TextView fieldTextView = (TextView) fieldView
                .findViewById(R.id.item_text);
        ImageView fieldImageView = (ImageView) fieldView
                .findViewById(R.id.item_image);

        fieldTextView.setText(this.fields.get(position).getText());
        fieldImageView.setImageResource(this.fields.get(position).getImage());

        return fieldView;
    }

    public void updateView() {
        this.fields = gameModel.getGameAreaFields();

        for (int i = 0; i < this.fields.size(); i++) {
            if ((this.fields.get(i).getState() == States.SHOWED)) {
                this.fields.get(i).setState(States.SHOWED);
                View fieldView = gridView.getChildAt(i);

                ((TextView) fieldView.findViewById(R.id.item_text))
                        .setText(this.fields.get(i).getText());
                ((ImageView) fieldView.findViewById(R.id.item_image))
                        .setImageResource(this.fields.get(i).getImage());

                fieldView.setOnClickListener(null);
            }
        }
    }

    public void setOnFieldClickListener(OnItemClickListener clickListener) {
        gridView.setOnItemClickListener(clickListener);
    }
}
