package com.example.aplicacioncitaconmemin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class UserDB extends SQLiteOpenHelper {
    private static final String DB_FILE = "userDB.db";
    private static final String TABLE_NAME = "Users";
    private static final String FIELD_ID = "ID";
    private static final String FIELD_USERNAME = "Username";
    private static final String FIELD_PASSWORD= "Password";
    private static final String FIELD_CELLPHONE= "Cellphone";

    public UserDB(Context context){
        super(context, DB_FILE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + "(" +
                FIELD_ID + " INTEGER PRIMARY KEY, " +
                FIELD_USERNAME + " TEXT, " +
                FIELD_PASSWORD + " TEXT, " +
                FIELD_CELLPHONE + " TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS ?";
        String[] params = {TABLE_NAME};
        db.execSQL(query, params);
    }

    public boolean registerUser(String username, String password, String cellphone){
        DatosUsuario a = findByID(username);
        //System.out.println(a.getUsername()); si no encuentra el usuario estos dos valores son -1
        //System.out.println(a.getPassword());
        if (findByID(username).getUsername() == "-1"){
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(FIELD_USERNAME, username);
            values.put(FIELD_PASSWORD, password);
            values.put(FIELD_CELLPHONE, cellphone);
            db.insert(TABLE_NAME, null, values);
            return true;
        }
        return false;
    }

    public boolean tryLogin(String username, String password){
        DatosUsuario datos = findByID(username);
        //System.out.println(datos.getUsername());
        //System.out.println(datos.getPassword() + " aaaa");
        if (username.equals(datos.getUsername()) && password.equals(datos.getPassword())){
            return true;
        }
        return false;
    }

    public void resetDB(){
        SQLiteDatabase db = getWritableDatabase();
        String clearDBQuery = "DELETE FROM " + TABLE_NAME;
        db.execSQL(clearDBQuery);
    }

    public DatosUsuario findByID(String username){

        SQLiteDatabase db = getReadableDatabase();
        String filtrito = FIELD_USERNAME + " = ?";
        String[] args = {username};
        Cursor c = db.query(TABLE_NAME, null, filtrito, args, null, null, null);
        String username2 = "-1";
        String cellphone = "-1";
        String password = "-1";
        if(c.moveToFirst()) {
            username2 = c.getString(c.getColumnIndex(FIELD_USERNAME));
            cellphone = c.getString(c.getColumnIndex(FIELD_CELLPHONE));
            password = c.getString(c.getColumnIndex(FIELD_PASSWORD));
        }
        return new DatosUsuario(username2, cellphone, password);
    }
}
