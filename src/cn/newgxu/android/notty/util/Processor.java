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

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;

/**
 * RESTClient JSON processor.
 * @author longkai(龙凯)
 * @email  im.longkai@gmail.com
 * @since  2013-6-5
 */
public class Processor {

	public static ContentValues json2Notice(JSONObject n) {
		ContentValues v = new ContentValues();
		try {
			v.put(C._ID, n.getLong(C.ID));
			v.put(C.notice.ADDED_DATE, n.getLong(C.notice.ADDED_DATE));
			v.put(C.notice.CLICK_TIMES, n.getInt(C.notice.CLICK_TIMES));
			v.put(C.notice.CONTENT, n.getString(C.notice.CONTENT));
			v.put(C.notice.DOC_NAME, n.getString(C.notice.DOC_NAME));
			v.put(C.notice.DOC_URL, n.getString(C.notice.DOC_URL));
			v.put(C.notice.LAST_MODIFIED_DATE, n.getLong(C.notice.LAST_MODIFIED_DATE));
			v.put(C.notice.TITLE, n.getString(C.notice.TITLE));
			
			JSONObject u = n.getJSONObject(C.USER);
			v.put(C.notice.USER_ID, u.getLong(C.ID));
			v.put(C.notice.USER_NAME, u.getString(C.user.AUTHED_NAME));
		} catch (JSONException e) {
			throw new RuntimeException("resolve json wrong -> " + n);
		}
		return v;
	}
	
	public static ContentValues json2User(JSONObject u) {
		ContentValues v = new ContentValues();
		try {
			v.put(C._ID, u.getLong(C.ID));
			v.put(C.user.ABOUT, u.getString(C.user.ABOUT));
			v.put(C.user.AUTHED_NAME, u.getString(C.user.AUTHED_NAME));
			v.put(C.user.CONTACT, u.getString(C.user.CONTACT));
			v.put(C.user.JOIN_TIME, u.getLong(C.user.JOIN_TIME));
			v.put(C.user.ORG, u.getString(C.user.ORG));
		} catch (JSONException e) {
			throw new RuntimeException("resolve json wrong -> " + u);
		}
		return v;
	}
	
}
