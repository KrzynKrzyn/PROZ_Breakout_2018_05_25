package breakout_proper;

import game_objects.*;

public class Ball extends Circle2d implements Movable
{
	private Vector2d movement = new Vector2d();
	
	public Vector2d getMovement() { return this.movement; }
	public void setMovement(Vector2d m) { this.movement = m; }
	
	private boolean destructible = false;
	
	public boolean isDestructible() { return destructible; }
	public void setIndestructible() { destructible = false; }
	public void setDestructible() { destructible = true; }
	
	private int durability = 1;
	
	public int getDurablility() { return durability; }
	public void setDurability(int dur) { durability = dur; }
	public void decDurability() { durability -= 1; }
	
//------------------------------------------------
	
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
		
	public void bounce(Shape2d gobj)
	{
		movement = movement.getReflection(gobj.collisionVector(this));
	}
	
	public void bounce(Vector2d vc)
	{
		movement = movement.getReflection(vc);
	}
	
	public Vector2d bouncevector(Shape2d gobj)
	{
		return gobj.collisionVector(this);
	}
	
	//------------------------------------------------
	
	public Ball(Ball gobj) 
	{
		super(gobj);
		this.movement = new Vector2d(gobj.getMovement());
		this.destructible = gobj.destructible;
		this.durability = gobj.durability;
	}
	
	public Ball(double x, double y, double r, Vector2d v) 
	{
		super(x, y, r);
		movement = v;
	}
	
	public Ball(Point2d point, double r, Vector2d v) 
	{
		super(point, r);
		movement = v;
	}
}
