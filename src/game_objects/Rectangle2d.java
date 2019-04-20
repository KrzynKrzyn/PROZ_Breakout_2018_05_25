package game_objects;

public class Rectangle2d implements Shape2d
{
	private Point2d xy;
	private double width;
	private double height;

	public Point2d getPoint() { return xy; }
	public double getWidth() { return width; }
	public double getHeight() { return height; }
	
	public double getLLimit() { return xy.x(); }
	public double getRLimit() { return xy.x() + width; }
	public double getULimit() { return xy.y(); }
	public double getDLimit() { return xy.y() + height; }
	/**
	 * This method returns point that lies on a diagonals crossing
	 * @return
	 */
	public Point2d getMiddlePoint() { return new Point2d( (getLLimit()+getRLimit())/2, (getULimit()+getDLimit())/2 ); }
//--------------------------------------------------------
	public void setPoint(Point2d p) { xy = p; }
	public void setWidth(double w) { width = w; }
	public void setHeight(double h) { height = h; }	
//--------------------------------------------------------

	private Point2d getNearestPointIn(Point2d pnt)
	{
		double 	r_dist = Math.abs(this.getRLimit() - pnt.x()),
				l_dist = Math.abs(this.getLLimit() - pnt.x()),
				d_dist = Math.abs(this.getDLimit() - pnt.y()),
				u_dist = Math.abs(this.getULimit() - pnt.y());
		
		double 	min_dist = Math.min( Math.min(r_dist, l_dist), Math.min(d_dist, u_dist) );

		if(min_dist == r_dist) return new Point2d(this.getRLimit(), pnt.y());
		if(min_dist == l_dist) return new Point2d(this.getLLimit(), pnt.y());
		if(min_dist == d_dist) return new Point2d(pnt.x(), this.getDLimit());
		if(min_dist == u_dist) return new Point2d(pnt.x(), this.getULimit());
		
		return null;
	}
	
	private Point2d getNearestPointOut(Point2d pnt)
	{
		if(pnt.y() >= this.getULimit() && pnt.y() <= this.getDLimit())
		{
			if(pnt.x() <= this.getLLimit()) return new Point2d(this.getLLimit(), pnt.y());
			if(pnt.x() >= this.getRLimit()) return new Point2d(this.getRLimit(), pnt.y());
		}

		if(pnt.x() >= this.getLLimit() && pnt.x() <= this.getRLimit())
		{
			if(pnt.y() <= this.getULimit()) return new Point2d(pnt.x(), this.getULimit());
			if(pnt.y() >= this.getDLimit()) return new Point2d(pnt.x(), this.getDLimit());
		}
		
		if(pnt.x() < this.getLLimit() && pnt.y() < this.getULimit()) return new Point2d(this.getLLimit(), this.getULimit());
		if(pnt.x() < this.getLLimit() && pnt.y() > this.getDLimit()) return new Point2d(this.getLLimit(), this.getDLimit());
		if(pnt.x() > this.getRLimit() && pnt.y() < this.getULimit()) return new Point2d(this.getRLimit(), this.getULimit());
		if(pnt.x() > this.getRLimit() && pnt.y() > this.getDLimit()) return new Point2d(this.getRLimit(), this.getDLimit());
		
		return null;
	}
	/**
	 * This method returns point that lies on rectangle border that is nearest to given point
	 * @param pnt
	 * @return
	 */
	public Point2d getNearestPoint(Point2d pnt)
	{
		if(this.doesContain(pnt)) return this.getNearestPointIn(pnt);
		else return this.getNearestPointOut(pnt);
	}
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
	public Rectangle2d(Rectangle2d gobj) 
	{
		xy = new Point2d(gobj.getPoint());
		width = gobj.getWidth();
		height = gobj.getHeight();
	}
	
	public Rectangle2d(double x, double y, double w, double h) 
	{
		xy = new Point2d(x,y);
		width = Math.abs(w);
		height = Math.abs(h);
	}
	
	public Rectangle2d(Point2d point, double w, double h) 
	{
		xy = point;
		width = Math.abs(w);
		height = Math.abs(h);
	}	
}
