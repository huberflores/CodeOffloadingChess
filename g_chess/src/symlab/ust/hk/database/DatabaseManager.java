package symlab.ust.hk.database;

import org.gChess.manager.MyEventContentProvider;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

public class DatabaseManager {
		
	private Uri dbUri;
	private Context dContext;
	
	
	public DatabaseManager(Context c){
		this.dContext = c;
	}
	
	public void saveData(String action, double time1, double time2, double attr1, double attr2){
		ContentValues values = new ContentValues();
		values.put(EventDescriptor.COLUMN_EVENT_NAME, action);
		values.put(EventDescriptor.COLUMN_START_TIME, time1);
		values.put(EventDescriptor.COLUMN_END_TIME, time2);
		values.put(EventDescriptor.COLUMN_ATTRIBUTE1, attr1);
		values.put(EventDescriptor.COLUMN_ATTRIBUTE2, attr2);
		 	
		dbUri = dContext.getContentResolver().insert(MyEventContentProvider.CONTENT_URI, values);
		
	 }
	
	public void setDbUri(Uri cursor){
		this.dbUri = cursor;
	}


}
