package com.pokinboroda.andriy.foxhunting.score;

import android.content.Context;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.pokinboroda.andriy.foxhunting.util.ObjectSerializer;

/**
 * Manage all score records and persistently store they.
 */
public class ScoreList  {

    /** Max count of records in score list. */
    private static final int MAX_COUNT_OF_RECORDS = 10;

    /** Name of file in internal storage what store scores list. */
    private static final String STORE_FILE_NAME = "/scoreList.list";

    /** Application context. */
    private Context context;

    /** Scores list. */
    private List<ScoreItem> scoreList;

    /**
     * Instantiates a new Score list.
     * Restore from file if exist, otherwise create new.
     *
     * @param mContext the application context
     */
    public ScoreList(final Context mContext) {
        this.context = mContext;

        scoreList = ObjectSerializer.deserializeFromFile(context,
                STORE_FILE_NAME);

        if (scoreList == null) {
            scoreList = new LinkedList<>();
        }
    }

    /**
     * Save score list to file.
     */
    public final void saveToFile() {
        ObjectSerializer.serializeToFile(context, STORE_FILE_NAME, scoreList);
    }

    /**
     * Add score.
     * Method automatically decide if should store record appropriate to
     * {@code MAX_COUNT_OF_RECORDS} and worst score in list.
     *
     * @param scoreRecord the score record
     */
    public final void addScore(final ScoreItem scoreRecord) {
        if (scoreList.size() < MAX_COUNT_OF_RECORDS) {
            scoreList.add(scoreRecord);
        } else if (getWorstScore().getScore() < scoreRecord.getScore()) {
            scoreList.add(scoreRecord);
            Collections.sort(scoreList);
            scoreList.remove(MAX_COUNT_OF_RECORDS);
        }
    }

    /**
     * Gets worst score record.
     *
     * @return the worst score
     */
    public final ScoreItem getWorstScore() {
        if (scoreList.size() < MAX_COUNT_OF_RECORDS) {
            return new ScoreItem(0, 0, "");
        } else {
            Collections.sort(scoreList);
            return scoreList.get(scoreList.size() - 1);
        }
    }

    /**
     * Gets score list.
     *
     * @return the score list
     */
    public final List<ScoreItem> getScoreList() {
        Collections.sort(scoreList);
        return scoreList;
    }

    /**
     * Delete score list.
     */
    public final void deleteScoreList() {
        scoreList = new LinkedList<>();
        saveToFile();
    }
}
