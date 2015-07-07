package com.pokinboroda.andriy.foxhunting;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.widget.DrawerLayout;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.pokinboroda.andriy.foxhunting.controller.GameController;
import com.pokinboroda.andriy.foxhunting.model.GameModel;
import com.pokinboroda.andriy.foxhunting.score.ScoreList;
import com.pokinboroda.andriy.foxhunting.util.ObjectSerializer;

import pokinboroda.andriy.com.foxhunting.R;


/**
 * The type Game activity.
 */
public class GameActivity extends AppCompatActivity {
    private static final String GAME_MODEL_FILE = "/gameModel.obj";

    /* Drawer */
    private ListView menuList;
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;

    private ScoreList scoreList;
    private GameController gameController;

    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        createDrawerMenu();

        scoreList = new ScoreList(this);

        GameModel gameModel = ObjectSerializer
                .deserializeFromFile(this, GAME_MODEL_FILE);

        if (gameModel == null) {
            gameController = new GameController(this, scoreList);
        } else {
            gameController = new GameController(this, scoreList, gameModel);
        }
    }

    @Override
    protected final void onStop() {
        super.onStop();

        scoreList.saveToFile();

        ObjectSerializer.serializeToFile(this, GAME_MODEL_FILE,
                                         gameController.getGameModel());
    }

    @Override
    public final boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public final boolean onOptionsItemSelected(final MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /* Drawer side menu */
    private void createDrawerMenu() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close) {
            public void onDrawerOpened(final View view) {
                super.onDrawerOpened(view);
                getSupportActionBar().setTitle(R.string.drawer_menu_title);
            }

            public void onDrawerClosed(final View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(R.string.app_name);
            }
        };

        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.setDrawerListener(drawerToggle);

        menuList = (ListView) findViewById(R.id.drawer_list);
        menuList.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.menu_array)));
        menuList.setOnItemClickListener(new DrawerItemClickListener());
    }

    private class DrawerItemClickListener
            implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(final AdapterView<?> parent, final View view,
                                final int position, final long id) {

            switch (position) {
            case 0 : // Score
                startActivity(new Intent(GameActivity.this,
                        ScoreActivity.class));
                break;
            case 1 : // Rules
                startActivity(new Intent(GameActivity.this,
                        RulesActivity.class));
                break;
            case 2 : // About
                drawerLayout.closeDrawer(Gravity.LEFT);

                new AlertDialog.Builder(GameActivity.this)
                        .setTitle(R.string.about_title)
                        .setMessage(R.string.about_message)
                        .setNegativeButton(R.string.close_button, null)
                        .create()
                        .show();
                break;
            case 3 : // Exit
                finish();
            default:
                new AlertDialog.Builder(GameActivity.this)
                        .setTitle(R.string.internal_error)
                        .setNegativeButton(R.string.exit_button,
                                new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog,
                                                final int which) {
                                finish();
                            }
                        })
                        .create()
                        .show();
            }
        }
    }

    @Override
    protected final void onPostCreate(final Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }
}
