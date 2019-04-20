package breakout_proper;

import game_objects.*;
import java.util.*;

public class BallSet 
{
	private double radius;
	private Vector2d vector;
	
	private List<Ball> ball_tab;
	
//------------------------------------------------------
	
	public int size() { return ball_tab.size(); }
	
	public double getBlueR() { return radius; }
	public Vector2d getBlueV() { return vector; }
	
	public void setBlueR(double r) { radius = r; }
	public void setBlueV(Vector2d v) { vector = v; }
	
	public Ball getBall(int n) { return ball_tab.get(n); }
	public List<Ball> getCurrentBalls() { return Collections.unmodifiableList(new ArrayList<Ball>(ball_tab)); }//return new ArrayList<Ball>(ball_tab); }
	
//------------------------------------------------------
	
	public Ball spawnBall(double x, double y)
	{
		Ball ret = new Ball(x, y, radius, new Vector2d(vector));
		ball_tab.add(ret);
		
		return ret;
	}
	
	public Ball spawnBall(Point2d p)
	{
		Ball ret = new Ball(p, radius, new Vector2d(vector));
		ball_tab.add(ret);
		
		return ret;
	}
	
	public void removeBall(int n)
	{
		ball_tab.remove( ball_tab.get(n) );
	}
	
	public void removeBall(Ball b)
	{
		ball_tab.remove(b);
	}
	
//----------------------------
	
	public BallSet(BallSet other)
	{
		this.radius = other.radius;
		this.vector = other.vector;
		
		this.ball_tab = new ArrayList<Ball>();
		for(int i=0;i<other.size();i++) this.ball_tab.add(new Ball(other.getBall(i)));
	}
	
	public BallSet(double r, Vector2d v)
	{
		ball_tab = new ArrayList<Ball>();
		radius = r;
		vector = v;
	}
}
