package breakout_proper;

import game_objects.*;

public class Bonus extends Rectangle2d implements Movable
{
	private Vector2d movement = new Vector2d();
	private Function func;
	
	public Vector2d getMovement() { return this.movement; }
	public void setMovement(Vector2d m) { this.movement = m; }
	
	static interface Function
	{
		public void activate();
	}
	
//----------------------------------------
	
	public void setSpeed(double v)
	{
		movement.normalize().factor(v);
	}
	
	public void changeSpeed(double f)
	{
		movement.factor(f);
	}
	
	public void move(Vector2d v)
	{
		this.getPoint().add(v);
	}
	
	public void move()
	{
		this.getPoint().add(movement);
	}
	
	void activate()
	{
		func.activate();
	}
	
//----------------------------------------
	
	public Bonus(Bonus gobj) 
	{
		super(gobj);
		this.movement = new Vector2d(gobj.getMovement());
		this.func = gobj.func;
	}
	/*
	public Bonus(double x, double y, double w, double h) 
	{
		super(x,y,w,h);
	}
	
	public Bonus(Point2d point, double w, double h) 
	{
		super(point,w,h);
	}	*/
	
	public Bonus(double x, double y, double w, double h, Vector2d v, Function f) 
	{
		super(x,y,w,h);
		func = f;
		movement = v;
	}
	
	public Bonus(Point2d point, double w, double h, Vector2d v, Function f) 
	{
		super(point,w,h);
		func = f;
		movement = v;
	}	
}
