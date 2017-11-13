package com.example.seenu.vts;

import java.sql.Date;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SA_DatabaseHandler extends SQLiteOpenHelper {
	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;
	// Database Name
	private static final String DATABASE_NAME = "STAY_ADMIN";
	// All table name
	private static final String TABLE_LOCSUMMARY = "LOCATIONSUMMARY";
	
	/* Column Value */
	private static final String KEY_SLNO = "SLNO";
	private static final String KEY_VEHNO = "VEHICLENO";
	private static final String KEY_STATUS = "STATUS";
	private static final String KEY_SPEED = "SPEED";
	private static final String KEY_LOCNAME = "LOCNAME";
	private static final String KEY_DATE = "LOCDATE";
	private static final String KEY_TIME = "LOCTIME";
	private static final String KEY_STATUSFLAG = "STATUSFLAG";
	
	public SA_DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		/* CREATE ACCESSPOINT TABLE */
		String CREATE_LOCSUMMARY_TABLE = "CREATE TABLE IF NOT EXISTS "
				+ TABLE_LOCSUMMARY + "(" +
				KEY_SLNO + " TEXT," +
				KEY_VEHNO + " TEXT," + 
				KEY_STATUS + " TEXT," + 
				KEY_SPEED + " TEXT," + 
				KEY_LOCNAME + " TEXT," + 
				KEY_DATE + " TEXT," + 
				KEY_TIME + " TEXT," +
				KEY_STATUSFLAG + " TEXT" + ")";
		db.execSQL(CREATE_LOCSUMMARY_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		// db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

		// Create tables again
		// onCreate(db);
	}

	// Adding new Location Summary
	public void addLocationSummary(LocSummary locSummary, String flag,
			String whereColName, String whereColValue) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_SLNO, locSummary.getSlNo());
		values.put(KEY_VEHNO, locSummary.getVehNo());
		values.put(KEY_STATUS, locSummary.getStatus());
		values.put(KEY_SPEED, locSummary.getSpeed());
		values.put(KEY_LOCNAME, locSummary.getLocName());
		values.put(KEY_DATE, locSummary.getLocDate());
		values.put(KEY_TIME, locSummary.getLocTime());
		values.put(KEY_STATUSFLAG, locSummary.getStatusFlag());
		if (flag.equalsIgnoreCase("Insert")) {
			// Inserting Row
			db.insert(TABLE_LOCSUMMARY, null, values);
		} else if (flag.equalsIgnoreCase("Update")) {
			// Update the Record
			db.update(TABLE_LOCSUMMARY, values, whereColName + "='"
					+ whereColValue + "'", null);
		}
		db.close(); // Closing database connection
	}
	
	//Retrieve Data From Location Summary
	public Cursor getLocSummary(String whrColValue)
	{
	     Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_LOCSUMMARY + " WHERE " + KEY_STATUSFLAG + " = " + whrColValue, null);
	     return cursor;
	}

	/* Get Table Record Count */
	public int getRecordCount(String tableName) {
		String countQuery = "SELECT * FROM " + tableName;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int recCount = cursor.getCount();
		cursor.close();
		db.close();
		return recCount;
	}

	public int getRecordCount(String tableName, String colName, int colValue) {
		String countQuery = "SELECT * FROM " + tableName + " WHERE " + colName
				+ "=" + colValue;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int recCount = cursor.getCount();
		cursor.close();
		db.close();
		return recCount;
	}

	public String getSingleRecord(String tableName, String colName,
			String whereColName, String recValue) {
		String userData = "";
		try {
			Cursor UserDataCursor = getReadableDatabase().rawQuery(
					"SELECT " + colName + " FROM " + tableName + " WHERE "
							+ whereColName + "='" + recValue + "'", null);
			if (UserDataCursor != null) {
				if (UserDataCursor.moveToFirst()) {
					do {
						userData = UserDataCursor.getString(UserDataCursor
								.getColumnIndex(colName));
					} while (UserDataCursor.moveToNext());
				}
			}

		} catch (SQLiteException se) {
			Log.e("DB_getSingleRecord()",
					"Exception~!~***~!~" + se.getMessage());
			// Log.e(getClass().getSimpleName(),
			// "Could not create or Open the database");
		}
		return userData;
	}

	public String getSingleRecord(String tableName, String colName) {
		String userData = "";
		try {
			Cursor UserDataCursor = getReadableDatabase().rawQuery(
					"SELECT " + colName + " FROM " + tableName, null);
			if (UserDataCursor != null) {
				if (UserDataCursor.moveToFirst()) {
					do {
						userData = UserDataCursor.getString(UserDataCursor
								.getColumnIndex(colName));
					} while (UserDataCursor.moveToNext());
				}
			}
		} catch (SQLiteException se) {
			// Log.e(getClass().getSimpleName(),
			// "Could not create or Open the database");
			Log.e("DB_getSingleRecord", "Exception" + se.getMessage());
		}
		return userData;
	}

	/* Delete Medical Profile */
	public void deleteLocSummary() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_LOCSUMMARY, null, null);
		db.close();
	}

}
