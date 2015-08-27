package com.example.ele_me.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;

import com.example.ele_me.R;

public class ClearEditTextView extends EditText implements  
        OnFocusChangeListener, TextWatcher { 
	/**
	 * 刪除按鈕的引用
	 */
    private Drawable mClearDrawable; 
    /**
     * 控件是否有焦點
     */
    private boolean hasFoucs;
 
    public ClearEditTextView(Context context) { 
    	this(context, null); 
    } 
 
    public ClearEditTextView(Context context, AttributeSet attrs) { 
    	//這裡構造方法也很重要，不加這個很多屬性不能再XML裡面定義
    	this(context, attrs, android.R.attr.editTextStyle); 
    } 
    
    public ClearEditTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }
    
    
    private void init() { 
    	//獲取EditText的DrawableRight,假如沒有設置我們就使用默認的圖片
    	mClearDrawable = getCompoundDrawables()[2]; 
        if (mClearDrawable == null) { 
//        	throw new NullPointerException("You can add drawableRight attribute in XML");
        	mClearDrawable = getResources().getDrawable(R.drawable.delete_selector); //圖片樣式
        } 
        
        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight()); 
        //默認設置隱藏圖標
        setClearIconVisible(false); 
        //設置焦點改變的監聽
        setOnFocusChangeListener(this); 
        //設置輸入框裡面內容發生改變的監聽
        addTextChangedListener(this); 
    } 
 
 
    /**
     * 因為我們不能直接給EditText設置點擊事件，所以我們用記住我們按下的位置來模擬點擊事件
     * 當我們按下的位置 在  EditText的寬度 - 圖標到控件右邊的間距 - 圖標的寬度  和
     * EditText的寬度 - 圖標到控件右邊的間距之間我們就算點擊了圖標，豎直方向就沒有考慮
     */
    @Override 
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_UP) {
			if (getCompoundDrawables()[2] != null) {

				boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
						&& (event.getX() < ((getWidth() - getPaddingRight())));
				
				if (touchable) {
					this.setText("");
				}
			}
		}

		return super.onTouchEvent(event);
	}
 
    /**
     * 當ClearEditText焦點發生變化的時候，判斷裡面字符串長度設置清除圖標的顯示與隱藏
     */
    @Override 
    public void onFocusChange(View v, boolean hasFocus) { 
    	this.hasFoucs = hasFocus;
        if (hasFocus) { 
            setClearIconVisible(getText().length() > 0); 
        } else { 
            setClearIconVisible(false); 
        } 
    } 
 
 
    /**
     * 設置清除圖標的顯示與隱藏，調用setCompoundDrawables為EditText繪製上去
     * @param visible
     */
    protected void setClearIconVisible(boolean visible) { 
        Drawable right = visible ? mClearDrawable : null; 
        setCompoundDrawables(getCompoundDrawables()[0], 
                getCompoundDrawables()[1], right, getCompoundDrawables()[3]); 
    } 
    
    
    /**
     * 當輸入框裡面內容發生變化的時候回調的方法
     */
    @Override 
    public void onTextChanged(CharSequence s, int start, int count, 
            int after) { 
            	if(hasFoucs){
            		setClearIconVisible(s.length() > 0);
            	}
    } 
 
    @Override 
    public void beforeTextChanged(CharSequence s, int start, int count, 
            int after) { 
         
    } 
 
    @Override 
    public void afterTextChanged(Editable s) { 
         
    } 
    
   
    /**
     * 設置晃動動畫
     */
    public void setShakeAnimation(){
    	this.setAnimation(shakeAnimation(5));
    }
    
    
    /**
     * 晃動動畫
     * @param counts 1秒鐘晃動多少下
     * @return
     */
    public static Animation shakeAnimation(int counts){
    	Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
    	translateAnimation.setInterpolator(new CycleInterpolator(counts));
    	translateAnimation.setDuration(1000);
    	return translateAnimation;
    }
 
 
}
