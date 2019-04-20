package breakout_interface;

import breakout_manager.RestrictedWorld;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.canvas.*;


public class GameGraphics extends Application
{
	public static StackPane createTitle(double x, double y, double width, double height, Color color, String label)
	{
		StackPane stack = new StackPane();
		
        stack.setLayoutX(x);
        stack.setLayoutY(y);
        
		Rectangle rectangle = new Rectangle(width, height);
		rectangle.setOpacity(0);
		
        Text text = new Text(label);
        double fontsize = (rectangle.getWidth() > rectangle.getHeight() ) ? rectangle.getWidth() : rectangle.getHeight();
        text.setFont(Font.font("Verdana", FontWeight.BOLD, fontsize/label.length()));
        text.setFill(color);
        
        stack.getChildren().addAll(rectangle, text);
        
        return stack;
	}
	
	public static StackPane createMenuButton(double x, double y, double width, double height, Color color, String label)
	{
		StackPane stack = new StackPane();
		
        stack.setLayoutX(x);
        stack.setLayoutY(y);
		
		Rectangle rectangle = new Rectangle(width, height);
		rectangle.setFill(color);
		
        stack.setOnMouseEntered(new EventHandler<MouseEvent>() 
        {
            public void handle(MouseEvent me) 
            {
                rectangle.setFill(color.darker());
            }
        });
		
        stack.setOnMouseExited(new EventHandler<MouseEvent>() 
        {
            public void handle(MouseEvent me) 
            {
                rectangle.setFill(color);
            }
        });
        
        Text text = new Text(label);
        double fontsize = (rectangle.getWidth()/8 < rectangle.getHeight() ) ? rectangle.getWidth()/8 : rectangle.getHeight();
        text.setFont(Font.font("Verdana", FontWeight.BOLD, fontsize));
        text.setFill(Color.WHITE);
        
        stack.getChildren().addAll(rectangle, text);
        
        return stack;
	}
	
	public static void draw(GraphicsContext gc, game_objects.Rectangle2d obj, Color c)
	{
		gc.setFill(c);
        gc.fillRect(obj.getLLimit()+1, obj.getULimit()+1, obj.getWidth()-2, obj.getHeight()-2);
	}
	
	public static void draw(GraphicsContext gc, game_objects.Circle2d obj, Color c)
	{
		gc.setFill(c);
		double x = obj.getPoint().x();
     	double y = obj.getPoint().y();
     	double r = obj.getRadius();
		gc.fillOval(x-r, y-r, 2*r, 2*r);
	}
	
	public static void draw(GraphicsContext gc, breakout_proper.Block obj)
	{
		if(!obj.isDestructible()) gc.setFill(Color.BLACK);
		else
		{
			int c = (50*obj.getDurablility() > 255) ? 255 : 50*obj.getDurablility();
			gc.setFill(Color.rgb(150, 255-c, 150));
		}
		
        gc.fillRect(obj.getLLimit()+1, obj.getULimit()+1, obj.getWidth()-2, obj.getHeight()-2);
	}

	public static void draw(GraphicsContext gc, breakout_proper.Ball obj)
	{
		int c = (obj.getMovement().getLength() > 3) ? 175 : 0;
		gc.setFill(Color.rgb(255, c, 0));
		
		double x = obj.getPoint().x();
     	double y = obj.getPoint().y();
     	double r = obj.getRadius();
		gc.fillOval(x-r, y-r, 2*r, 2*r);	
	}
	
	public static void draw(GraphicsContext gc, breakout_proper.Bonus obj)
	{
		gc.setFill(Color.LIGHTSKYBLUE);
        gc.fillRect(obj.getLLimit()+1, obj.getULimit()+1, obj.getWidth()-2, obj.getHeight()-2);	
	}
	
	public static void draw(GraphicsContext gc, breakout_proper.Paddle obj)
	{
		int c = (obj.getSpeed() > 2.5) ? 200 : 0;
		gc.setFill(Color.rgb(c, 0, 0));
		
        gc.fillRect(obj.getLLimit(), obj.getULimit(), obj.getWidth(), obj.getHeight());
	}
	
	public static void draw(GraphicsContext gc, RestrictedWorld world)
	{
    	gc.setFill(Color.WHITE);
    	gc.fillRect(0, 0, world.getX(), world.getY());
    	
        for(int i=0;i<world.getBlocks().size();i++) if(world.getBlocks().get(i).getDurablility() > 0) draw(gc, world.getBlocks().get(i));
		for(int i=0;i<world.getBalls().size();i++) draw(gc, world.getBalls().get(i));
        for(int i=0;i<world.getBonuses().size();i++) draw(gc, world.getBonuses().get(i));
        draw(gc, world.getPaddle());
        
        gc.setFill(Color.BLACK);
        gc.fillText("Score", 10, world.getY()-25);
        gc.fillText(String.valueOf((int)world.getScore()),20,world.getY()-10);
        
        gc.setFill(Color.BLACK);
        gc.fillText("Lifes", world.getX()-30, world.getY()-25);
        gc.fillText(String.valueOf((int)world.getLifes()), world.getX()-20, world.getY()-10);
	}
	
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) 
    {
    	GameControler gc = new GameControler();
    	
    	primaryStage.initStyle(StageStyle.UTILITY);
    	
    	primaryStage.setTitle("Breakout4");
        primaryStage.setScene(gc.getState());
        primaryStage.setResizable(false);
        
	    new AnimationTimer()	// only for debuging - to be changed later on
	    {	    	
	        public void handle(long currentNanoTime)
	        {	
	        	 primaryStage.setScene(gc.getState());
	        }
	    }.start();
        
        primaryStage.show();
    }
}
