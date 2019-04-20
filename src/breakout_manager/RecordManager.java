package breakout_manager;

import java.util.*;
import java.io.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

public final class RecordManager 
{
	private String user_name;
	private Calendar start;
	private Tracker score;

	private RestrictedWorld recorded;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ROOT);
	
	public static class Record implements java.io.Serializable
	{
		private static final long serialVersionUID = 8441887872665377708L;
		/*
		 * 
		 */
		private String user_name;
		private String start_time;
		private long time_played;
		private int score;
		
		public String getUser_name() {
			return user_name;
		}
		public String getStart_time() {
			return start_time;
		}
		public long getTime_played() {
			return time_played;
		}
		public int getScore() {
			return score;
		}
	}
	
	public String getName() 
	{
		return user_name;
	}
	
	public int getScore() 
	{
		return score.number;
	}
	
	private abstract class Tracker implements breakout_proper.Observer
	{
		int number;
		
		public Tracker(int n) { number = n; }		
	}
//--------------------------------------------------------------------------------------------------
	static private void saveRecord(Record record)
	{
		try 
		{
			FileOutputStream fileOut;
			ObjectOutputStream out;
			
			File f = new File("records.rcd");
			if(!f.exists() || f.isDirectory())
			{
				fileOut = new FileOutputStream("records.rcd");
				out = new ObjectOutputStream(fileOut);
			}
			else
			{
				fileOut = new FileOutputStream("records.rcd", true);
				out = new ObjectOutputStream(fileOut)
				{
					  @Override
					  protected void writeStreamHeader() throws IOException {
					    reset();
					  }
					};
			}
				
			out.writeObject(record);
			
			out.close();
			fileOut.close();
		}
		catch(IOException i) 
		{
			i.printStackTrace();
		}			
	}
	
	static public List<Record> loadRecords()
	{
		File f = new File("records.rcd");
		List<Record> ret = new ArrayList<Record>();
		Object temp_rec;
		
		if(!f.exists() || f.isDirectory()) return ret;
		
		try 
		{
			FileInputStream fileIn = new FileInputStream("records.rcd");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			
			while(true)
			{
				try {
					temp_rec = in.readObject();
					ret.add((Record)temp_rec);
				}
				catch(EOFException e){
					break;
				}
			}
			
			in.close();
			fileIn.close();
		}
		catch(IOException i) 
		{
			i.printStackTrace();
			return null;
		}
		catch(ClassNotFoundException c) 
		{
			c.printStackTrace();
			return null;
		}
		
		return ret;
	}
	
	public void startRecording(String name, RestrictedWorld world)
	{
		user_name = name;
		start = Calendar.getInstance();
		recorded = world;

		score.number = 0;
		recorded.attachScoreTracker(score);
	}
	
	public void reattachRecording(RestrictedWorld world)
	{
		if(recorded == null) return;
		
		recorded.detachScoreTracker(score);
		recorded = world;
		recorded.attachScoreTracker(score);
	}
	/*
	public void pauseRecording()
	{
		if(recorded != null)
			recorded.detachScoreTracker(score);
	}
	
	public void continueRecording(RestrictedWorld world)
	{
		recorded = world;
		recorded.detachScoreTracker(score);
	}
	*/
	public void stopRecording()
	{
		if(recorded == null) return;
		
		recorded.detachScoreTracker(score);
		recorded = null;
	}
	
	public void saveRecording()
	{
		if(recorded == null) return;
		
		Record ret = new Record();
		
		ret.user_name = this.user_name;
		ret.start_time = sdf.format(this.start.getTime());
		ret.time_played = (Calendar.getInstance().getTimeInMillis() - this.start.getTimeInMillis())/1000; 
		ret.score = score.number;
		
		saveRecord(ret);
	}
//--------------------------------------------------------------------------------------------------
	public RecordManager() 
	{
		score = new Tracker(0)
		{
			public void update() { ++number; }
		};
	}
	
    public static void main(String[] args) throws ParseException
    {/*
    	RestrictedWorld world = WorldManager.generateWorldfromMap( FileManager.loadMap("test23") );
    	RecordManager rec_test = new RecordManager();
    	
    	rec_test.startRecording("test_UG", world);
    	rec_test.stopRecording();
    	
    	System.out.println(RecordManager.loadRecords().size());
    	System.out.println(RecordManager.loadRecords().get(2).user_name);*/
    	/*
    	SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ROOT);
        String date = sdf.format(Calendar.getInstance().getTime());
        
        System.out.println(date);
        
        Calendar cal = Calendar.getInstance();

        try
        {
        	cal.setTime( sdf.parse(date) );
        }
        catch (java.text.ParseException e)
        {
			e.printStackTrace();
		}

        System.out.println(cal.getTime());
        
        Calendar cak2 = Calendar.getInstance();
        
        System.out.println(cal.getTimeInMillis() - cak2.getTimeInMillis());*/
    	
    	List<Record> rec = RecordManager.loadRecords();
    	
    	for(Record r : rec) System.out.println(r.getUser_name() + " || " + r.getScore() + " || " + r.getStart_time() + " || " + r.getTime_played());
    }
}
