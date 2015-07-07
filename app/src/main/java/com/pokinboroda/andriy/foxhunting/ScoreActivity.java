package com.pokinboroda.andriy.foxhunting;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import com.pokinboroda.andriy.foxhunting.score.ScoreItem;
import com.pokinboroda.andriy.foxhunting.score.ScoreList;

import pokinboroda.andriy.com.foxhunting.R;


/**
 * The type Score activity.
 */
public class ScoreActivity extends AppCompatActivity {

    private ListView listView;
    private ScoreList scoreList;

    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        scoreList = new ScoreList(this);
        listView = (ListView) findViewById(R.id.score_list);
        updateListView();
    }

    private void updateListView() {
        listView.setAdapter(new ScoreRecordAdapter(this, R.layout.score_item,
                                                   scoreList.getScoreList()));
    }

    @Override
    public final boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_score, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public final boolean onOptionsItemSelected(final MenuItem item) {
        // Handle presses on the action bar items
        if (item.getItemId() == R.id.action_delete) {
            scoreList.deleteScoreList();
            updateListView();
        }
        return super.onOptionsItemSelected(item);
    }

    private class ScoreRecordAdapter extends ArrayAdapter<ScoreItem> {

        private Context context;
        private int resourceId;
        private List<ScoreItem> scoreList;

        /**
         * Instantiates a new Score record adapter.
         *
         * @param mContext the m context
         * @param resource the resource
         * @param objects the objects
         */
        public ScoreRecordAdapter(final Context mContext, final int resource,
                                  final List<ScoreItem> objects) {
            super(mContext, resource, objects);
            this.context = mContext;
            this.resourceId = resource;
            this.scoreList = objects;
        }

        @Override
        public int getCount() {
            return scoreList.size();
        }

        @Override
        public View getView(final int position, View view,
                            final ViewGroup parent) {
            view = ((Activity) context).getLayoutInflater()
                    .inflate(resourceId, parent, false);

            ((TextView) view.findViewById(R.id.score_item_place_text))
                    .setText((position  + 1) + "");
            ((TextView) view.findViewById(R.id.score_item_username_text))
                    .setText(scoreList.get(position).getPlayerName());
            ((TextView) view.findViewById(R.id.score_item_level_text))
                    .setText(scoreList.get(position).getLevel() + "");
            ((TextView) view.findViewById(R.id.score_item_score_text))
                    .setText(scoreList.get(position).getScore() + "");

            return view;
        }
    }
}
