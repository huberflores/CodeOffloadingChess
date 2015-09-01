package org.gChess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import cs.mc.ut.ee.logic.MiniMaxRemote;
import cs.mc.ut.ee.utilities.Commons;

import symlab.ust.hk.algorithm.MiniMax;
import symlab.ust.hk.database.DatabaseCommons;
import symlab.ust.hk.database.DatabaseHandler;


import android.content.Context;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

/**
 * ChessView says "I wanna put your stuff on the screen,
 * and I know how do do it." I say, great. So, ChessView,
 * would you please put my stuff on the screen? Oh, and I
 * also would like some of your fantastic functions
 * that make it easy to dump my grid into and in return
 * get a nice, formatted display. Thank you for chess boards.
 * I really do appreciate it.
 */
public class ChessView extends View {
	
	//Watch out, b/c these babies are
	//only accessible after the ChessView
	//class has been instantiated
	protected static Paint GREEN;
	protected static Paint CYAN;
	
	private Paint textPaint;
	private ChessBoard cb;
	
	private Integer height;
	private Integer width;
	
	private static final int SELECT_MODE = 0;
	private static final int MOVE_MODE = 1;
	
	/** Tells us whether or not we are in
	 * selection mode (selecting a piece to move)
	 * or move mode (selecting a location to move to)
	 */
	private int actionMode;
	
	private static final int BLACK_TURN = 0;
	private static final int WHITE_TURN = 1;
	
	private int whosTurn;
	
	private ChessPiece selected;
	
	Context baseContext;
	
	DatabaseHandler dataEvent;
	
	
	public ChessView(Context context, LinearLayout.LayoutParams params) {
		
		super(context);
		
		//database
		dataEvent = DatabaseHandler.getInstance();
        dataEvent.setContext(context);
		
		baseContext = context;
				
		CYAN = new Paint();
		GREEN = new Paint();
		
		GREEN.setColor(Color.BLUE);
		CYAN.setColor(Color.CYAN);
		
		textPaint = new Paint();	
		textPaint.setColor(Color.BLACK);
		
		height = getHeight();
		width = getWidth();
		
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.FILL);
        
		cb = new ChessBoard(this, params);

		
		actionMode = SELECT_MODE;
		whosTurn = WHITE_TURN;
		selected = null;
		
