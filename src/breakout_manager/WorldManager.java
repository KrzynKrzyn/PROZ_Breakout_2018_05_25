package breakout_manager;

import breakout_proper.*;
import game_objects.Vector2d;

public final class WorldManager 
{	
	private RestrictedWorld operating, original;
	private RecordManager recorder;
	
	public RestrictedWorld accessWorld()
	{
		return operating;
	}
	
	static public RestrictedWorld generateWorldfromMap(BlockSet map)
	{
		return new RestrictedWorld(
				map,
				
				new Paddle( map.getXLim()/2 - 1.5*map.getXCell(),
							28*map.getYLim()/32,
							3*map.getXCell(),
							map.getYCell(),
							2),
				
				new BallSet( Math.min(map.getXCell(), map.getYCell())/2,
							new Vector2d(0.1, -1.75) ),
				
				new BonusSet( map.getXCell(),
							map.getYCell(),
							new Vector2d(0,1)),
							
				new World.Chances(75, 5, 5, 1, 3, 3, 4, 5),
				
				3);
	}
	
	public void setupWorld(RestrictedWorld world)
	{
		original = new RestrictedWorld(world);
		resetWorld();
	}
	
	public void setupWorld(BlockSet map)
	{
		this.setupWorld(generateWorldfromMap(map));
	}
	
	public void resetWorld()
	{
		operating = new RestrictedWorld(original);
		if(recorder != null) recorder.reattachRecording(operating);
	}
	/**
	 * This method turns on recorder that can save records later on.
	 * @param name
	 * @return
	 */
	public boolean enableRecorder(String name)
	{
		if(recorder != null) return false;
		recorder = new RecordManager();
		recorder.startRecording(name, operating);
		
		return true;
	}
	/**
	 * This method saves all records that have been captured by a recorder, and turns off the recorder.
	 * @return
	 */
	public boolean disableRecorder()
	{
		if(recorder == null) return false;
		if(recorder.getScore() > 0) recorder.saveRecording();
		recorder.stopRecording();
		
		recorder = null;
		
		return true;
	}
//---------------------------------------------------------------------
	public WorldManager()
	{
		setupWorld( generateWorldfromMap(new BlockSet(100,100,10,10)) );
	}
}
