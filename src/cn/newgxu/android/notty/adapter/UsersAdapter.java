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
package cn.newgxu.android.notty.adapter;

import cn.newgxu.android.notty.util.C;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 认证用户列表适配器。
 * @author longkai(龙凯)
 * @email  im.longkai@gmail.com
 * @since  2013-6-6
 */
public class UsersAdapter extends CursorAdapter {

	public UsersAdapter(Context context) {
		super(context, null, 0);
	}

	private static class ViewHolder {
		TextView authedName;
		int authedNameIndex;
	}
	
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		View v = LayoutInflater.from(context).
				inflate(android.R.layout.simple_list_item_1, null);
		ViewHolder holder = new ViewHolder();
		holder.authedName = (TextView) v.findViewById(android.R.id.text1);
		holder.authedNameIndex = cursor.getColumnIndexOrThrow(C.user.AUTHED_NAME);
		v.setTag(holder);
		return v;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		ViewHolder holder = (ViewHolder) view.getTag();
		holder.authedName.setText(cursor.getString(holder.authedNameIndex));
	}

}
