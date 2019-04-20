package game_objects;

public interface Movable 
{
	public Vector2d getMovement();
	public void setMovement(Vector2d movement); 
	
	public void setSpeed(double velocity);
	public void changeSpeed(double factor);
	
	public void move(Vector2d v);
	public void move();
}
