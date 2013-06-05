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
package cn.newgxu.android.notty.service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.longkai.android.util.L;
import cn.longkai.android.util.NetworkUtils;
import cn.longkai.android.util.RESTMethod;
import cn.newgxu.android.notty.util.C;
import cn.newgxu.android.notty.util.Processor;
import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;

/**
 * 负责抓取云端信息的服务线程。
 * @author longkai(龙凯)
 * @email  im.longkai@gmail.com
 * @since  2013-6-5
 */
public class FetchService extends IntentService {

	private static final String TAG = FetchService.class.getSimpleName();
	
	public FetchService() {
		super(TAG);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		if (NetworkUtils.connected(getApplicationContext())) {
			JSONObject result = RESTMethod.get(intent.getStringExtra(C.URI));
			L.d(TAG, "json: %s", result);
			try {
				JSONArray notices = result.getJSONArray(C.NOTICES);
				ContentValues[] noticeValues = new ContentValues[notices.length()];
				ContentValues[] userValues = new ContentValues[notices.length()];
				for (int i = 0; i < userValues.length; i++) {
					JSONObject n = notices.getJSONObject(i);
					JSONObject u = n.getJSONObject(C.USER);
					noticeValues[i] = Processor.json2Notice(n);
					userValues[i] = Processor.json2User(u);
				}
				String[] uris = intent.getStringArrayExtra("uris");
				getContentResolver().bulkInsert(Uri.parse(uris[0]), noticeValues);
				getContentResolver().bulkInsert(Uri.parse(uris[1]), userValues);
			} catch (JSONException e) {
				throw new RuntimeException("ERROR when resolving json -> " + result);
			} 
		}
	}

}
