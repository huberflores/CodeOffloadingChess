package org.gChess;

import java.io.IOException;

import symlab.ust.hk.database.DatabaseCommons;

import cs.mc.ut.ee.utilities.Commons;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class gChess extends Activity { 
    /** Called when the activity is first created. */ 
	
	
	//Battery level intent
		private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver(){
		@Override
		public void onReceive(Context arg0, Intent intent) {
				int level = intent.getIntExtra("level", 0);
				Commons.batteryLevel= level;
			}
		};
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    
	    
	    this.registerReceiver(this.mBatInfoReceiver,
	            new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
	    
	    Display display = getWindowManager().getDefaultDisplay();
	    Point size = new Point();
	    display.getSize(size);
	    int width = size.x;
	    int height = size.y;
        
	    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
	    
	    	    
	    setContentView(R.layout.main);
        ChessView cv = new ChessView(this, params);
       
        
        setContentView(cv);
        
       
    }
    
        
    
    
}