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

import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import cn.longkai.android.util.RESTMethod;
import cn.newgxu.android.notty.NottyApplication;
import cn.newgxu.android.notty.R;
import cn.newgxu.android.notty.activity.UserServiceActivity;
import cn.newgxu.android.notty.util.C;

/**
 * 用户服务界面片段。
 * @author longkai(龙凯)
 * @email  im.longkai@gmail.com
 * @since  2013-6-7
 */
public class ServiceItems extends Fragment implements OnClickListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.service_items, container, false);
		SharedPreferences prefs = NottyApplication.getApp().getPrefs();
		Button login = (Button) v.findViewById(R.id.login);
		View gotoAuth = v.findViewById(R.id.go_to_auth);
		if (prefs.getLong(C._ID, 0) != 0) {
			login.setText(R.string.go_to_service);
			gotoAuth.setVisibility(View.GONE);
		} else {
			gotoAuth.setOnClickListener(this);
		}
		login.setOnClickListener(this);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.go_to_auth:
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri.parse(getString(R.string.auth_uri)));
			startActivity(intent);
			break;
		case R.id.login:
			NottyApplication app = NottyApplication.getApp();
			SharedPreferences prefs = app.getPrefs();
			if (prefs.contains(C.ACCOUNT) && prefs.contains(C.PWD)) {
				startActivity(new Intent(getActivity(), UserServiceActivity.class));
//				final String account = prefs.getString(C.ACCOUNT, null);
//				final String pwd = prefs.getString(C.PWD, null);
//				new Handler().post(new Runnable() {
//					
//					@Override
//					public void run() {
//						JSONObject result = RESTMethod.get(C.DOMAIN
//								+ "/info/login?account=" + account + "&pwd=" + pwd);
//						try {
//							if (result.getInt(C.STATUS) == C.OK) {
//								Intent intent = new Intent(getActivity(), UserServiceActivity.class);
//								startActivity(intent);
//							} else {
//								Toast.makeText(getActivity(), getString(R.string.login_no)
//										+ result.getString(C.MSG), Toast.LENGTH_SHORT).show();
//							}
//						} catch (Exception e) {
//							Toast.makeText(getActivity(), getString(R.string.login_error), Toast.LENGTH_SHORT).show();
//						}
//					}
//				});
			} else {
				LoginBoxFragment fragment = new LoginBoxFragment();
				fragment.show(getFragmentManager(), "login");
			}
			break;
		default:
			break;
		}
	}
	
}
