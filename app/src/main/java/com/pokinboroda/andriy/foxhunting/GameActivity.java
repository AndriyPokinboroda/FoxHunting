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
 * The GameActivity is the main activity. On this activity pass over the game.
 * Basically this activity just instantiate {@link GameController}
 * what manage exactly the game. This class save and restore game model
 * and save to file the score.
 *
 * @author Pokinboroda Andriy
 * @version 0.1
 */
public class GameActivity extends AppCompatActivity {

    /** Name of file what store {@link GameModel} state. */
    private static final String GAME_MODEL_FILE = "/gameModel.obj";

    /** Represent items of side menu. */
    public enum MenuItems {

        /** Score item. */
        SCORE(0),

        /** Rules item. */
        RULES(1),

        /** About item. */
        ABOUT(2),

        /** Exit item. */
        EXIT(3);

        /** Position of item. */
        private int position;

        /** Constructor with initialize item by position.
         *
         * @param mPosition position of item */
        MenuItems(final int mPosition) {
            this.position = mPosition;
        }

        /**
         * Return menu item appropriate to position.
         *
         * @param mPosition item position
         * @return menu item, if item with this position does not exist
         *         return EXIT item
         */
        public static MenuItems getItemByPosition(final int mPosition) {
            for (MenuItems item : MenuItems.values()) {
                if (item.position == mPosition) {
                    return item;
                }
            }

            return MenuItems.EXIT;
        }
    }

    /** Represent drawer menu items list. */
    private ListView menuList;

    /**
     * Use to make the button on action bar
     * act like toggle for side menu(Open/Close).
     */
    private ActionBarDrawerToggle drawerToggle;

    /** Drawer layout for side menu. */
    private DrawerLayout drawerLayout;

    /** Game score list. */
    private ScoreList scoreList;

    /** Game controller for fox hunting game. */
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
    protected final void onPause() {
        super.onPause();

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

    /** Method what configure drawer menu. */
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


    /** Class for handling side menu items click. */
    private class DrawerItemClickListener
            implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(final AdapterView<?> parent, final View view,
                                final int position, final long id) {
            MenuItems chooseItem = MenuItems.getItemByPosition(position);

            switch (chooseItem) {
            case SCORE :
                startActivity(new Intent(GameActivity.this,
                        ScoreActivity.class));
                break;
            case RULES :
                startActivity(new Intent(GameActivity.this,
                        RulesActivity.class));
                break;
            case ABOUT :
                drawerLayout.closeDrawer(Gravity.LEFT);

                new AlertDialog.Builder(GameActivity.this)
                        .setTitle(R.string.about_title)
                        .setMessage(R.string.about_message)
                        .setNegativeButton(R.string.close_button, null)
                        .create()
                        .show();
                break;
            case EXIT :
                finish();
                break;
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
