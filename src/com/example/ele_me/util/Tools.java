package com.example.ele_me.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Environment;
import android.view.View;
import android.view.View.MeasureSpec;

/*
 * 系統工具類
 * */
public class Tools {

	// 判斷有沒有sd卡
	public static boolean hasSDCard() {
		String status = Environment.getExternalStorageState();
		if (!status.equals(Environment.MEDIA_MOUNTED)) {
			return false;
		}
		return true;
	}

	// 獲得根目錄路徑
	public static String getRootFilePath() {
		if (hasSDCard()) {
			return Environment.getExternalStorageDirectory().getAbsolutePath()
					+ "/";// filePath:/sdcard/
		} else {
			return Environment.getDataDirectory().getAbsolutePath() + "/data/"; // filePath:
																				// /data/data/
		}
	}


	// 將定義的view裝換成 bitmap格式
	public Bitmap convertViewToBitmap(View view) {
		view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
				MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		view.buildDrawingCache();
		Bitmap bitmap = view.getDrawingCache();
		return bitmap;
	}

	/**
	 * 將px值轉換為dip或dp值，保證尺寸大小不變
	 * 
	 * @param context
	 * @param pxValue
	 * @return
	 */
	public int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 
	 * 將dip或dp值轉換為px值，保證尺寸大小不變
	 * 
	 * @param dipValue
	 * 
	 * @param scale
	 * 
	 * @return
	 */

	public int dip2px(Context context, float dipValue) {

		final float scale = context.getResources().getDisplayMetrics().density;

		return (int) (dipValue * scale + 0.5f);

	}

	/**
	 * 
	 * 將px值轉換為sp值，保證文字大小不變
	 * 
	 * @param pxValue
	 * 
	 * @param fontScale
	 * 
	 * @return
	 */

	public int px2sp(Context context, float pxValue) {

		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;

		return (int) (pxValue / fontScale + 0.5f);

	}

	/**
	 * 
	 * 將px值轉換為sp值，保證文字大小不變
	 * 
	 * @param spValue
	 * 
	 * @param fontScale
	 * 
	 * @return
	 */

	public int sp2px(Context context, float spValue) {

		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;

		return (int) (spValue * fontScale + 0.5f);

	}

	public static boolean hasGingerbread() {
		return Build.VERSION.SDK_INT >= VERSION_CODES.GINGERBREAD;
	}

	public static boolean hasHoneycomb() {
		return Build.VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB;
	}

	/**
	 * 檢查是否存在SDCard
	 * 
	 * @return
	 */
	public static boolean hasSdcard() {
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

}
