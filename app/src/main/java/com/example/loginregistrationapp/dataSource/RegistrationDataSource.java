package com.example.loginregistrationapp.dataSource;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.loginregistrationapp.dataObject.RegistrationDataModel;

public class RegistrationDataSource extends MyDatabaseHandler {

    public static final String TABLE_NAME_REGISTRATION = "tbl_registration";
    public static final String COLUMN_USER_ID = "usr_id";
    public static final String COLUMN_USER_FULL_NAME = "usr_fullname";
    public static final String COLUMN_USER_EMAIL = "usr_email";
    public static final String COLUMN_USER_MOBILE = "usr_mobile";
    public static final String COLUMN_USER_PASSWORD = "reg_password";


    public static final String CREATE_TABLE_REGISTRATION = "CREATE TABLE "
            + TABLE_NAME_REGISTRATION + "("
            + COLUMN_USER_ID
            + " INTEGER PRIMARY KEY,"
            + COLUMN_USER_FULL_NAME
            + " TEXT,"
            + COLUMN_USER_EMAIL
            + " TEXT,"
            + COLUMN_USER_MOBILE
            + " TEXT,"
            + COLUMN_USER_PASSWORD
            + " TEXT"
            + ")";

    public static String[] allColumns = {
            COLUMN_USER_ID, COLUMN_USER_FULL_NAME, COLUMN_USER_EMAIL,
            COLUMN_USER_MOBILE, COLUMN_USER_PASSWORD
    };

    private SQLiteDatabase db;
    private String TAG = RegistrationDataSource.class.getSimpleName();

    public RegistrationDataSource(Context context) {
        super(context, MyDatabaseHandler.DATABASE_NAME, MyDatabaseHandler.DATABASE_VERSION);
    }

    public void saveToDatabase(RegistrationDataModel registrationDataModel) {
        Log.e("MyClass", "saveToDatabase: " + registrationDataModel.toString());
        db = getDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USER_FULL_NAME, registrationDataModel.getFullname());
        contentValues.put(COLUMN_USER_EMAIL, registrationDataModel.getEmail());
        contentValues.put(COLUMN_USER_MOBILE, registrationDataModel.getMobile());
        contentValues.put(COLUMN_USER_PASSWORD, registrationDataModel.getPassword());

        long insertedRow = db.insert(TABLE_NAME_REGISTRATION, null, contentValues);
        Log.e(TAG, "saveToDatabase: " + insertedRow);
    }

    public Boolean IsValidUser(String email, String password) {
        Log.e(TAG, "IsValidUser: ");
        db = getDatabase();
        String selection = COLUMN_USER_EMAIL + " = ? AND " + COLUMN_USER_PASSWORD + " = ? ";
        String[] selectionArgs = {email, password};
        Cursor cursor = db.query(TABLE_NAME_REGISTRATION, allColumns, selection, selectionArgs, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.close();
            db.close();
            return true;
        }
        cursor.close();
        db.close();
        return false;
    }
}
