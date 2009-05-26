package com.maulinpathare.solitaire;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.widget.ImageView;

public class Card extends ImageView{
	private String mSuite;
	private int mRank;
	private Bitmap mImage;
	private Bitmap mBackImage;
	private boolean mFaceUp;
	private Rect mRect;
	private Rect mOldRect;
	private int mPile;
	
	public Card(Context context, Resources res, int image, String suite, int rank){
		super(context);
		//BitmapFactory.Options opts = new BitmapFactory.Options();
        //opts.inJustDecodeBounds = true;
		Bitmap bm, fm;
		fm = BitmapFactory.decodeResource(res, image);
		bm = BitmapFactory.decodeResource(res, R.drawable.bb1);
		mImage = Bitmap.createScaledBitmap(fm,49, 70, true);
		mBackImage = Bitmap.createScaledBitmap(bm,49, 70, true);
		mSuite = suite;
		mRank = rank;
		mFaceUp = false;
		mPile = 0;
		mRect = new Rect(0, 0, 49, 70);
		mOldRect = new Rect(0, 0, 49, 70);
	}
	
	public Card(Context context){
		super(context);
		
	}
	public Bitmap getFrontImage(){
		return mImage;
	}
	public Bitmap getBackImage(){
		return mBackImage;
	}
	public String getSuite(){
		return mSuite;
	}
	public int getRank(){
		return mRank;
	}
	public void setFaceUp(){
		mFaceUp = true;
	}
	public void setFaceDown(){
		mFaceUp = false;
	}
	public boolean isFaceUp(){
		return mFaceUp;
	}
	public Rect getRect(){
		return mRect;
	}
	public void setRect(int left, int top, int right, int bottom){
		mRect.set(left, top, right, bottom);
	}
	public void setRect(Rect rect){
		mRect.set(rect);
	}
	public Rect getmOldRect(){
		return mOldRect;
	}
	public void setmOldRect(int left, int top, int right, int bottom){
		mOldRect.set(left, top, right, bottom);
	}
	public void setmOldRect(Rect rect){
		mOldRect.set(rect);
	}
	public void setPile(int pile){
		mPile = pile;
	}
	public int getPile(){
		return mPile;
	}
}//end class Card