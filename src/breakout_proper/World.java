package breakout_proper;

import game_objects.*;
import java.util.*;

public class World 
{
	private Rectangle2d limits;
	
	private BlockSet blockset;
	private BallSet balls;	//change to factory only?
	private BonusDatabase bonusdb;
	private BonusSet bonuses; //change to factory only?
	private Paddle paddle;
	
	private World.Chances chances;
	
	public double getX() { return limits.getRLimit(); }
	public double getY() { return limits.getDLimit(); }
	
	final protected BlockSet getBlockSet() { return blockset; }
	final protected BallSet getBallSet() { return balls; }
	final protected BonusSet getBonusSet() { return bonuses; }
	
	public List<Block> getBlocks() { return blockset.getBlockList(); }
	public List<Ball> getBalls() { return balls.getCurrentBalls(); }
	public List<Bonus> getBonuses() { return bonuses.getCurrentActive(); }
	public Paddle getPaddle() { return paddle; }
	
	private List<Observer> score = new ArrayList<Observer>();	//arraylist etc.
	
	public void attachScoreTracker(Observer st)
	{
		score.add(st);
	}
	
	public void detachScoreTracker(Observer st)
	{
		score.remove(st);
	}
//----------------------------------------

	private void init_bigBalls(int chance)
	{
		bonusdb.insertBonus(chance, () -> { //flawed
			List<Ball> copy = balls.getCurrentBalls();
			
			for(Ball b : copy) b.setRadius( b.getRadius() * 1.2 );
			
			Timer timer = new Timer();
			
			timer.schedule(new TimerTask() {
				@Override
				public void run() 
				{ 
					for(Ball b : copy) b.setRadius( b.getRadius() / 1.2 );
				}
			}, 8*1000);
		});
	}
	
	private void init_bigPaddle(int chance)
	{
		bonusdb.insertBonus(chance, () -> {
			paddle.getPoint().setX( paddle.getLLimit() - 0.1*paddle.getWidth() );
			paddle.setWidth( 1.2*paddle.getWidth() );
			
			Timer timer = new Timer();
			
			timer.schedule(new TimerTask() {
				@Override
				public void run() 
				{
					paddle.getPoint().setX( paddle.getLLimit() + 0.1*paddle.getWidth() );
					paddle.setWidth( paddle.getWidth()/1.2 );
				}
			}, 8*1000);
		});
	}
	
	private void init_fastBalls(int chance)
	{
		bonusdb.insertBonus(chance, () -> { //flawed
			List<Ball> copy = balls.getCurrentBalls();
			
			for(Ball b : copy) b.getMovement().factor(1.2);
			
			Timer timer = new Timer();
			
			timer.schedule(new TimerTask() {
				@Override
				public void run() 
				{ 
					for(Ball b : copy) b.getMovement().factor(0.8333);
				}
			}, 8*1000);
		});
	}
	
	private void init_fastPaddle(int chance)
	{
		bonusdb.insertBonus(chance, () -> {
			paddle.changeSpeed(1.5);
			
			Timer timer = new Timer();
			
			timer.schedule(new TimerTask() {
				@Override
				public void run() { paddle.changeSpeed(1/1.5); }
			}, 8*1000);
		});
	}
	
	private void init_threeBalls(int chance)
	{
		bonusdb.insertBonus(chance, () -> {
			for(int i=0;i<3;i++)
			{
				Ball b = balls.spawnBall(paddle.getLLimit() + paddle.getWidth()/2,
										 paddle.getULimit() - paddle.getHeight()/2 - balls.getBlueR());
				b.getMovement().setAngle(3*Math.PI/4 + i*Math.PI/4);
			}
		});
	}
	
	private void init_burstBalls(int chance)
	{
		bonusdb.insertBonus(chance, () -> {
			for(int i=0;i<10;i++)
			{
				Ball b = balls.spawnBall(paddle.getLLimit() + paddle.getWidth()/2,
										 paddle.getULimit() - paddle.getHeight()/2 - balls.getBlueR());
				b.getMovement().setAngle(3*Math.PI/4 + i*Math.PI/18);
				b.setRadius( balls.getBlueR()/2 );
				b.getMovement().factor(2);
			}
		});	
	}
	
