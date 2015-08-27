package com.example.ele_me.util;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

/**
 * ScrollView反彈效果的實現
 */
public class BounceScrollView extends ScrollView {
	private View inner;// 孩子View

	private float y;// 點擊時y坐標

	private Rect normal = new Rect();// 矩形(這裡只是個形式，只是用於判斷是否需要動畫.)

	private boolean isCount = false;// 是否開始計算

	public BounceScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/***
	 * 根據 XML 生成視圖工作完成.該函數在生成視圖的最後調用，在所有子視圖添加完之後. 即使子類覆蓋了 onFinishInflate
	 * 方法，也應該調用父類的方法，使該方法得以執行.
	 */
	@Override
	protected void onFinishInflate() {
		if (getChildCount() > 0) {
			inner = getChildAt(0);
		}
	}

	/***
	 * 監聽touch
	 */
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (inner != null) {
			commOnTouchEvent(ev);
		}

		return super.onTouchEvent(ev);
	}

	/***
	 * 觸摸事件
	 * 
	 * @param ev
	 */
	public void commOnTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			break;
		case MotionEvent.ACTION_UP:
			// 手指鬆開.
			if (isNeedAnimation()) {
				animation();
				isCount = false;
			}
			break;
		/***
		 * 排除出第一次移動計算，因為第一次無法得知y坐標， 在MotionEvent.ACTION_DOWN中獲取不到，
		 * 因為此時是MyScrollView的touch事件傳遞到到了LIstView的孩子item上面.所以從第二次計算開始.
		 * 然而我們也要進行初始化，就是第一次移動的時候讓滑動距離歸0. 之後記錄準確了就正常執行.
		 */
		case MotionEvent.ACTION_MOVE:
			final float preY = y;// 按下時的y坐標
			float nowY = ev.getY();// 時時y坐標
			int deltaY = (int) (preY - nowY);// 滑動距離
			if (!isCount) {
				deltaY = 0; // 在這裡要歸0.
			}

			y = nowY;
			// 當滾動到最上或者最下時就不會再滾動，這時移動布局
			if (isNeedMove()) {
				// 初始化頭部矩形
				if (normal.isEmpty()) {
					// 保存正常的布局位置
					normal.set(inner.getLeft(), inner.getTop(),
							inner.getRight(), inner.getBottom());
				}
//				Log.e("jj", "矩形：" + inner.getLeft() + "," + inner.getTop()
//						+ "," + inner.getRight() + "," + inner.getBottom());
				// 移動布局
				inner.layout(inner.getLeft(), inner.getTop() - deltaY / 2,
						inner.getRight(), inner.getBottom() - deltaY / 2);
			}
			isCount = true;
			break;

		default:
			break;
		}
	}

	/***
	 * 回縮動畫
	 */
	public void animation() {
		// 開啟移動動畫
		TranslateAnimation ta = new TranslateAnimation(0, 0, inner.getTop(),
				normal.top);
		ta.setDuration(200);
		inner.startAnimation(ta);
		// 設置回到正常的布局位置
		inner.layout(normal.left, normal.top, normal.right, normal.bottom);

//		Log.e("jj", "回歸：" + normal.left + "," + normal.top + "," + normal.right
//				+ "," + normal.bottom);

		normal.setEmpty();

	}

	// 是否需要開啟動畫
	public boolean isNeedAnimation() {
		return !normal.isEmpty();
	}

	/***
	 * 是否需要移動布局 inner.getMeasuredHeight():獲取的是控件的總高度
	 * 
	 * getHeight()：獲取的是屏幕的高度
	 * 
	 * @return
	 */
	public boolean isNeedMove() {
		int offset = inner.getMeasuredHeight() - getHeight();
		int scrollY = getScrollY();
//		Log.e("jj", "scrolly=" + scrollY);
		// 0是頂部，後面那個是底部
		if (scrollY == 0 || scrollY == offset) {
			return true;
		}
		return false;
	}

}
