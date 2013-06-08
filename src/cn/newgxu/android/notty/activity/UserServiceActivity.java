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
package cn.newgxu.android.notty.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import cn.newgxu.android.notty.NottyApplication;
import cn.newgxu.android.notty.R;
import cn.newgxu.android.notty.ui.CreateNoticeFragment;
import cn.newgxu.android.notty.ui.UserServiceFragment;
import cn.newgxu.android.notty.util.C;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

/**
 * 用户自服务活动。
 * @author longkai(龙凯)
 * @email  im.longkai@gmail.com
 * @since  2013-6-8
 */
public class UserServiceActivity extends SherlockFragmentActivity {

	private FragmentManager fm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(cn.longkai.android.R.layout.fragment_container);
		
		fm = getSupportFragmentManager();
		getSupportActionBar().setTitle(NottyApplication.getApp().getPrefs().getString(C.user.AUTHED_NAME, null));
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		UserServiceFragment fragment = new UserServiceFragment();
		fm.beginTransaction().replace(cn.longkai.android.R.id.fragment_container, fragment).commit();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.service, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Fragment fragment = null;
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		case R.id.create:
//			current, due to the server and the client' s not well equiped,
//			so currentlly, allowing use publish notices from android is not available.
//			if (fm.findFragmentByTag("create") == null) {
//				fragment = new CreateNoticeFragment();
//			}
			Toast.makeText(this, R.string.not_available, Toast.LENGTH_SHORT).show();
			break;
		case R.id.notices:
			if (fm.findFragmentByTag("notices") == null) {
				fragment = new UserServiceFragment();
			}
			break;
		default:
			break;
		}
		if (fragment != null) {
			fm.beginTransaction()
			.replace(cn.longkai.android.R.id.fragment_container, fragment)
			.addToBackStack(null)
			.commit();
		}
		return true;
	}

}