	private void init_shootBalls(int chance)
	{
		bonusdb.insertBonus(chance, () -> {
			
			Timer timer = new Timer();
			Timer endtimer = new Timer();
			
			timer.scheduleAtFixedRate(new TimerTask() {
				@Override
				public void run() 
				{
					Ball b1 = balls.spawnBall(paddle.getLLimit(),
							 paddle.getULimit() - paddle.getHeight()/2 - balls.getBlueR());
	
					b1.setDestructible();
					//b1.setDurability(1);
					
					b1.setRadius( balls.getBlueR()/2 );
					b1.getMovement().factor(2);
					
					Ball b2 = balls.spawnBall(paddle.getRLimit(),
							 paddle.getULimit() - paddle.getHeight()/2 - balls.getBlueR());
					
					b2.setDestructible();
					//b2.setDurability(1);
					
					b2.setRadius( balls.getBlueR()/2 );
					b2.getMovement().factor(2);
				}
			}, 0, 200);
			
			endtimer.schedule(new TimerTask() {
				@Override
				public void run() { timer.cancel(); }
			}, 1000);
		});	
	}
	
	public static class Chances
	{
		int nothing_chance;
		int bigballs;
		int bigpaddle;
		int burstballs;
		int fastballs;
		int fastpaddle;
		int shootballs;
		int threeballs;
		
		public Chances(Chances other)
		{
			this.nothing_chance = other.nothing_chance;
			this.bigballs = other.bigballs;
			this.bigpaddle = other.bigpaddle;
			this.burstballs = other.burstballs;
			this.fastballs = other.fastballs;
			this.fastpaddle = other.fastpaddle;
			this.shootballs = other.shootballs;
			this.threeballs = other.threeballs;
		}
		
		public Chances(int nothing_chance, int bigballs, int bigpaddle, int burstballs, int fastballs, int fastpaddle,
				int shootballs, int threeballs) 
		{
			this.nothing_chance = nothing_chance;
			this.bigballs = bigballs;
			this.bigpaddle = bigpaddle;
			this.burstballs = burstballs;
			this.fastballs = fastballs;
			this.fastpaddle = fastpaddle;
			this.shootballs = shootballs;
			this.threeballs = threeballs;
		}
	}
	
	private void initBonuses(World.Chances chance)
	{
		this.init_bigBalls(chance.bigballs);
		this.init_bigPaddle(chance.bigpaddle);
		this.init_burstBalls(chance.burstballs);
		this.init_fastBalls(chance.fastballs);
		this.init_fastPaddle(chance.fastpaddle);
		this.init_shootBalls(chance.shootballs);
		this.init_threeBalls(chance.threeballs);
	}
//----------------------------------------------------------------------------------------------------------------
	private Vector2d handleCollision(Rectangle2d limit, Ball ball)
	{
		if(limits.doesContain(ball)) return null;
			
		if(ball.bouncevector(limits).equals(new Vector2d(0,1)))	//possibly move this bit to updateBall
		{
			balls.removeBall(ball);
			return null;
		}

		return ball.bouncevector(limits).getOpposite();
	}
	
	private Vector2d handleCollision(Block block, Ball ball)
	{
		if(!ball.doesCollide(block)) return null;
		
		Vector2d ret = ball.bouncevector(block);
		
		if(block.isDestructible()) 
		{	
			block.decDurability();
		
			if(block.getDurablility() == 0)
			{
				for(Observer o : score) o.update();
				//delete block
				
				Bonus.Function gamble = bonusdb.gamble();
				if(gamble != null) 
					bonuses.spawnBonus(block.getLLimit(), block.getULimit(), gamble);
			}
		}
		
		return ret;
	}

