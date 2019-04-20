package breakout_proper;

import game_objects.*;

public class Block extends Rectangle2d
{
	private boolean destructible = true;
	private int durability = 0;
	
	public boolean isDestructible() { return destructible; }
	public int getDurablility() { return durability; }
	
	public void setIndestructible() { destructible = false; }
	public void setDestructible() { destructible = true; }
	public void setDurability(int dur) { durability = dur; }
	public void decDurability() { durability -= 1; }
	
//------------------------------------------------
	
	public Block(Block gobj) 
	{
		super(gobj);
		this.durability = gobj.durability;
		this.destructible = gobj.destructible;
	}
	
	public Block(double x, double y, double w, double h, int dur) 
	{
		super(x,y,w,h);
		durability = dur;
	}
	
	public Block(Point2d point, double w, double h, int dur) 
	{
		super(point,w,h);
		durability = dur;
	}	
}
