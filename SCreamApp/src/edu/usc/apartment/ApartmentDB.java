package edu.usc.apartment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ApartmentDB extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "apartmentDB";

	private static final int DATABASE_VERSION = 13;
	
	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table apartmentDB (_id integer primary key autoincrement, "
			+ "latitude text not null, longitude text not null, description text not null, rent text not null, size text not null);";

	public ApartmentDB(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Method is called during creation of the database
	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	// Method is called during an upgrade of the database, e.g. if you increase
	// the database version
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		Log.w(ApartmentDB.class.getName(),"Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS apartmentDB");
		onCreate(database);
	}

}
