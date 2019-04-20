package game_objects;

public class Circle2d implements Shape2d
{
	private Point2d xy;
	private double radius;
	
	public Point2d getPoint() { return xy; }
	public double getRadius() { return radius; }
//--------------------------------------------------------
	public void setPoint(Point2d p) { xy = p; }
	public void setRadius(double r) { radius = r; }
//--------------------------------------------------------

	public boolean doesContain(Shape2d gobj)
	{
		if(gobj instanceof Rectangle2d) return doesContain((Rectangle2d)gobj);
		if(gobj instanceof Circle2d) return doesContain((Circle2d)gobj);
		
		return false;
	}
	
	public boolean doesCollide(Shape2d gobj)
	{
		if(gobj instanceof Rectangle2d) return doesCollide((Rectangle2d)gobj);
		if(gobj instanceof Circle2d) return doesCollide((Circle2d)gobj);
		
		return false;
	}
	
	
	public Vector2d collisionVector(Shape2d gobj)
	{
		if(gobj instanceof Rectangle2d) return collisionVector((Rectangle2d)gobj);
		if(gobj instanceof Circle2d) return collisionVector((Circle2d)gobj);
		
		return null;
	}
//--------------------------------------------------------	
	
	public boolean doesContain(Rectangle2d gobj) { return Collision2d.doesContain(this, gobj); }
	public boolean doesContain(Circle2d gobj) { return Collision2d.doesContain(this, gobj); }
	public boolean doesContain(Point2d gobj) { return Collision2d.doesContain(this, gobj); }	
	
	public boolean doesCollide(Rectangle2d gobj) { return Collision2d.doesCollide(this, gobj); }
	public boolean doesCollide(Circle2d gobj) { return Collision2d.doesCollide(this, gobj); }
	
	public Vector2d collisionVector(Rectangle2d gobj) { return Collision2d.collisionVector(this,gobj); }
	public Vector2d collisionVector(Circle2d gobj) { return Collision2d.collisionVector(this,gobj); }
	public Vector2d collisionVector(Point2d gobj) { return Collision2d.collisionVector(this,gobj); }
	
//--------------------------------------------------------
	
	public Circle2d(Circle2d gobj) 
	{
		xy = new Point2d(gobj.getPoint());
		radius = gobj.getRadius();
	}
	
	public Circle2d(double x, double y, double r) 
	{
		xy = new Point2d(x,y);
		radius = Math.abs(r);
	}
	
	public Circle2d(Point2d point, double r) 
	{
		xy = point;
		radius = Math.abs(r);
	}
}
