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
package cn.newgxu.android.notty.util;

import cn.newgxu.android.notty.R;
import android.app.Activity;
import android.content.Intent;

/**
 * 与app主题相关，目前只有亮色和暗色两个系统主题。
 * @author longkai(龙凯)
 * @email  im.longkai@gmail.com
 * @since  2013-6-5
 */
public class ThemeUtils {

	/** our app' s current theme */
	private static int		currentTheme;

	/** app' s light theme */
	public static final int	LIGHT_THEME	= 1;
	/** app' s theme */
	public static final int	DARK_THEME	= 2;
	
	public static final void apply(Activity activity, int theme) {
		currentTheme = theme;
		activity.finish();
		activity.startActivity(new Intent(activity, activity.getClass()));
	}
	
	public static final void switchTheme(Activity activity) {
		switch (currentTheme) {
		default:
		case LIGHT_THEME:
			activity.setTheme(R.style.AppTheme);
			break;
		case DARK_THEME:
			activity.setTheme(R.style.AppTheme_Dark);
			break;
		}
	}
	
}
