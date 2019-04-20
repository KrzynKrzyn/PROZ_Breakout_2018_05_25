package game_objects;

public interface Shape2d //this might be goofy - consider removing it
{
	public boolean doesContain(Point2d gobj);
	public boolean doesContain(Shape2d gobj);
	public boolean doesCollide(Shape2d gobj);
	public Vector2d collisionVector(Shape2d gobj);
}
