package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Date;

public class ScoreManager {
    private static final String TABLE_NAME = "score";
    public static final String KEY_ID_SCORE = "id_score";
    public static final String KEY_EMAIL_USER = "email_user";
    public static final String KEY_VALUE_SCORE = "value_score";
    public static final String CREATE_TABLE_SCORE = "CREATE TABLE `score` (\n" +
            "  `id_score` INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "  `email_user` VARCHAR(45) NOT NULL,\n" +
            "  `value_score` INT NOT NULL);";

    private MySQLite myDb;
    private SQLiteDatabase db;

    public ScoreManager(Context context){
        myDb = MySQLite.getInstance(context);
    }

    public void open(){
        db = myDb.getWritableDatabase();
    }

    public void close(){
        db.close();
    }

    public long createScore(Score _score){

        ContentValues values = new ContentValues();

        values.put(KEY_EMAIL_USER, _score.getPlayer());
        values.put(KEY_VALUE_SCORE, _score.getScore());

        return db.insert(TABLE_NAME, null, values);
    }

    public Score readScore(String _email){
        Score score = new Score("null", 0);
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_EMAIL_USER + " = " + "'" + _email + "'", null);
        if(cursor.moveToFirst()){
            score.setIdScore(cursor.getInt(cursor.getColumnIndex(KEY_ID_SCORE)));
            score.setPlayer(cursor.getString(cursor.getColumnIndex(KEY_EMAIL_USER)));
            score.setScore(cursor.getInt(cursor.getColumnIndex(KEY_VALUE_SCORE)));

            cursor.close();
            return score;
        }
        return score;
    }

    public int upgradeScore(Score _score){
        ContentValues values = new ContentValues();

        String player = "'" + _score.getPlayer() + "'";
        String score = "'" + _score.getScore() + "'";

        values.put(KEY_EMAIL_USER, player);
        values.put(KEY_VALUE_SCORE, score);

        String whereClause = KEY_ID_SCORE + " = ?";
        String[] whereClauseArgs = {_score.getPlayer() + ""};

        return db.update(TABLE_NAME,values, whereClause, whereClauseArgs);
    }

    public int deleteScore(Score _score){
        String whereClause = KEY_ID_SCORE + " = ?";
        String[] whereClauseArgs = {_score.getPlayer() + ""};

        return db.delete(TABLE_NAME, whereClause, whereClauseArgs);
    }
}