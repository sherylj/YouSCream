package edu.usc.apartment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ApartmentDBAdapter {
	
	// Database fields
	public static final String KEY_ROWID = "_id";
	public static final String KEY_LATITUDE = "latitude";
	public static final String KEY_LONGITUDE = "longitude";
	public static final String KEY_DESCRIPTION = "description";
	public static final String KEY_RENT = "rent";
	public static final String KEY_SIZE = "size";
	private static final String DATABASE_TABLE = "apartmentDB";
	private Context context;
	private SQLiteDatabase database;
	private ApartmentDB dbHelper;

	public ApartmentDBAdapter(Context context) {
		this.context = context;
	}

	public ApartmentDBAdapter open() throws SQLException {
		dbHelper = new ApartmentDB(context);
		database = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		dbHelper.close();
	}
	
	public void update() {
		dbHelper.onUpgrade(database, 13, 13);
	}

	/**
	 * Create a new todo If the todo is successfully created return the new
	 * rowId for that note, otherwise return a -1 to indicate failure.
	 */

	public long createTodo(String latitude, String longitude, String description, String rent, String size) {
		ContentValues initialValues = createContentValues(latitude, longitude, description, rent, size);

		return database.insert(DATABASE_TABLE, null, initialValues);
	}

	/**
	 * Update the todo
	 */

	public boolean updateTodo(long rowId, String latitude, String longitude,String description, String rent, String size) {
		ContentValues updateValues = createContentValues(latitude, longitude,description, rent, size);

		return database.update(DATABASE_TABLE, updateValues, KEY_ROWID + "="+ rowId, null) > 0;
	}

	/**
	 * Deletes todo
	 */

	public boolean deleteTodo(long rowId) {
		return database.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
	}

	/**
	 * Return a Cursor over the list of all todo in the database
	 * 
	 * @return Cursor over all notes
	 */

	public Cursor fetchAllTodos() {
		return database.query(DATABASE_TABLE, new String[] { KEY_ROWID,
				KEY_LATITUDE, KEY_LONGITUDE, KEY_DESCRIPTION, KEY_RENT, KEY_SIZE }, null, null, null,
				null, null);
	}
	

	/**
	 * Return a Cursor positioned at the defined todo
	 */

	public Cursor fetchTodo(long rowId) throws SQLException {
		Cursor mCursor = database.query(true, DATABASE_TABLE, new String[] {
				KEY_ROWID, KEY_LATITUDE, KEY_LONGITUDE, KEY_DESCRIPTION, KEY_RENT, KEY_SIZE },
				KEY_ROWID + "=" + rowId, null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	private ContentValues createContentValues(String category, String summary, String description, String rent, String size) {
		ContentValues values = new ContentValues();
		values.put(KEY_LATITUDE, category);
		values.put(KEY_LONGITUDE, summary);
		values.put(KEY_DESCRIPTION, description);
		values.put(KEY_RENT, rent);
		values.put(KEY_SIZE, size);
		return values;
	}
}
