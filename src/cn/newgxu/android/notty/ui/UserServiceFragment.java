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

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import cn.newgxu.android.notty.NottyApplication;
import cn.newgxu.android.notty.R;
import cn.newgxu.android.notty.activity.NoticeActivity;
import cn.newgxu.android.notty.util.C;

import com.actionbarsherlock.app.SherlockListFragment;

/**
 * 用户服务中心
 * @author longkai(龙凯)
 * @email  im.longkai@gmail.com
 * @since  2013-6-8
 */
public class UserServiceFragment extends SherlockListFragment implements LoaderCallbacks<Cursor> {

	private NoticesAdapter mAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		mAdapter = new NoticesAdapter(getSherlockActivity());
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setListAdapter(mAdapter);
		getLoaderManager().initLoader(0, null, this);
		getSherlockActivity().getSupportActionBar().setSubtitle(R.string.user_notices);
		getSherlockActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent(getActivity(), NoticeActivity.class);
		intent.setData(Uri.parse(C.BASE_URI + C.NOTICES + "/" + id));
		startActivity(intent);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		SharedPreferences prefs = NottyApplication.getApp().getPrefs();
		return new CursorLoader(getActivity(), Uri.parse(C.BASE_URI + C.NOTICES),
				null, C.notice.USER_ID + "=" + prefs.getLong(C._ID, 0), null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		mAdapter.swapCursor(data);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		mAdapter.swapCursor(null);
	}
	
	private static class NoticesAdapter extends CursorAdapter {

		public NoticesAdapter(Context context) {
			super(context, null, 0);
		}

		private static class ViewHolder {
			TextView title;
			int titleIndex;
			TextView addedTime;
			int addedTimeIndex;
		}
		
		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			View v = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2, null);
			ViewHolder holder = new ViewHolder();
			
			holder.title = (TextView) v.findViewById(android.R.id.text1);
			holder.addedTime = (TextView) v.findViewById(android.R.id.text2);
			holder.addedTimeIndex = cursor.getColumnIndex(C.notice.ADDED_DATE);
			holder.titleIndex = cursor.getColumnIndex(C.notice.TITLE);
			
			v.setTag(holder);
			return v;
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			ViewHolder holder = (ViewHolder) view.getTag();
			holder.title.setText(cursor.getString(holder.titleIndex));
			long t = cursor.getLong(holder.addedTimeIndex);
			holder.addedTime.setText(DateUtils.getRelativeTimeSpanString(t));
		}
		
	}

}
