package game_objects;

final public class Collision2d
{
// R-R

	public static boolean doesCollide(Rectangle2d gobj1, Rectangle2d gobj2)
	{		
		if(gobj2.getRLimit() < gobj1.getLLimit()) return false;
		if(gobj1.getRLimit() < gobj2.getLLimit()) return false;
		
		if(gobj2.getDLimit() < gobj1.getULimit()) return false;
		if(gobj1.getDLimit() < gobj2.getULimit()) return false;
		
		return true;
	}
	
	public static boolean doesContain(Rectangle2d limit, Rectangle2d gobj)
	{
		if(gobj.getLLimit() < limit.getLLimit()) return false;
		if(gobj.getRLimit() > limit.getRLimit()) return false;

		if(gobj.getULimit() < limit.getULimit()) return false;
		if(gobj.getDLimit() > limit.getDLimit()) return false;
		
		return true;
	}
	
	public static boolean doesContain(Rectangle2d limit, Point2d gobj)
	{
		if(limit.getLLimit() > gobj.x()) return false;
		if(limit.getRLimit() < gobj.x()) return false;
		if(limit.getULimit() > gobj.y()) return false;
		if(limit.getDLimit() < gobj.y()) return false;
		
		return true;
	}	
	
	//====================================================
// C-C
	public static boolean doesCollide(Circle2d gobj1, Circle2d gobj2)
	{
		double dist = gobj1.getRadius() + gobj2.getRadius();
		
		if(gobj1.getPoint().getDistanceSq(gobj2.getPoint()) > dist*dist) return false;
		
		return true;
	}
	
	public static boolean doesContain(Circle2d limit, Circle2d gobj)
	{
		double distance = limit.getPoint().getDistance( gobj.getPoint() );
		
		if(distance + gobj.getRadius() > limit.getRadius()) return false;
		
		return true;
	}
	
	public static boolean doesContain(Circle2d limit, Point2d gobj)
	{
		if(gobj.getDistance(limit.getPoint()) > limit.getRadius()) return false;
		
		return true;
	}	
	//====================================================
// C-R
	public static boolean doesCollide(Rectangle2d gobj1, Circle2d gobj2)
	{
		Point2d dist_point = gobj1.getNearestPoint(gobj2.getPoint());
		
		//if(gobj1.doesContain(gobj2)) return true;
		if(dist_point.getDistanceSq(gobj2.getPoint()) > gobj2.getRadius()*gobj2.getRadius() ) return false;
		
		return true;
	}
	
	public static boolean doesCollide(Circle2d gobj1, Rectangle2d gobj2)
	{
		return doesCollide(gobj2, gobj1);
	}
	
	public static boolean doesContain(Rectangle2d limit, Circle2d gobj)
	{
		if(gobj.getPoint().x() - gobj.getRadius() < limit.getLLimit()) return false;
		if(gobj.getPoint().x() + gobj.getRadius() > limit.getRLimit()) return false;

		if(gobj.getPoint().y() - gobj.getRadius() < limit.getULimit()) return false;
		if(gobj.getPoint().y() + gobj.getRadius() > limit.getDLimit()) return false;

		return true;
	}
	
	public static boolean doesContain(Circle2d limit, Rectangle2d gobj)
	{
		if(!doesContain(limit, new Point2d(gobj.getLLimit(), gobj.getULimit()) )) return false;
		if(!doesContain(limit, new Point2d(gobj.getLLimit(), gobj.getDLimit()) )) return false;
		if(!doesContain(limit, new Point2d(gobj.getRLimit(), gobj.getULimit()) )) return false;
		if(!doesContain(limit, new Point2d(gobj.getRLimit(), gobj.getDLimit()) )) return false;
		
		return true;
	}
	
	//====================================================
//Collision vectors R
	/**
	 * This method returns vector that is orthogonal to surface where collision occured.
	 * @param fixed
	 * @param gobj
	 * @return
	 */
	public static Vector2d collisionVector(Rectangle2d fixed, Rectangle2d gobj)
	{
		return Collision2d.collisionVector(fixed, gobj.getMiddlePoint());
	}
	/**
	 * This method returns vector that is orthogonal to surface where collision occured.
	 * @param fixed
	 * @param gobj
	 * @return
	 */
	public static Vector2d collisionVector(Rectangle2d fixed, Circle2d gobj)
	{
		return Collision2d.collisionVector(fixed, gobj.getPoint());
	}
	/**
	 * This method returns vector that is orthogonal to surface where collision occured.
	 * @param fixed
	 * @param gobj
	 * @return
	 */
	public static Vector2d collisionVector(Rectangle2d fixed, Point2d gobj)	//bleurgh: '>' -> '>='
	{
		if(Collision2d.doesContain(fixed,gobj))
			return Collision2d.collisionVectorInside(fixed, gobj);
		else 
			return Collision2d.collisionVectorOutside(fixed, gobj);
	}
	
	private static Vector2d collisionVectorInside(Rectangle2d fixed, Point2d gobj)
	{
		double 	r_dist = Math.abs(fixed.getRLimit() - gobj.x()),
				l_dist = Math.abs(fixed.getLLimit() - gobj.x()),
				d_dist = Math.abs(fixed.getDLimit() - gobj.y()),
				u_dist = Math.abs(fixed.getULimit() - gobj.y());
		
		double 	min_dist = Math.min( Math.min(r_dist, l_dist), Math.min(d_dist, u_dist) );
		
		if(min_dist == r_dist) return new Vector2d(1,0);
		if(min_dist == l_dist) return new Vector2d(-1,0);
		if(min_dist == d_dist) return new Vector2d(0,1);
		if(min_dist == u_dist) return new Vector2d(0,-1);
		
		return null;
	}
	
	private static Vector2d collisionVectorOutside(Rectangle2d fixed, Point2d gobj)
	{													  
		Vector2d c_p = new Vector2d(fixed.getNearestPoint(gobj), gobj).normalize();
		double cp_angle = c_p.getAngle();
		
		if(gobj.x() > fixed.getRLimit() && Math.abs(cp_angle) <= Math.PI/4) return new Vector2d(1,0);
		if(gobj.x() < fixed.getLLimit() && Math.abs(cp_angle) >= 3*Math.PI/4) return new Vector2d(-1,0);
		if(gobj.y() > fixed.getDLimit() && cp_angle >= 0) return new Vector2d(0,1);
		if(gobj.y() < fixed.getULimit() && cp_angle <= 0) return new Vector2d(0,-1);
	
		return null;
	}
	
	//====================================================
//Collision vectors C
	/**
	 * This method returns vector that is orthogonal to surface where collision occured.
	 * @param fixed
	 * @param gobj
	 * @return
	 */
	public static Vector2d collisionVector(Circle2d fixed, Rectangle2d gobj)
	{
		return Collision2d.collisionVector(fixed, gobj.getMiddlePoint());
	}
	/**
	 * This method returns vector that is orthogonal to surface where collision occured.
	 * @param fixed
	 * @param gobj
	 * @return
	 */
	public static Vector2d collisionVector(Circle2d fixed, Circle2d gobj)
	{
		return Collision2d.collisionVector(fixed, gobj.getPoint());
	}
	/**
	 * This method returns vector that is orthogonal to surface where collision occured.
	 * @param fixed
	 * @param gobj
	 * @return
	 */
	public static Vector2d collisionVector(Circle2d fixed, Point2d gobj)
	{
		Vector2d ret = new Vector2d(fixed.getPoint(), gobj);
		
		return ret.normalize();
	}
	
	//====================================================
	
	private Collision2d() {}
}
