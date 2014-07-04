package org.gChess;

import java.util.ArrayList;
import java.util.Random;

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
	
	public ChessView(Context context, LinearLayout.LayoutParams params) {
		
		super(context);
		
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
			
			if (whosTurn == BLACK_TURN){
				automaticBlackMove();
			}
			
			
			Log.i("NOTICE", "invalidating...");
		}

		return true;
	}
	
	/** 
	 * Here the search algorithm should be called 
	 * by creating a new instance of DecisionMove class 
	 */
	public void automaticBlackMove(){
	
		
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


	}
	
	
	
	
		
	
	
	
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
}