		new Player().start();
		
		
	
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event){
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			String test = event.getX() + " " + event.getY();
			Log.i("TOUCH_EVENT", test);
			
			ChessSquare cs = cb.squareAtCoords((int) event.getX(), (int) event.getY());
			// did we touch a chess square?
			if (cs != null) {
				ChessPiece cp = cs.getOccupant();
				// are we selecting a piece?
				if (actionMode == SELECT_MODE) {
					// did our square have a piece on it?
					if (cp != null) {
						// does the piece belong to the current player's team?
						if ((cp.getColor() == ChessPiece.WHITE) && (whosTurn==WHITE_TURN)) {
							selected = cp;
							actionMode = MOVE_MODE;
							Log.i("EVENT", "Selection of: " + selected);
						}
					}
				}
				// we are selecting a move location?
				else if (actionMode == MOVE_MODE) {
					ArrayList<Location> locs = selected.getValidMoveLocations();
					// if the selected location is a valid move location
					if (cs.getLocation().includedIn(locs)) {
						selected.moveTo(cs.getLocation());
						if (whosTurn == WHITE_TURN) {
							whosTurn = BLACK_TURN;
							///Right after white turn finishes, then new black turn is active
						
														
						}
						/*else{  
							whosTurn = WHITE_TURN;
						}*/
					}
					else {
						selected = null;
					}
					// make sure to go back to select mode
					actionMode = SELECT_MODE;
				}
			}		 
			invalidate();
			
			
			/*if (whosTurn == BLACK_TURN){
				automaticBlackMove();
			}*/
			
			
			Log.i("NOTICE", "invalidating...");
		}

		return true;
	}
	
	/** 
	 * Here the search algorithm should be called 
	 * by creating a new instance of DecisionMove class 
	 */
	
	
	class Player extends Thread {

		public void run() {
			// TODO Auto-generated method stub
			
			while (true){
				
				if (whosTurn==BLACK_TURN){
					automaticBlackMove();
				}
			}
			
		}
		
		public void automaticBlackMove(){
			
			/**
			 * Local processing
			 */
			//MiniMax alg = new MiniMax();
			
			/**
			 * Remote processing
			 */
			MiniMaxRemote alg = new MiniMaxRemote();  
			
			//int [][] chessBoard = cb.getChessBoard();  
			
			/**
			 * automatic board (play time)
			 */
			//int [][] chessBoard = cb.getChessBoard(); 
			
			/**
			 * hard coded board
			 * this is for experimenting purposes
			 */ 
					
			int [][] chessBoard = {{0, -3, -4, -2, -1, -4, 0, -3}, 
					{0, 0, -6, -6, -6, 0, -6, -6}, 
					{-6, -6, 0, 0, 0, 0, 0, -5}, 
					{-5, 0, 0, 5, 0, 0, 0, 0}, 
					{0, 0, 6, 0, 6, -6, 0, 0}, 
					{6, 6, 0, 6, 0, 6, 0, 6}, 
					{0, 0, 0, 0, 0, 0, 6, 0}, 
					{3, 0, 0, 2, 1, 4, 5, 3}
					};
			
			
			
			double startTime = System.currentTimeMillis();
			 
			float [] steps = alg.getSteps(chessBoard, 1);
		
			
			double finalTime = System.currentTimeMillis();
			Log.i("Total time for local processing: ", (finalTime- startTime) + "");
			Log.i("Battery level: ", Commons.batteryLevel + "");
			
			int [] stats = getCpuUsageStatistic();
			
			Log.i("user, system, idle, cpu percentage", stats[0] + "," + stats[1] + "," + stats[2] + "," + stats[3]);
			
			dataEvent.getInstance().getDatabaseManager().saveData("Local processing", startTime, finalTime, (double) stats[0], (double) stats[1]);
			
			ArrayList<ChessPiece> pieces = cb.getPiecesByColor(ChessPiece.BLACK);
			
			if (pieces.size()==1){
				extractDatabaseFile(new DatabaseCommons());
			}
			
			
			
			int x1 = (int)(steps[1])/8;
			int y1 = (int)(steps[1])%8;
			Location origin = new Location (y1,x1);
			
			int x2 = (int)(steps[2])/8;
			int y2 = (int)(steps[2])%8;
			Location destine = new Location (y2,x2);
			
			ChessPiece auto = cb.getPieceAt(origin);
			auto.moveTo(destine);
			whosTurn = WHITE_TURN;
			postInvalidate();

		}

	
	}
	
	
	/**
	 * Naive method based on Random
	 */
	/*public void automaticBlackMove(){
		
		ArrayList<ChessPiece> pieces = cb.getPiecesByColor(ChessPiece.BLACK);
		boolean pieceHasMoved=false;
		
		while (!pieceHasMoved){
			
			int source = new Random().nextInt(pieces.size());
			ChessPiece auto = (pieces.get(source));
			
			ArrayList<Location> locs = auto.getValidMoveLocations();
			
			if (locs.size()>0){
				int dest = new Random().nextInt(locs.size());
				
				auto.moveTo(locs.get(dest));
				
				whosTurn = WHITE_TURN;
				
				pieceHasMoved = true;
				
				Toast.makeText(baseContext.getApplicationContext(), "WHITE TURN!!!",Toast.LENGTH_SHORT).show();
			}
			
		}
		
	}*/
	
	
		
	
	
	
	@Override
	public void onDraw(Canvas canvas) {
		Log.i("NOTICE", "rendering...");
		cb.render(canvas);

		//canvas.drawColor(Color.WHITE);
//		canvas.drawText("Hello World", 20, 20, textPaint);
//		Rect r = new Rect(5,5,20,20);
//		canvas.drawRect(r, GREEN);
	}
	
	/**
	 * get our changes and put 'em on the screen/canvas/thingy.
	 */
	private void update() {
		//TODO
	}
	
	/**
	 * I gots to get myself a new game, and this is how I'm gonna
	 * do it. I'm a man with a mission: to play chess.
	 */
	private void initNewGame() {
		//TODO
	}
	
	public int[] getCpuUsageStatistic() {

	    String tempString = executeTop();

	    tempString = tempString.replaceAll(",", "");
	    tempString = tempString.replaceAll("User", "");
	    tempString = tempString.replaceAll("System", "");
	    tempString = tempString.replaceAll("IOW", "");
	    tempString = tempString.replaceAll("IRQ", "");
	    tempString = tempString.replaceAll("%", "");
	    for (int i = 0; i < 10; i++) {
	        tempString = tempString.replaceAll("  ", " ");
	    }
	    tempString = tempString.trim();
	    String[] myString = tempString.split(" ");
	    int[] cpuUsageAsInt = new int[myString.length];
	    for (int i = 0; i < myString.length; i++) {
	        myString[i] = myString[i].trim();
	        cpuUsageAsInt[i] = Integer.parseInt(myString[i]);
	    }
	    return cpuUsageAsInt;
	}

	private String executeTop() {
	    java.lang.Process p = null;
	    BufferedReader in = null;
	    String returnString = null;
	    try {
	        p = Runtime.getRuntime().exec("top -n 1");
	        in = new BufferedReader(new InputStreamReader(p.getInputStream()));
	        while (returnString == null || returnString.contentEquals("")) {
	            returnString = in.readLine();
	        }
	    } catch (IOException e) {
	        Log.e("executeTop", "error in getting first line of top");
	        e.printStackTrace();
	    } finally {
	        try {
	            in.close();
	            p.destroy();
	        } catch (IOException e) {
	            Log.e("executeTop",
	                    "error in closing and destroying top process");
	            e.printStackTrace();
	        }
	    }
	    return returnString;
	}
	
	//Extract database        
    public void extractDatabaseFile(DatabaseCommons db){	
    	try {
    		db.copyDatabaseFile();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }

	
}
