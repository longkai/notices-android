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

import cn.longkai.android.util.AppUtils;
import cn.newgxu.android.notty.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.SpannableString;
import android.text.util.Linkify;
import android.view.View;
import android.widget.TextView;

/**
 * 关于。
 * @author longkai(龙凯)
 * @email  im.longkai@gmail.com
 * @since  2013-6-8
 */
public class AboutBoxDialogFragment extends DialogFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		View v = getActivity().getLayoutInflater().inflate(R.layout.about, null);
		TextView tv = (TextView) v.findViewById(android.R.id.text1);
		SpannableString aboutTxt = new SpannableString(getString(R.string.version) + ": "
				+ AppUtils.version(getActivity()) + "\n" + getString(R.string.about_text));
		tv.setText(aboutTxt);
		Linkify.addLinks(tv, Linkify.ALL);
		return new AlertDialog.Builder(getActivity())
			.setTitle(getString(R.string.about))
			.setCancelable(true)
			.setIcon(R.drawable.ic_launcher)
			.setPositiveButton(R.string.ok, null)
			.setView(v)
			.create();
	}
	
}
