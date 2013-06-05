/*
 * Copyright 2013 longkai
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package cn.newgxu.android.notty.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.text.TextUtils;
import cn.newgxu.android.notty.R;
import cn.newgxu.android.notty.util.C;

/**
 * 应用程序数据源。
 * @author longkai(龙凯)
 * @email  im.longkai@gmail.com
 * @since  2013-6-5
 */
public class NottyProvider extends ContentProvider {
	
	public static final String SINGLE_RECORD_MIME_TYPE = "vnd.android.cursor.item/vnd." + C.AUTHORITY + ".";
	public static final String MULTIPLE_RECORDS_MIME_TYPE = "vnd.android.cursor.dir/vnd." + C.AUTHORITY + ".";
	

	private static final int	NOTICES						= 0;
	private static final int	NOTICE						= 1;
	private static final int	USERS						= 2;
	private static final int	USER						= 3;
	
	private static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

	static {
		matcher.addURI(C.AUTHORITY, C.NOTICES, NOTICES);
		matcher.addURI(C.AUTHORITY, C.NOTICES + "/#", NOTICE);
		matcher.addURI(C.AUTHORITY, C.USERS, USERS);
		matcher.addURI(C.AUTHORITY, C.USERS + "/#", USER);
	}
	
	private NottyData data;
	
	@Override
	public boolean onCreate() {
		this.data = new NottyData(getContext());
		return true;
	}

	@Override
	public String getType(Uri uri) {
		switch (matcher.match(uri)) {
		case USER:
			return SINGLE_RECORD_MIME_TYPE + C.USERS;
		case USERS:
			return MULTIPLE_RECORDS_MIME_TYPE + C.USERS;
		case NOTICE:
			return SINGLE_RECORD_MIME_TYPE + C.NOTICES;
		case NOTICES:
			return MULTIPLE_RECORDS_MIME_TYPE + C.NOTICES;
		default:
			throw new RuntimeException("WRONG URI with -> " + uri);
		}
	}
	
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteDatabase db = data.getReadableDatabase();
		Cursor cursor = null;
		switch (matcher.match(uri)) {
		case NOTICE:
			selection = " _id = " + uri.getLastPathSegment();
			cursor = db.query(C.NOTICES, projection, selection, selectionArgs, null, null, null);
			break;
		case NOTICES:
			sortOrder = TextUtils.isEmpty(sortOrder) ? C.DESC_SORT : sortOrder;
			cursor = db.query(C.NOTICES, projection, selection, selectionArgs, null, null, sortOrder);
			break;
		case USER:
			selection = " _id = " + uri.getLastPathSegment();
			cursor = db.query(C.USERS, projection, selection, selectionArgs, null, null, null);
			break;
		case USERS:
			sortOrder = TextUtils.isEmpty(sortOrder) ? C.DESC_SORT : sortOrder;
			cursor = db.query(C.USERS, projection, selection, selectionArgs, null, null, sortOrder);
			break;
		default:
			throw new IllegalArgumentException("Sorry, we can' t matche this URI: " + uri);
		}
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = data.getWritableDatabase();
		String table = null;
		switch (matcher.match(uri)) {
		case USERS:
			table = C.USERS;
			break;
		case NOTICES:
			table = C.NOTICES;
			break;
		default:
			throw new IllegalArgumentException("Sorry, we can' t matche this URI: " + uri);
		}
		long id = db.insertWithOnConflict(table, null, values, SQLiteDatabase.CONFLICT_REPLACE);
		getContext().getContentResolver().notifyChange(uri, null, false); // currently, we don' t need sync adapter.
		return ContentUris.withAppendedId(uri, id);
	}
	
	@Override
	public int bulkInsert(Uri uri, ContentValues[] values) {
		SQLiteDatabase db = data.getWritableDatabase();
		String table = null;
		db.beginTransaction();
		switch (matcher.match(uri)) {
		case NOTICES:
			table = C.NOTICES;
			break;
		case USERS:
			table = C.USERS;
			break;
		default:
			throw new IllegalArgumentException("Sorry, we can' t matche this URI: " + uri);
		}
		for (int i = 0; i < values.length; i++) {
			db.insertWithOnConflict(table, null, values[i], SQLiteDatabase.CONFLICT_REPLACE);
		}
		db.setTransactionSuccessful();
		db.endTransaction();
		getContext().getContentResolver().notifyChange(uri, null, false);
		return values.length;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/**
	 * sqlite数据库
	 */
	private class NottyData extends SQLiteOpenHelper {
		
		public NottyData(Context context) {
			super(context,
					context.getResources().getString(R.string.db_name),
					null,
					context.getResources().getInteger(R.integer.db_version));
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			StringBuilder sql = new StringBuilder("CREATE TABLE ");
			sql.append(C.NOTICES).append(" (")
				.append(C._ID).append(" integer primary key, ")
				.append(C.notice.ADDED_DATE).append(" varchar(30), ")
				.append(C.notice.CLICK_TIMES).append(" int, ")
				.append(C.notice.CONTENT).append(" text, ")
				.append(C.notice.DOC_NAME).append(" varchar(30), ")
				.append(C.notice.DOC_URL).append(" varchar(255), ")
				.append(C.notice.LAST_MODIFIED_DATE).append(" varchar(20), ")
				.append(C.notice.TITLE).append(" varchar(30), ")
				.append(C.notice.USER_ID).append(" int, ")
				.append(C.notice.USER_NAME).append(" varchar(30))");
			db.execSQL(sql.toString());
			
			sql.setLength(0);
			sql.append("CREATE TABLE ").append(C.USERS).append(" (")
				.append(C._ID).append(" integer primary key, ")
				.append(C.user.ABOUT).append(" text, ")
				.append(C.user.AUTHED_NAME).append(" varchar(50), ")
				.append(C.user.CONTACT).append(" varchar(50), ")
				.append(C.user.JOIN_TIME).append(" varchar(20), ")
				.append(C.user.ORG).append(" varchar(30))");
			db.execSQL(sql.toString());
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + C.NOTICES);
			db.execSQL("DROP TABLE IF EXISTS " + C.USERS);
			onCreate(db);
		}
		
	}

}
