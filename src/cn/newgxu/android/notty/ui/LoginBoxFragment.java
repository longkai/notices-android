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

import cn.longkai.android.util.StyleUtils;
import cn.newgxu.android.notty.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;

/**
 * 用户登录框。
 * @author longkai(龙凯)
 * @email  im.longkai@gmail.com
 * @since  2013-6-7
 */
public class LoginBoxFragment extends DialogFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
//		AlertDialog.Builder builder = null;
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//			builder = new AlertDialog.Builder(getActivity(), R.style.AppTheme_Dark)
//				.setIcon(R.drawable.social_person_dark);
//		} else {
//			builder = new AlertDialog.Builder(getActivity())
//				.setIcon(R.drawable.social_person_light);
//		}
		View v = LayoutInflater.from(getActivity()).inflate(R.layout.login, null);
//		return builder.setView(v)
//				.create();
		return new AlertDialog.Builder(getActivity())
				.setCancelable(false)
				.setTitle(R.string.login_box)
				.setIcon(StyleUtils.getThemeDependentAttr(getActivity().getTheme(), R.attr.user_icon))
				.setView(v)
				.setPositiveButton(R.string.ok, null)
				.setNegativeButton(R.string.no, null)
				.create();
	}
	
}
