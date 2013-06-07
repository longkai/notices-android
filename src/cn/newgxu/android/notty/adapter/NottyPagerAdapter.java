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

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import cn.newgxu.android.notty.ui.AuthedUsersFragment;
import cn.newgxu.android.notty.ui.NoticesFragment;
import cn.newgxu.android.notty.ui.UserServiceFragment;

/**
 * app主界面pager适配器。
 * @author longkai(龙凯)
 * @email  im.longkai@gmail.com
 * @since  2013-6-5
 */
public class NottyPagerAdapter extends FragmentStatePagerAdapter {

	private static final int	PAGER_COUNT		= 3;
	/** 认证用户列表 */
	public static final int		AUTHED_USERS	= 0;
	/** 最新信息列表 */
	public static final int		LATEST_NOTICES	= 1;
	/** 用户自服务 */
	public static final int		USER_SERVICE	= 2;
	
	
	private Fragment[] fragments;
	
	public NottyPagerAdapter(FragmentManager fm) {
		super(fm);
		fragments = new Fragment[PAGER_COUNT];
		Fragment fragment = null;
		for (int i = 0; i < PAGER_COUNT; i++) {
			switch (i) {
			default:
			case AUTHED_USERS:
				fragment = new AuthedUsersFragment();
				break;
			case LATEST_NOTICES:
				fragment = new NoticesFragment();
				break;
			case USER_SERVICE:
				fragment = new UserServiceFragment();
				break;
			}
			fragments[i] = fragment;
		}
	}

	@Override
	public Fragment getItem(int position) {
		return fragments[position];
	}

	@Override
	public int getCount() {
		return PAGER_COUNT;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		String title = null;
		switch (position) {
		case AUTHED_USERS:
			title = "认证列表";
			break;
		default:
		case LATEST_NOTICES:
			title = "最新信息";
			break;
		case USER_SERVICE:
			title = "用户中心";
			break;
		}
		return title;
	}
	
}
