package pokinboroda.andriy.com.foxhunting.score;

import android.content.Context;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import pokinboroda.andriy.com.foxhunting.util.ObjectSerializer;

public class ScoreList  {

    private Context context;
    private static final int MAX_COUNT_OF_RECORDS = 10;
    private static final String STORE_FILE_NAME = "/scoreList.list";
    private List<ScoreItem> scoreList;

    public ScoreList(Context context) {
        this.context = context;
        scoreList = ObjectSerializer.deserializeFromFile(context,
                STORE_FILE_NAME);
        if (scoreList == null) {
            scoreList = new LinkedList<>();
        }
    }

    public void saveToFile() {
        ObjectSerializer.serializeToFile(context, STORE_FILE_NAME, scoreList);
    }

    public void addScore(ScoreItem scoreRecord) {
        if (scoreList.size() < MAX_COUNT_OF_RECORDS)
            scoreList.add(scoreRecord);
        else if (getWorstScore().getScore() < scoreRecord.getScore()) {
            scoreList.add(scoreRecord);
            Collections.sort(scoreList);
            scoreList.remove(MAX_COUNT_OF_RECORDS);
        }
    }

    public boolean isFull() {
        return (scoreList.size() >= MAX_COUNT_OF_RECORDS);
    }

    public ScoreItem getWorstScore() {
        if (scoreList.size() < MAX_COUNT_OF_RECORDS) {
            return new ScoreItem(0, 0, "");
        } else {
            Collections.sort(scoreList);
            return scoreList.get(scoreList.size() - 1);
        }
    }

    public List<ScoreItem> getScoreList() {
        Collections.sort(scoreList);
        return scoreList;
    }

    public void deleteScoreList() {
        scoreList = new LinkedList<>();
        saveToFile();
    }
}