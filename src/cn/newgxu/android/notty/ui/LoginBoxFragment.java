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

import org.json.JSONException;
import org.json.JSONObject;

import cn.longkai.android.util.L;
import cn.longkai.android.util.RESTMethod;
import cn.longkai.android.util.StyleUtils;
import cn.newgxu.android.notty.MainActivity;
import cn.newgxu.android.notty.NottyApplication;
import cn.newgxu.android.notty.R;
import cn.newgxu.android.notty.util.C;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 用户登录框。
 * @author longkai(龙凯)
 * @email  im.longkai@gmail.com
 * @since  2013-6-7
 */
public class LoginBoxFragment extends DialogFragment {

	private static final String TAG = LoginBoxFragment.class.getSimpleName();
	
	ProgressDialog progressDialog;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		View v = LayoutInflater.from(getActivity()).inflate(R.layout.login, null);
		final EditText account = (EditText) v.findViewById(R.id.account);
		final EditText pwd = (EditText) v.findViewById(R.id.password);
		return new AlertDialog.Builder(getActivity())
				.setCancelable(false)
				.setTitle(R.string.login_box)
				.setIcon(StyleUtils.getThemeDependentAttr(getActivity().getTheme(), R.attr.user_icon))
				.setView(v)
				.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						final String _account = account.getEditableText().toString();
						final String _pwd = pwd.getEditableText().toString();
						progressDialog = ProgressDialog.show(getActivity(), "login...", "login...");
						new Handler().post(new Runnable() {
							@Override
							public void run() {
								try {
									JSONObject result = RESTMethod.get(C.DOMAIN
											+ "/info/login?account=" + _account + "&pwd=" + _pwd);
									if (result.getInt(C.STATUS) == C.OK) {
//										put the account and pwd to the app' s context.
										SharedPreferences.Editor editor = NottyApplication.getApp().getPrefs().edit();
										editor.putString(C.ACCOUNT, _account);
										editor.putString(C.PWD, _pwd);
										editor.commit();
										Toast.makeText(getActivity(), R.string.login_ok, Toast.LENGTH_SHORT).show();
										Intent intent = new Intent(Intent.ACTION_VIEW);
										intent.setData(Uri.parse("http://lab.newgxu.cn"));
										startActivity(intent);
									} else {
										Toast.makeText(getActivity(), getString(R.string.login_no) + ": "
												+ result.getString(C.MSG), Toast.LENGTH_SHORT).show();
									}
								} catch (Exception e) {
									L.wtf(TAG, "error when try to login in the thread.", e);
								}
							}
						});
						progressDialog.dismiss();
						
//						new Thread(new Runnable() {
//							@Override
//							public void run() {
//								try {
//									Thread.sleep(3000);
//									JSONObject result = RESTMethod.get(C.DOMAIN
//											+ "/info/login?account=" + _account + "&pwd=" + _pwd);
//									if (result.getInt(C.STATUS) == C.OK) {
////										put the account and pwd to the app' s context.
//										SharedPreferences.Editor editor = NottyApplication.getApp().getPrefs().edit();
//										editor.putString(C.ACCOUNT, _account);
//										editor.putString(C.PWD, _pwd);
//										editor.commit();
//										Toast.makeText(getActivity(), R.string.login_ok, Toast.LENGTH_SHORT).show();
//									} else {
//										Toast.makeText(getActivity(), R.string.login_no + ": "
//												+ result.getString(C.MSG), Toast.LENGTH_SHORT).show();
//									}
//								} catch (Exception e) {
//									L.wtf(TAG, "error when try to login in the thread.", e);
//								} finally {
//									progressDialog.dismiss();
//								}
//							}
//						}).start();
					}
				})
				.setNegativeButton(R.string.no, null)
				.create();
	}
	
}
