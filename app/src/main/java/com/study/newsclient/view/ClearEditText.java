package com.study.newsclient.view;


import android.annotation.SuppressLint;
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
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.study.newsclient.R;


/** 带清除功能的文本输入框
 * @ClassName: ClearEditText
 * @Description: TODO
 * @date 2014-5-4 下午4:18:45
 */
@SuppressLint("AppCompatCustomView")
public class ClearEditText extends EditText implements
        OnFocusChangeListener, TextWatcher {
	/**
	 * 删除按钮的引用
	 */
   private Drawable mClearDrawable;
   
   private OnEditorInputListener inputListener;

   public ClearEditText(Context context) {
   	this(context, null); 
   } 

   public ClearEditText(Context context, AttributeSet attrs) {
   	//这里构造方法也很重要，不加这个很多属性不能再XML里面定义
   	this(context, attrs, android.R.attr.editTextStyle); 
   } 
   
   public ClearEditText(Context context, AttributeSet attrs, int defStyle) {
       super(context, attrs, defStyle);
       init();
   }
   
   public interface OnEditorInputListener {
       /**
        * Called when an action is being performed.
        *
        * identifier you supplied, or {@link EditorInfo#IME_NULL
        * EditorInfo.IME_NULL} if being called due to the enter key
        * being pressed.
        * otherwise, this is null.
        * @return Return true if you have consumed the action, else false.
        */
       boolean onEditorInput();
   }
   
   public void setOnEditorInputListener(OnEditorInputListener l) {

	   inputListener = l;
   }
   
   private void init() { 
   	//获取EditText的DrawableRight,假如没有设置我们就使用默认的图片
   	mClearDrawable = getCompoundDrawables()[2]; 
       if (mClearDrawable == null) { 
       	mClearDrawable = getResources().getDrawable(R.drawable.ic_search_clear);
       }

//       mClearDrawable.setBounds(-60, -0, mClearDrawable.getIntrinsicWidth()-60, mClearDrawable.getIntrinsicHeight());
       mClearDrawable.setBounds(10, -0, mClearDrawable.getIntrinsicWidth()+10, mClearDrawable.getIntrinsicHeight());
       setClearIconVisible(false); 
       setOnFocusChangeListener(this); 
       addTextChangedListener(this); 
   } 


   /**
    * 因为我们不能直接给EditText设置点击事件，所以我们用记住我们按下的位置来模拟点击事件
    * 当我们按下的位置 在  EditText的宽度 - 图标到控件右边的间距 - 图标的宽度  和
    * EditText的宽度 - 图标到控件右边的间距之间我们就算点击了图标，竖直方向没有考虑
    */
   @Override
   public boolean onTouchEvent(MotionEvent event) {
       if (getCompoundDrawables()[2] != null) { 
           if (event.getAction() == MotionEvent.ACTION_UP) {
           	boolean touchable = event.getX() > (getWidth() 
                       - getPaddingRight() - mClearDrawable.getIntrinsicWidth()-100) 
                       && (event.getX() < ((getWidth() - getPaddingRight())+40));
               if (touchable) { 
                   this.setText(""); 
               } 
           } 
       } 

       return super.onTouchEvent(event); 
   } 

   /**
    * 当ClearEditText焦点发生变化的时候，判断里面字符串长度设置清除图标的显示与隐藏
    */
   @Override
   public void onFocusChange(View v, boolean hasFocus) {
       if (hasFocus) { 
           setClearIconVisible(getText().length() > 0); 
       } else { 
           setClearIconVisible(false); 
       } 
   } 


   /**
    * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
    * @param visible
    */
   protected void setClearIconVisible(boolean visible) { 
       Drawable right = visible ? mClearDrawable : null;
       setCompoundDrawables(getCompoundDrawables()[0], 
               getCompoundDrawables()[1], right, getCompoundDrawables()[3]); 
   } 
    
   
   /**
    * 当输入框里面内容发生变化的时候回调的方法
    */
   @Override
   public void onTextChanged(CharSequence s, int start, int count,
                             int after) {
       setClearIconVisible(s.length() > 0); 
	
	  
   } 

   @Override
   public void beforeTextChanged(CharSequence s, int start, int count,
                                 int after) {
        
   } 

   @Override
   public void afterTextChanged(Editable s) {
	   if(inputListener!=null){
		   inputListener.onEditorInput(); }
   } 
   
  
   /**
    * 设置晃动动画
    */
   public void setShakeAnimation(){
   	this.setAnimation(shakeAnimation(5));
   }
   
   
   /**
    * 晃动动画
    * @param counts 1秒钟晃动多少下
    * @return
    */
   public static Animation shakeAnimation(int counts){
   	Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
   	translateAnimation.setInterpolator(new CycleInterpolator(counts));
   	translateAnimation.setDuration(1000);
   	return translateAnimation;
   }
}