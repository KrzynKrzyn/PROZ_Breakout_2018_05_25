package breakout_proper;

import java.util.*;

import game_objects.*;

public class BonusSet
{
	private Vector2d vector;
	private double height;
	private double width;
	
	private List<Bonus> active_tab;
	
//------------------------------------------

	public int size() { return active_tab.size(); }
	
	public Vector2d getBlueV() { return vector; }
	public double getBlueH() { return height; }
	public double getBlueW() { return width; }
	
	public void setBlueV(Vector2d v) { vector = v; }
	public void setBlueH(double h) { height = h; }
	public void setBlueW(double w) { width = w; }
	
	public Bonus getActive(int n) { return active_tab.get(n); }
	public List<Bonus> getCurrentActive() { return Collections.unmodifiableList(new ArrayList<Bonus>(active_tab)); } //new ArrayList<Bonus>
	
//--------------------------------------------
		
	public Bonus spawnBonus(double x, double y, Bonus.Function bf)
	{
		Bonus ret = new Bonus(x,y,width,height,new Vector2d(vector),bf);
		
		active_tab.add( ret );
		return ret;
	}
	
	public void removeActive(Bonus b)
	{
		active_tab.remove(b);
	}
	
	public void removeActive(int n)
	{
		active_tab.remove(active_tab.get(n));
	}
	
//--------------------------------------------
	
	public BonusSet(BonusSet other)
	{
		this.height = other.height;
		this.width = other.width;
		this.vector = other.vector;
		
		active_tab = new ArrayList<Bonus>();
		for(int i=0;i<other.size();i++) active_tab.add(new Bonus(other.getActive(i)));
	}
	
	public BonusSet(double w, double h, Vector2d v)
	{
		active_tab = new ArrayList<Bonus>();
		
		height = h;
		width = w;
		vector = v;
	}
}
