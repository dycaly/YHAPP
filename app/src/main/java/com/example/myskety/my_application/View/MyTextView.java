package com.example.myskety.my_application.View;




import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.example.myskety.my_application.R;

public class MyTextView extends View {

	private int mColor = 0x35A337;
	private String mText="";
	private int mTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics());

	private Canvas mCanvas;
	private Bitmap mBitmap;
	private Paint mPaint;

	private int hight =0;
	private float mAlpha;
	private Rect mIconRect;
	private Rect mTextBound;

	private Paint mTextPaint;


	public MyTextView(Context context) {
		this(context,null);
	}
	public MyTextView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		
		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.IconView);
				
		int n = typedArray.getIndexCount();
		
		for(int i = 0; i < n; i++){
			int attr = typedArray.getIndex(i);
			switch(attr){
			case R.styleable.IconView_m_color:
				mColor = typedArray.getColor(attr, mColor);
				break;
			case R.styleable.IconView_m_text:
				mText = typedArray.getString(attr);
				break;
			case R.styleable.IconView_m_text_size:
				mTextSize = (int) typedArray.getDimension(attr, mTextSize);
				break;
			}
		}
		typedArray.recycle();
		
		mTextBound = new Rect();
		mTextPaint = new Paint();
		mTextPaint.setTextSize(mTextSize);
		mTextPaint.getTextBounds(mText, 0, mText.length(), mTextBound);
		
		
	}
	
	public void setIconAlpha(float alpha){
		this.mAlpha = alpha;
		invalidateView();
	}

	//�ػ�
	private void invalidateView() {
		if(Looper.getMainLooper() == Looper.myLooper()){
			invalidate();
		}
		else{
			postInvalidate();
		}
	}
	
	
	@SuppressLint("DrawAllocation")
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		int iconWidth = Math.min(getMeasuredWidth() - getPaddingLeft() - getPaddingRight(),
				getMeasuredHeight() - getPaddingTop() - getPaddingBottom() - mTextBound.height());

		int left = getMeasuredWidth() /2 - iconWidth /2;
		int top = (getMeasuredHeight() - mTextBound.height()) / 2 - iconWidth / 2;
		
		mIconRect = new Rect(left,top,left + iconWidth, top + iconWidth);

		hight = getMeasuredHeight()/3*2;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		
		int alpha = (int) Math.ceil(mAlpha * 255);

		drawSourceText(canvas, alpha);

		drawTargetText(canvas, alpha);

	}
	private void drawTargetText(Canvas canvas, int alpha) {
		mTextPaint.setColor(mColor);
		mTextPaint.setAlpha(alpha);
		int x =mIconRect.left + mIconRect.width()/2 - mTextBound.width()/2;
		int y = hight;
		canvas.drawText(mText, x, y, mTextPaint);
		
	}
	private void drawSourceText(Canvas canvas, int alpha) {
		mTextPaint.setColor(0xFF5b5b5b);
		mTextPaint.setAlpha(255-alpha);
		int x =mIconRect.left + mIconRect.width()/2 - mTextBound.width()/2;
		int y = hight;
		canvas.drawText(mText, x, y, mTextPaint);
	}
	
	private static final String INSTANCE_STATUS = "instance_mt_status";
	private static final String STATUS_ALPHA = "status_mt_alpha";
	
	@Override
	protected Parcelable onSaveInstanceState() {
		
		Bundle bundle = new Bundle();
		bundle.putParcelable(INSTANCE_STATUS, super.onSaveInstanceState());
		bundle.putFloat(STATUS_ALPHA, mAlpha);
		
		return bundle;
	}
	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		
		if(state instanceof Bundle){
			Bundle bundle = (Bundle) state;
			mAlpha = bundle.getFloat(STATUS_ALPHA);
			super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATUS));
		}
		else
			super.onRestoreInstanceState(state);
	}
	
}
