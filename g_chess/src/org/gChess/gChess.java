package org.gChess;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class gChess extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    
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