package com.pokinboroda.andriy.foxhunting.view;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pokinboroda.andriy.com.foxhunting.R;
import com.pokinboroda.andriy.foxhunting.model.GameAreaField;
import com.pokinboroda.andriy.foxhunting.model.GameAreaField.States;
import com.pokinboroda.andriy.foxhunting.model.GameModel;

/**
 * Created by andriy on 05.07.15.
 */
public class GameAreaView extends BaseAdapter {

    private Activity activity;
    private GridView gridView;
    private GameModel gameModel;
    private List<GameAreaField> fields;


    /**
     * Instantiates a new Game area view.
     *
     * @param mActivity the m activity
     * @param mGameModel the m game model
     */
    public GameAreaView(final Activity mActivity, final GameModel mGameModel) {
        this.activity = mActivity;
        this.gameModel = mGameModel;
        this.fields = getGameAreaFields();
        this.gridView = (GridView) activity.findViewById(R.id.play_area_grid);
        this.gridView.setAdapter(this);
    }
    @Override
    public final int getCount() {
        return fields.size();
    }

    @Override
    public final Object getItem(final int position) {
        return null;
    }

    @Override
    public final long getItemId(final int position) {
        return 0;
    }

    @Override
    public final View getView(final int position, final View convertView,
                              final ViewGroup parent) {
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

    /**
     * Update view.
     */
    public final void updateView() {
        this.fields = getGameAreaFields();

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

    /**
     * Gets game area fields.
     *
     * @return the game area fields
     */
    public final List<GameAreaField> getGameAreaFields() {
        List<GameAreaField> listFields = new ArrayList<>();

        for (int i = 0; i < GameModel.AREA_DIMENSION; i++) {
            for (int j = 0; j < GameModel.AREA_DIMENSION; j++) {
                listFields.add(gameModel.fields[j][i]);
            }
        }

        return listFields;
    }

    /**
     * Sets on field click listener.
     *
     * @param clickListener the click listener
     */
    public final void setOnFieldClickListener(
            final OnItemClickListener clickListener) {
        gridView.setOnItemClickListener(clickListener);
    }
}