	private Vector2d handleCollision(Paddle paddle, Ball ball)
	{
		if(!ball.doesCollide(paddle)) return null;
		
		if(paddle.collisionVector(ball).equals(new Vector2d(0,-1))) 
		{
			ball.getMovement().setAngle( paddle.getBounceAngle(ball) );
			return null;
		}
		
		return ball.bouncevector(paddle); //b.bounce(paddle);
	}
//----------------------------------------------------------------------------------------------------------------	
	private void updateBall(int i)
	{
		List<Vector2d> bounce_vectors = new ArrayList<Vector2d>();
		Ball b = balls.getBall(i);
		
		b.move();
		
		Vector2d check = null;
		
		check = handleCollision(limits, b);
		if(check != null && !bounce_vectors.contains(check)) bounce_vectors.add( check );
		
		check = handleCollision(paddle, b);
		if(check != null && !bounce_vectors.contains(check)) bounce_vectors.add( check );
		
		List<Block> vicinity = blockset.getVicinity(b.getPoint(),b.getRadius());
		for(Block vc : vicinity)
		{
			check = handleCollision(vc, b);
			if(check != null && !bounce_vectors.contains(check)) bounce_vectors.add( check );
		}

		for(Vector2d vec : bounce_vectors)
		{
			b.move( Vector2d.factor(vec, b.getMovement().getLength()) ); //resurface
			b.bounce( vec ); //bounce
			if(b.isDestructible())
			{
				b.decDurability();
				if(b.getDurablility() <= 0)
				{
					balls.removeBall(b);
					return;
				}
			}
		}
	}
	
	private void updateBonus(int i)
	{
		Bonus b = bonuses.getActive(i);
		
		b.move();
		
		if(!limits.doesContain(b)) 
		{
			bonuses.removeActive(b);//risky
			return;
		}
		
		if(b.doesCollide(paddle)) 
		{
			b.activate();
			bonuses.removeActive(b);
			return;
		}		
	}

	private void updatePaddle()
	{
		paddle.move();
		for(int j=0;j<balls.size();j++) 
			if(balls.getBall(j).doesCollide(paddle)) balls.getBall(j).move(paddle.getMovement()); //just a test
	}
	/**
	 * Updates game objects. (move, bounce, remove etc.)
	 */
	public void update()
	{
		for(int i=balls.size()-1;i>=0;--i) updateBall(i);
		updatePaddle();
		for(int i=bonuses.size()-1;i>=0;--i) updateBonus(i);
	}
	
//----------------------------------------

	public World(World other)
	{
		this.blockset = new BlockSet(other.blockset);
		this.paddle = new Paddle(other.paddle);
		this.balls = new BallSet(other.balls);
		this.bonuses = new BonusSet(other.bonuses);
		
		this.limits = new Rectangle2d(other.limits);
		
		this.chances = new World.Chances(other.chances);
		this.bonusdb = new BonusDatabase(other.chances.nothing_chance);
		this.initBonuses(other.chances);
	}
	
	public World(BlockSet map, Paddle paddle, BallSet balls, BonusSet bonuses, World.Chances chance)
	{
		this.blockset = map;
		this.paddle = paddle;
		this.balls = balls;
		this.bonuses = bonuses;
		
		this.limits = new Rectangle2d(0, 0, blockset.getXLim(), blockset.getYLim());
		
		this.chances = chance;
		this.bonusdb = new BonusDatabase(chance.nothing_chance);
		this.initBonuses(chance);
	}
	//shred constructors below
	/*public World(BlockSet map, int paddlespeed, Vector2d ball_initspeed, Vector2d bonus_initspeed, World.Chances chance)
	{
		blockset = map;
		
		limits = new Rectangle2d(0, 0, blockset.getXLim(), blockset.getYLim());

		paddle = new Paddle(limits.getRLimit()/2 - 1.5*blockset.getXCell(),
							28*limits.getDLimit()/32,
							3*blockset.getXCell(),
							blockset.getYCell(),
							paddlespeed);
		
		balls = new BallSet( Math.min(blockset.getXCell(), blockset.getYCell())/2, ball_initspeed ); // 0.1, -2
		
		bonusdb = new BonusDatabase(chance.nothing_chance);
		bonuses = new BonusSet( blockset.getXCell(), blockset.getYCell(), bonus_initspeed );
		
		this.initBonuses(chance);
				
	}
	
	public World(BlockSet bs)
	{
		this(bs, 2, new Vector2d(0.1, -1.75), new Vector2d(0,1), new World.Chances(75, 5, 5, 1, 3, 3, 4, 5));
	}

	public World(int x, int y, int col, int row)
	{
		this(new BlockSet(x,y,col,row));
	}*/
}
