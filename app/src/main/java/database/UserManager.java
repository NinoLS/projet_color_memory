package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Date;

public class UserManager {
    private static final String TABLE_NAME = "user";
    public static final String KEY_ID_USER = "id_user";
    public static final String KEY_NOM_USER = "nom_user";
    public static final String KEY_PASSWORD_USER = "password_user";
    public static final String KEY_BIRTH_USER = "birth_user";
    public static final String KEY_EMAIL_USER = "email_user";
    public static final String KEY_GENDER_USER = "gender_user";
    public static final String CREATE_TABLE_USER = "CREATE TABLE `user` (\n" +
            "  `id_user` INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "  `nom_user` VARCHAR(45) NOT NULL,\n" +
            "  `password_user` VARCHAR(45) NOT NULL,\n" +
            "  `birth_user` VARCHAR(45) NOT NULL,\n" +
            "  `email_user` VARCHAR(255) NOT NULL,\n" +
            "  `gender_user` TINYINT(1) NOT NULL)";

    private MySQLite myDb;
    private SQLiteDatabase db;

    public UserManager(Context context){
        myDb = MySQLite.getInstance(context);
    }

    public void open(){
        db = myDb.getWritableDatabase();
    }

    public void close(){
        db.close();
    }

    public long createUser(User _user){

        ContentValues values = new ContentValues();

        values.put(KEY_NOM_USER, _user.getName());
        values.put(KEY_PASSWORD_USER, _user.getPassword());
        values.put(KEY_BIRTH_USER, _user.getBirth());
        values.put(KEY_EMAIL_USER, _user.getEmail());
        values.put(KEY_GENDER_USER, _user.getGender());

        return db.insert(TABLE_NAME, null, values);
    }

    public User readUser(String _email){
        User user = new User("null", "null", "null", "null", (byte) 0);
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_EMAIL_USER + " = " + "'" + _email + "'", null);
        if(cursor.moveToFirst()){
            user.setIdUser(cursor.getInt(cursor.getColumnIndex(KEY_ID_USER)));
            user.setName(cursor.getString(cursor.getColumnIndex(KEY_NOM_USER)));
            user.setPassword(cursor.getString(cursor.getColumnIndex(KEY_PASSWORD_USER)));
            user.setBirth(cursor.getString(cursor.getColumnIndex(KEY_BIRTH_USER)));
            user.setEmail(cursor.getString(cursor.getColumnIndex(KEY_EMAIL_USER)));
            user.setGender((byte) cursor.getInt(cursor.getColumnIndex(KEY_GENDER_USER)));
            cursor.close();
            return user;
        }
        return user;
    }

    public int upgradeUser(User _user){
        ContentValues values = new ContentValues();

        String name = "'" + _user.getName() + "'";
        String password = "'" + _user.getPassword() + "'";
        String birth = "'" + _user.getBirth() + "'";
        String email = "'" + _user.getEmail() + "'";

        values.put(KEY_NOM_USER, name);
        values.put(KEY_PASSWORD_USER, password);
        values.put(KEY_BIRTH_USER, birth);
        values.put(KEY_EMAIL_USER, email);
        values.put(KEY_GENDER_USER, _user.getGender());

        String whereClause = KEY_ID_USER + " = ?";
        String[] whereClauseArgs = {_user.getEmail() + ""};

        return db.update(TABLE_NAME,values, whereClause, whereClauseArgs);
    }

    public int deleteUser(User _user){
        String whereClause = KEY_ID_USER + " = ?";
        String[] whereClauseArgs = {_user.getEmail() + ""};

        return db.delete(TABLE_NAME, whereClause, whereClauseArgs);
    }

    public Cursor getUsers(){
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }
}