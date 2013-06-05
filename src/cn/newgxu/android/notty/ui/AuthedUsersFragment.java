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

import android.app.AlertDialog;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import cn.longkai.android.util.StyleUtils;
import cn.newgxu.android.notty.R;
import cn.newgxu.android.notty.adapter.UsersAdapter;
import cn.newgxu.android.notty.util.C;

/**
 * 认证用户列表界面片段。
 * @author longkai(龙凯)
 * @email  im.longkai@gmail.com
 * @since  2013-6-5
 */
public class AuthedUsersFragment extends ListFragment implements LoaderCallbacks<Cursor> {

	private CursorAdapter mAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		mAdapter = new UsersAdapter(getActivity());
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setListAdapter(mAdapter);
		getLoaderManager().initLoader(0, null, this);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Cursor c = getActivity().getContentResolver().query(Uri.parse(C.BASE_URI + C.USERS + "/" + id), null, null, null, null);
		if (c.moveToNext()) {
			long joinTime = c.getLong(c.getColumnIndex(C.user.JOIN_TIME));
			String[] items = {
					getString(R.string.org, c.getString(c.getColumnIndex(C.user.ORG))),
					getString(R.string.contact, c.getString(c.getColumnIndex(C.user.CONTACT))),
					getString(R.string.join_time, DateUtils.getRelativeTimeSpanString(joinTime)),
					getString(R.string.about, c.getString(c.getColumnIndex(C.user.ABOUT)))};
			new AlertDialog.Builder(getActivity())
				.setIcon(StyleUtils.getThemeDependentAttr(getActivity().getTheme(), R.attr.user_icon))
				.setTitle(c.getString(c.getColumnIndex(C.user.AUTHED_NAME)))
				.setItems(items, null)
				.setNeutralButton(R.string.close, null)
				.create().show();
		} else {
			Toast.makeText(getActivity(), R.string.no_such_man, Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		return new CursorLoader(getActivity(), Uri.parse(C.BASE_URI + C.USERS), C.user.USER_ROW, null, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		mAdapter.swapCursor(data);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		mAdapter.swapCursor(null);
	}

}
