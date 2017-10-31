package com.sabbir.personal_s_diary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseClass
{
	public static final String KEY_ROWID = "_id";
	public static final String KEY_NAME = "persons_name";
	public static final String KEY_HOTNESS = "persons_hotness";
	
	private static final String DATABASE_NAME2 = "HotOrNotdb2";
	private static final String DATABASE_TABLE2 = "peopleTable2";
	private static final int DATABASE_VERSION = 2;
	
	private DbHelper ourHelper;
	private final Context ourContext;
	private SQLiteDatabase ourDatabase;
	
	private static class DbHelper extends SQLiteOpenHelper {

		public DbHelper(Context context) {
			super(context, DATABASE_NAME2, null, DATABASE_VERSION);
		}

		public void onCreate(SQLiteDatabase db) {
			//try {
				db.execSQL("CREATE TABLE " + DATABASE_TABLE2 + " (" +
						KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
						KEY_NAME + " TEXT NOT NULL, " +
						KEY_HOTNESS + " TEXT NOT NULL);"
				);
			//} catch (SQLException e){
			//	e.printStackTrace();
			//}

		}

		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			//try {
				//droping table
				db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE2);

				//creating table (or database!)
				onCreate(db);
			//} catch (SQLException e) {
			//	e.printStackTrace();
			//}
		}		
	}
	
	public DatabaseClass(Context c)
	{
		ourContext = c;
	}
	
	public DatabaseClass open() throws SQLException
	{
		ourHelper = new DbHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
	}

	public void close()
	{
		ourHelper.close();
	}

	public long createEntry(String name, String description)
	{
		ContentValues cv = new ContentValues();
		cv.put(KEY_NAME, name);
		cv.put(KEY_HOTNESS, description);
		return ourDatabase.insert(DATABASE_TABLE2, null, cv);
	}

//	This method is collecting the data from the database table and serving them to the caller
	public String getData()
	{
		String[] columns = new String[]{ KEY_ROWID, KEY_NAME, KEY_HOTNESS};
		Cursor c = ourDatabase.query(DATABASE_TABLE2, columns, null, null, null, null, null);
		String result = "";
		
		int iRow = c.getColumnIndex(KEY_ROWID);
		int iName = c.getColumnIndex(KEY_NAME);
		int iDescription = c.getColumnIndex(KEY_HOTNESS);
		
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
		{
			result = result + "\n(" + c.getString(iRow) + ")...\t" + c.getString(iName) + "  ...\t" + c.getString(iDescription) + "\n";
		}
		
		return result;
	}

	public String getName(long l)
	{
		String[] columns = new String[]{ KEY_ROWID, KEY_NAME, KEY_HOTNESS};
		Cursor c = ourDatabase.query(DATABASE_TABLE2, columns, KEY_ROWID + "=" + l, null, null, null, null);
		
		if (c != null)
		{
			c.moveToFirst();
			String name = c.getString(1);
			return name;
		}		
		return null;
	}

	public String getDescription(long l)
	{
		String[] columns = new String[]{ KEY_ROWID, KEY_NAME, KEY_HOTNESS};
		Cursor c = ourDatabase.query(DATABASE_TABLE2, columns, KEY_ROWID + "=" + l, null, null, null, null);
		
		if (c != null)
		{
			c.moveToFirst();
			String hotness = c.getString(2);
			//return "=" + hotness;
			return hotness;
		}		
		return null;
	}

	
	public void updateEntry(long lRow, String mName, String mHotness)
	{
		ContentValues cvUpdate = new ContentValues();
		cvUpdate.put(KEY_NAME, mName);
		cvUpdate.put(KEY_HOTNESS, mHotness);
		ourDatabase.update(DATABASE_TABLE2, cvUpdate, KEY_ROWID + "=" + lRow, null);
		
	}

	public void deleteEntry(long lRow1)
	{
		ourDatabase.delete(DATABASE_TABLE2, KEY_ROWID + "=" + lRow1, null);
	}
}
