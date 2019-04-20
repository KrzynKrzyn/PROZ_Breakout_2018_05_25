package game_objects;

public class Vector2d 
{
	private double x;
	private double y;
	
	public double x() { return x; }
	public double y() { return y; }
	
	/**
	 * Sets x component of the vector.
	 * @param nx
	 */
	public void setX(double nx) { x = nx; }
	/**
	 * Sets y component of the vector.
	 * @param ny
	 */
	public void setY(double ny) { y = ny; }
	
	//====================================================
	
	/**
	 * Gets vector's length squared.
	 * @return
	 */
	public double getLengthSq() { return x*x + y*y; }
	/**
	 * Gets vector's length.
	 * @return
	 */
	public double getLength() { return Math.sqrt(this.getLengthSq()); }
	
	/**
	 * Normalizes given vector.
	 * @param v
	 * @return
	 */
	public static Vector2d normalize(Vector2d v)
	{
		Vector2d ret = new Vector2d(v);
		
		double length = ret.getLength();
		ret.x /= length;
		ret.y /= length;
		
		return ret;
	}
	/**
	 * Normalizes this vector.
	 * @return
	 */
	public Vector2d normalize()
	{
		double length = this.getLength();
		x /= length;
		y /= length;
		
		return this;
	}
	/**
	 * Sets vector's length to given length.
	 * @param length
	 */
	public void setLength(double length)
	{
		this.normalize();
		x *= length;
		y *= length;
	}
	
	//====================================================
	/**
	 * Gets vector's angle.
	 * @return
	 */
	public double getAngle()
	{
		return Math.atan2(y, x);
	}
	/**
	 * Sets vector's angle.
	 * @param angle
	 */
	public void setAngle(double angle)
	{
		double length = Math.sqrt(x*x + y*y);
		x = length * Math.sin(angle);
		y = length * Math.cos(angle);
	}
	
	//====================================================
	/**	
	 * This is method used to get dot product of two given vectors.
	 * @param v1
	 * @param v2
	 * @return This returns dot product of two given vectors.
	 */
	public static double dot(Vector2d v1, Vector2d v2)
	{
		return v1.x * v2.x + v1.y * v2.y;
	}
	/**
	 * This is method used to get dot product of this and another vector.
	 * @param v
	 * @return This returns dot product of this and another vector.
	 */
	public double dot(Vector2d v)
	{
		return dot(this,v);
	}
	/**
	 * This is method used to add two vectors together.
	 * @param v1
	 * @param v2
	 * @return This returns product of addition of two given vectors.
	 */
	public static Vector2d plus(Vector2d v1, Vector2d v2)
	{
		return new Vector2d(v1.x + v2.x, v1.y + v2.y);
	}
	/**
	 * This is method used to add vector to this vector.
	 * @param v
	 * @return This returns product of addition of two vectors.
	 */
	public Vector2d plus(Vector2d v)
	{
		this.x += v.x;
		this.y += v.y;
		
		return this;
	}
	/**
	 * This is method used to subtract two vectors.
	 * @param v1
	 * @param v2
	 * @return This returns product of subtraction of two given vectors.
	 */
	public static Vector2d minus(Vector2d v1, Vector2d v2)
	{
		return new Vector2d(v1.x - v2.x, v1.y - v2.y);
	}
	/**
	 * This is method used to subtract vector from this vector.
	 * @param v
	 * @return This returns product of subtraction of two vectors.
	 */
	public Vector2d minus(Vector2d v)
	{
		this.x -= v.x;
		this.y -= v.y;
		
		return this;
	}
	/**
	 * This is method used to multiply vector's components by given factor.
	 * @param v
	 * @param f
	 * @return This returns vector which components has been multiplied by a given factor.
	 */
	public static Vector2d factor(Vector2d v, double f)
	{
		return new Vector2d(v.x * f, v.y * f);
	}

	public static Vector2d factor(double f, Vector2d v)
	{
		return factor(v,f);
	}
	/**
	 * This is methood used to multiply vector's components by given factor.
	 * @param f
	 * @return  This returns vector which components has been multiplied by a given factor.
	 */
	public Vector2d factor(double f)
	{
		this.x *= f;
		this.y *= f;
		
		return this;
	}
	
	//====================================================
	/**
	 * This is method used to get vector which is counter-clockwise orthogonal to this vector.
	 * @return
	 */
	public Vector2d getLOrthogonal()
	{
		return new Vector2d(-y,x);
	}
	/**
	 * This is method used to get vector which is clockwise orthogonal to this vector.
	 * @return
	 */
	public Vector2d getROrthogonal()
	{
		return new Vector2d(y,-x);
	}
	/**
	 * This is method used to get opposite vector to this vector.
	 * @return
	 */	
	public Vector2d getOpposite()
	{
		return new Vector2d(-x,-y);
	}
	/**
	 * This is method used to get vector's reflection.
	 * @param mirror
	 * @return
	 */
	public Vector2d getReflection(Vector2d mirror)
	{	
		Vector2d norm_mirror = normalize(mirror);
		return minus(this, factor(2*dot(this,norm_mirror), norm_mirror));
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
		if(!(o instanceof Vector2d)) return false;
		
		Vector2d v = (Vector2d)o;
		
		if(v.x != this.x) return false;
		if(v.y != this.y) return false;
		
		return true;
	}
	
	public Vector2d()
	{
		x = 0;
		y = 0;
	}
	
	public Vector2d(Vector2d v)
	{
		x = v.x;
		y = v.y;
	}
	
	public Vector2d(double nx, double ny)
	{
		x = nx;
		y = ny;
	}
	
	public Vector2d(Point2d p1, Point2d p2)
	{
		x = p2.x() - p1.x();
		y = p2.y() - p1.y();
	}
	
	// think about necessity of Shape2d existence
	// eliminate nulls in geom_functions: '>' -> '>=' + other stuff
	// be careful with 0s in vector and point, they might get you

	// ok if low speed - no easy fix when speed is high -> collision vector R outside
	// doesn't occure with low speeds -> increasing framerate will fix the problem +double bounce - fix req
	// ok -> no bounce in case of double collide
	
	// getAngle <> setAngle - unification needed
	
	// ball penetrates paddle's sides - fix req
	// no bounce in case of corner collide - fix req
	// 'ball crawling' on borders - paddle, limits and the same problem for corner collide

	// removal of objects in loop is not pretty
}
