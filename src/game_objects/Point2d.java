package game_objects;

public class Point2d
{
	private double x;
	private double y;
	
	public double x() { return x; }
	public double y() { return y; }
	
	public void setX(double nx) { x = nx; }
	public void setY(double ny) { y = ny; }
	
	//====================================================
	
	public static double getDistanceSq(Point2d p1, Point2d p2)
	{  
		double nx = p2.x() - p1.x();
		double ny = p2.y() - p1.y();
		
		return nx*nx + ny*ny;
	}
	
	public static double getDistance(Point2d p1, Point2d p2) { return Math.sqrt(getDistanceSq(p1, p2)); }
	
	public double getDistanceSq(Point2d p) { return getDistanceSq(this, p); }
	public double getDistance(Point2d p) { return Math.sqrt(this.getDistanceSq(p)); }
	
	//====================================================
	/**
	 * This method returns point moved by a given vector .
	 * @param p
	 * @param v
	 * @return
	 */
	public static Point2d add(Point2d p, Vector2d v) 
	{ 
		return new Point2d(p.x + v.x(), p.y + v.y()); 
	}
	/**
	 * This method moves this point by a given vevtor.
	 * @param v
	 * @return
	 */
	public Point2d add(Vector2d v)
	{
		x += v.x();
		y += v.y();
		
		return this;
	}
	
	//====================================================
	@Override
	public String toString()
	{
		return this.x + "\t" + this.y;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(o == null) return false;
		if(!(o instanceof Point2d)) return false;
		
		Point2d p = (Point2d)o;
		
		if(p.x != this.x) return false;
		if(p.y != this.y) return false;
		
		return true;
	}
	
	public Point2d()
	{
		x = 0;
		y = 0;
	}
	
	public Point2d(Point2d p)
	{
		x = p.x;
		y = p.y;
	}
	
	public Point2d(double nx, double ny)
	{
		x = nx;
		y = ny;
	}
}
