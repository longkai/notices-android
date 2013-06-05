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
package cn.newgxu.android.notty;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import cn.longkai.android.util.StrictModeUtils;
import cn.newgxu.android.notty.adapter.NottyPagerAdapter;
import cn.newgxu.android.notty.util.ThemeUtils;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

/**
 * 应用程序主界面。
 * @author longkai(龙凯)
 * @email  im.longkai@gmail.com
 * @since  2013-6-5
 */
public class MainActivity extends SherlockFragmentActivity {

	private static final String TAG = MainActivity.class.getSimpleName();
	
	private FragmentManager fm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StrictModeUtils.useStrictMode(getApplicationContext()); // enable strict
		ThemeUtils.switchTheme(this);
		setContentView(R.layout.main);
		getSupportActionBar().setTitle(R.string.app_title);
		
		fm = getSupportFragmentManager();
		ViewPager pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(new NottyPagerAdapter(fm));
		pager.setCurrentItem(NottyPagerAdapter.LATEST_NOTICES);
		PagerTabStrip pagerTabStrip = (PagerTabStrip) pager.findViewById(R.id.pager_tab_strip);
		pagerTabStrip.setTabIndicatorColor(getResources().getColor(R.color.info));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.dark_theme:
			ThemeUtils.apply(this, ThemeUtils.DARK_THEME);
			break;
		case R.id.light_theme:
			ThemeUtils.apply(this, ThemeUtils.LIGHT_THEME);
			break;
		default:
			break;
		}
		return true;
	}
	
}
