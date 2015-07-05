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

/**
 * Created by andriy on 05.07.15.
 */
public class GameAreaView extends BaseAdapter {

    private Activity activity;
    private GridView gridView;
    private List<GameAreaField> fields;

    public GameAreaView(Activity activity) {
        this.activity = activity;
        this.fields = null;
        this.gridView = (GridView) activity.findViewById(R.id.play_area_grid);
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

        fieldTextView.setText(fields.get(position).getText());
        fieldImageView.setImageResource(fields.get(position).getImage());

        return fieldView;
    }

    public void updateView(List<GameAreaField> newFields) {
        this.fields = newFields;

        gridView.setAdapter(this);
    }

    public void setOnFieldClickListener(OnItemClickListener clickListener) {
        gridView.setOnItemClickListener(clickListener);
    }
}
