package com.maulinpathare.solitaire;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ScrollView;
		
public class Solitaire extends Activity{
    /** Called when the activity is first created. */
	private Context mContext;
	private ScrollView mGsv;
	private GameView mGv;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.main);
    }
    
    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean result = super.onCreateOptionsMenu(menu);
        menu.add(0, 1, 0, R.string.newGame);
        menu.add(0, 2, 0, R.string.exitGame);
        return result;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case 1:
        	mGsv = new ScrollView(this);
        	mGv = new GameView(this);
        	mGsv.addView(mGv);
        	setContentView(mGsv);
            return true;
        case 2:
            finish();
            return true;
        }
        return false;
    }
    
}//end class