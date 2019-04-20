package breakout_proper;

import game_objects.*;

public class Paddle extends Rectangle2d implements Movable
{
	private double speed = 2;
	private double spread = 2*Math.PI/3;
	
	final private Vector2d stop = new Vector2d(0,0);
	final private Vector2d left = new Vector2d(-speed,0);
	final private Vector2d right = new Vector2d(speed,0);
	
	private Vector2d movement = stop;
	
	public Vector2d getMovement() { return this.movement; }
	public double getSpeed() { return this.speed; }
	public double getSpread() { return spread; }
	
	public void setMovement(Vector2d m) { this.movement = m; }
	public void setSpread(double s) { spread = s; }

	//------------------------------------------------------------
	
	private void updateLeftRight()
	{
		left.setX(-speed);
		right.setX(speed);
	}
	
	public void setSpeed(double s) 
	{ 
		movement.factor(s/speed);
		speed = s;
		
		updateLeftRight();
	}
	
	public void changeSpeed(double f) 
	{ 
		movement.factor(f);
		speed *= f;
		
		updateLeftRight();
	}
	
	public void goLeft()
	{
		movement = left;
	}
	
	public void goRight()
	{
		movement = right;
	}
	
	public void goNowhere()
	{
		movement = stop;
	}
	
	public void move(Vector2d v)
	{
		this.getPoint().add(v);
	}
	
	public void move()
	{
		this.getPoint().add(movement);
	}
	/**
	 * This method returns angle that should be assigned to ball had it collide with upper edge of a paddle
	 * @param b
	 * @return
	 */
	public double getBounceAngle(Ball b)
	{
		return (Math.PI-spread/2) + (spread/this.getWidth())*(this.getRLimit()-b.getPoint().x());
	}
//------------------------------------------------
	
	public Paddle(Paddle gobj) 
	{
		super(gobj);
		this.movement = new Vector2d(gobj.movement);
		this.spread = gobj.spread;
		this.setSpeed(gobj.speed);
		updateLeftRight();
	}
	
	public Paddle(double x, double y, double w, double h, double speed) 
	{
		super(x,y,w,h);
		
		this.speed = speed;
		updateLeftRight();
	}
	
	public Paddle(Point2d point, double w, double h, double speed) 
	{
		super(point,w,h);
		
		this.speed = speed;
		updateLeftRight();
	}	
}
