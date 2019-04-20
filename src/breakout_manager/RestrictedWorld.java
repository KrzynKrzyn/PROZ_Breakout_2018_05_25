package breakout_manager;

import breakout_proper.*;

public class RestrictedWorld extends World
{
	private int used_ball_limit;
	
	private int used_ball_tracker;
	private Tracker score_tracker;
	
	private abstract class Tracker implements breakout_proper.Observer
	{
		int number;
		
		public Tracker(int n) { number = n; }		
	}
	
	//-------------------------------------------------------------------------------
	
	public int getScore()
	{
		return score_tracker.number;
	}
	
	public int getUsedBalls()
	{
		return used_ball_tracker;
	}
	
	public int getUsedBallsLimit()
	{
		return used_ball_limit;
	}
	
	public int getLifes()
	{
		return used_ball_limit - used_ball_tracker;
	}
	
	//------------------------------------------------------------------
	
	public boolean spawnBall()
	{
		if(this.getBalls().size() != 0 || this.getBonuses().size() != 0) return false;
		if(used_ball_tracker >= used_ball_limit) return false;
		
		this.getBallSet().spawnBall(this.getPaddle().getLLimit() + this.getPaddle().getWidth()/2,
									this.getPaddle().getULimit() - this.getPaddle().getHeight()/2 - this.getBallSet().getBlueR());
		++used_ball_tracker;
		
		return true;
	}
	
	public void paddleLeft()
	{
		this.getPaddle().goLeft();
	}
	
	public void paddleRight()
	{
		this.getPaddle().goRight();
	}
	
	public void paddleStop()
	{
		this.getPaddle().goNowhere();
	}
	
	//------------------------------------------------------------------
	
	public RestrictedWorld(RestrictedWorld other)
	{
		super(other);
		
		this.used_ball_limit = other.used_ball_limit;
		this.used_ball_tracker = other.used_ball_tracker;
		this.score_tracker = new Tracker(other.score_tracker.number)
		{
			public void update() { ++number; }
		};
		
		this.attachScoreTracker(this.score_tracker);
	}
	
	public RestrictedWorld(BlockSet map, Paddle paddle, BallSet balls, BonusSet bonuses, World.Chances chance, int lifes)
	{
		super(map,paddle,balls,bonuses,chance);
		
		used_ball_limit = lifes;
		used_ball_tracker = 0;
		score_tracker = new Tracker(0)
		{
			public void update() { ++number; }
		};
		
		this.attachScoreTracker(score_tracker);
	}
	//shred everything below
	/*public RestrictedWorld(BlockSet bs, int lifes)
	{
		super(bs);
		
		used_ball_limit = lifes;
		used_ball_tracker = 0;
		score_tracker = new Tracker(0)
		{
			public void update() { ++number; }
		};
		
		this.attachScoreTracker(score_tracker);
	}
	
	public RestrictedWorld(int x, int y, int col, int row, int lifes)
	{
		this(new BlockSet(x,y,col,row), lifes);
	}*/
}
