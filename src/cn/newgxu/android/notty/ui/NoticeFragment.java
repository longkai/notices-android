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
package cn.newgxu.android.notty.ui;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import cn.newgxu.android.notty.R;
import cn.newgxu.android.notty.util.C;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.github.rjeschke.txtmark.Processor;

/**
 * 查看信息界面片段。
 * 
 * @author longkai(龙凯)
 * @email im.longkai@gmail.com
 * @since 2013-6-6
 */
public class NoticeFragment extends SherlockFragment {

	private ActionBar actionBar;
	private WebView content;
	private Button doc; 
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		actionBar = getSherlockActivity().getSupportActionBar();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.notice, container, false);
		doc = (Button) v.findViewById(R.id.doc);
		content = (WebView) v.findViewById(android.R.id.content);
//		WebSettings settings = content.getSettings();
//		settings.setSupportZoom(true);
//		settings.setBuiltInZoomControls(true);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		new ProvideNoticeContent().execute(getActivity().getIntent().getData());
	}
	
	private class ProvideNoticeContent extends AsyncTask<Uri, Integer, Map<String, CharSequence>> {

		@Override
		protected Map<String, CharSequence> doInBackground(Uri... params) {
			Cursor c = getActivity().getContentResolver()
					.query(params[0], null, null, null, null);
			Map<String, CharSequence> m = null;
			if (c.moveToNext()) {
				m = new HashMap<String, CharSequence>();
				String content = c.getString(c.getColumnIndex(C.notice.CONTENT));
				m.put(C.notice.CONTENT, Processor.process(content));
				m.put(C.notice.ADDED_DATE, DateUtils.getRelativeTimeSpanString(
						c.getLong(c.getColumnIndex(C.notice.ADDED_DATE))));
				m.put(C.notice.CLICK_TIMES, c.getString(c.getColumnIndex(C.notice.CLICK_TIMES)));
				m.put(C.notice.DOC_NAME, c.getString(c.getColumnIndex(C.notice.DOC_NAME)));
				m.put(C.notice.DOC_URL, c.getString(c.getColumnIndex(C.notice.DOC_URL)));
				m.put(C.notice.LAST_MODIFIED_DATE, DateUtils.getRelativeTimeSpanString(
						c.getLong(c.getColumnIndex(C.notice.LAST_MODIFIED_DATE))));
				m.put(C.notice.TITLE, c.getString(c.getColumnIndex(C.notice.TITLE)));
				m.put(C.notice.USER_ID, c.getString(c.getColumnIndex(C.notice.USER_ID)));
				m.put(C.notice.USER_NAME, c.getString(c.getColumnIndex(C.notice.USER_NAME)));
			}
			return m;
		}

		@Override
		protected void onPostExecute(Map<String, CharSequence> result) {
			content.loadDataWithBaseURL("", result.get(C.notice.CONTENT).toString(), "text/html", "utf-8", "");
			actionBar.setTitle(result.get(C.notice.TITLE));
			actionBar.setSubtitle(result.get(C.notice.ADDED_DATE) + "  " + result.get(C.notice.USER_NAME));
			
			final CharSequence docUrl = result.get(C.notice.DOC_URL);
			if (!docUrl.equals("null")) {
				String originalName = result.get(C.notice.DOC_NAME).toString();
				String docName = originalName.equals("null") ? getString(R.string.default_doc_name) : originalName;
				doc.setText(getString(R.string.doc) + docName);
				doc.setVisibility(View.VISIBLE);
				doc.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(Intent.ACTION_VIEW);
						intent.setData(Uri.parse(C.DOMAIN + docUrl.toString()));
						startActivity(intent);
					}
				});
			}
		}

	}
	
}
