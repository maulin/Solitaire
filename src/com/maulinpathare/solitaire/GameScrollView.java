package com.maulinpathare.solitaire;

import android.content.Context;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class GameScrollView extends ScrollView{
	
	public GameScrollView(Context context) {
		super(context);
	}
	
	@Override
	public boolean onInterceptTouchEvent (MotionEvent ev){
		return false;
		
	}
}
