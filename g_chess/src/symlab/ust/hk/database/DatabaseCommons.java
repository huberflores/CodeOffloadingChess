package symlab.ust.hk.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;


public class DatabaseCommons {

	private String outFileName;
	private static final String app_package = "org.gChess";
	private static final String fileName = "event-data.db";
	
	private String absoluteFilePath;
	 
	 
	public DatabaseCommons(){ 
		
	}
	
	public void copyDatabaseFile() throws IOException{
		absoluteFilePath = "/data/data/" + app_package + "/databases/" + fileName;
		
	     InputStream myInput = new FileInputStream(absoluteFilePath);
	     Calendar calendar = Calendar.getInstance();
	    
	     this.outFileName = "/sdcard/" + fileName + calendar.getTimeInMillis()+".sql";
	      OutputStream myOutput = new FileOutputStream(outFileName);
	      byte[] buffer = new byte[1024];
	     int length;
	     while ((length = myInput.read(buffer))>0){ 
	     myOutput.write(buffer, 0, length);
	     }
	     myOutput.flush();
	     myOutput.close();
	     myInput.close();
	    
	     File borrar = new File(absoluteFilePath);
	     borrar.delete();
	 
	}
	    
	public boolean fileToCopy(){
	     File check = new File(absoluteFilePath);
	     if (check.exists()==true){
	     return true;
	     }else{
	     return false;
	     }
	}

	public String getDataBasePath(){
	     return this.outFileName;
	}
	    
	    
	public void deleteDatabaseFile(){
		File borrar = new File(absoluteFilePath);
	    borrar.delete();
	}

	
}






