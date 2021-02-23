package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;

public class UserManager {
    private static final String TABLE_NAME = "user";
    public static final String KEY_ID_USER = "id_user";
    public static final String KEY_NOM_USER = "nom_user";
    public static final String KEY_PASSWORD_USER = "password_user";
    public static final String KEY_BIRTH_USER = "birth_user";

    public static final String CREATE_TABLE_USER = "CREATE TABLE" + TABLE_NAME +
            KEY_ID_USER + " INT NOT NULL,\n" +
            KEY_NOM_USER + " VARCHAR(45) NOT NULL,\n" +
            KEY_PASSWORD_USER + " VARCHAR(45) NOT NULL,\n" +
            KEY_BIRTH_USER +  " VARCHAR(45) NOT NULL,\n" +
            "PRIMARY KEY ('" + KEY_ID_USER +"'))\n";

    private MySQLite myDb;
    private SQLiteDatabase db;

    public UserManager(Context context){
        myDb = MySQLite.getInstance(context);
    }

    public void open(){
        db = MySQLite.getWritableInstance();
    }

    public void close(){
        db.close();
    }

    public long createUser(User _user){
        ContentValues values = new ContentValues();
        values.put(KEY_NOM_USER, _user.getName());
        values.put(KEY_PASSWORD_USER, _user.getPassword());
        values.put(KEY_BIRTH_USER, _user.getBirth());

        return db.insert(TABLE_NAME, null, values);
    }

    public User readUser(int _id){
        User user = new User();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_ID_USER + " = " + _id, null);
        if(cursor.moveToFirst()){
            user.setIdUser(cursor.getInt(cursor.getColumnIndex(KEY_ID_USER)));
            user.setName(cursor.getString(cursor.getColumnIndex(KEY_NOM_USER)));
            user.setPassword(cursor.getString(cursor.getColumnIndex(KEY_PASSWORD_USER)));
            user.setBirth(cursor.getString(cursor.getColumnIndex(KEY_BIRTH_USER)));
            cursor.close();
        }
        return user;
    }

    public int upgradeUser(User _user){
        ContentValues values = new ContentValues();
        values.put(KEY_NOM_USER, _user.getName());
        values.put(KEY_PASSWORD_USER, _user.getPassword());
        values.put(KEY_BIRTH_USER, _user.getBirth());

        String whereClause = KEY_ID_USER + " = ?";
        String[] whereClauseArgs = {_user.getName() + ""};

        return db.delete(TABLE_NAME, whereClause, whereClauseArgs);
    }

    public int deleteUser(User _user){
        String whereClause = KEY_ID_USER + " = ?";
        String[] whereClauseArgs = {_user.getName() + ""};

        return db.delete(TABLE_NAME, whereClause, whereClauseArgs);
    }

    public Cursor getUsers(){
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }
}
