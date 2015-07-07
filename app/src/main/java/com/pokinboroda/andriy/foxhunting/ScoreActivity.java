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
 * The activity what display score list.
 *
 * @author Pokinboroda Andriy
 * @version 0.1
 */
public class ScoreActivity extends AppCompatActivity {

    /** The list view which display scores. */
    private ListView listView;

    /** The score list what store scores. */
    private ScoreList scoreList;

    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        scoreList = new ScoreList(this);
        listView = (ListView) findViewById(R.id.score_list);
        updateListView();
    }

    /** Update listView. */
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

    /** This class serve as adapter for listView. */
    private class ScoreRecordAdapter extends ArrayAdapter<ScoreItem> {

        /** Application context. */
        private Context context;

        /** Layout for list item. */
        private int resourceId;

        /** List of ScoreItems. */
        private List<ScoreItem> scoreList;

        /**
         * Instantiates a new Score record adapter.
         *
         * @param mContext the application context
         * @param resource the layout resource for item
         * @param scoreItems the list of records
         */
        public ScoreRecordAdapter(final Context mContext, final int resource,
                                  final List<ScoreItem> scoreItems) {
            super(mContext, resource, scoreItems);
            this.context = mContext;
            this.resourceId = resource;
            this.scoreList = scoreItems;
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
