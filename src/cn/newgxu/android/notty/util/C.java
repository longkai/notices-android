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
package cn.newgxu.android.notty.util;

/**
 * 应用程序共享常量。
 * @author longkai(龙凯)
 * @email  im.longkai@gmail.com
 * @since  2013-6-5
 */
public final class C {

	public static final String URI = "uri";
	
	public static final String	DOMAIN		= "http://lab.newgxu.cn";
	public static final String	_ID			= "_id";
	public static final String	ID			= "id";
	public static final String	DESC_SORT	= " _id DESC";
	public static final String	ASC_SORT	= " _id ASC";

	public static final String	NOTICES		= "notices";
	public static final String	NOTICE		= "notice";
	public static final String	USERS		= "users";
	public static final String	USER		= "user";
	public static final String	AUTHORITY	= "cn.newgxu.android.notty.provider";
	public static final String	BASE_URI	= "content://" + AUTHORITY + "/";
	
	public static final class notice {
		public static final String		TITLE				= "title";
		public static final String		CONTENT				= "content";
		public static final String		CLICK_TIMES			= "click_times";
		public static final String		ADDED_DATE			= "add_date";
		public static final String		LAST_MODIFIED_DATE	= "last_modified_date";
		public static final String		DOC_URL				= "doc_url";
		public static final String		DOC_NAME			= "doc_name";
		public static final String		USER_ID				= "uid";
		public static final String		USER_NAME			= "username";
		
		public static final String[] NOTTY_COLUMNS = {_ID, ADDED_DATE, CLICK_TIMES, DOC_NAME, TITLE, USER_ID, USER_NAME};
		public static final String[] LATEST_NOTICE_ID = {"max(" + _ID + ")"};
	}
	
	public static final class user {
		public static final String	ORG			= "org";
		public static final String	ABOUT		= "about";
		public static final String	CONTACT		= "contact";
		public static final String	AUTHED_NAME	= "authed_name";
		public static final String	JOIN_TIME	= "join_time";
		
		public static final String[] USER_ROW = {_ID, AUTHED_NAME};
	}
	
}
