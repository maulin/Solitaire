package com.maulinpathare.solitaire;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
		
public class Solitaire extends Activity{
    /** Called when the activity is first created. */
	private GameView mGv;
	private GameScrollView mGsv;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean result = super.onCreateOptionsMenu(menu);
        menu.add(0, 1, 0, R.string.newGame);
        menu.add(0, 2, 0, R.string.exitGame);
        menu.add(0, 3, 0, R.string.help);
        return result;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case 1:
        	mGv = new GameView(this);
        	mGsv = new GameScrollView(this);
        	mGsv.addView(mGv);
        	setContentView(mGsv);
            return true;
        case 2:
            finish();
            return true;
        case 3:
        	setContentView(R.layout.help);
            return true;
        }
        return false;
    }
    
}//end class