package org.gChess;

import java.util.ArrayList;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;



public class King extends ChessPiece {
	
	
	public King(int color, Location loc, ChessBoard cb, String name) {
		super(color, loc,  cb, name);
		if (getColor() == ChessPiece.BLACK) {
			loadImage(R.drawable.black_king);
		}
		else if (getColor() == ChessPiece.WHITE) {
			loadImage(R.drawable.white_king);
		}
	}

	@Override
	public ArrayList<Location> getMoveLocations() {
		ArrayList<Location> openLocs = new ArrayList<Location>();
		for ( int index = 0; index < 8; index++ ){
			openLocs.add( getLoc().getLocationInDir(index));
		}
		return openLocs;
	}
	


}
