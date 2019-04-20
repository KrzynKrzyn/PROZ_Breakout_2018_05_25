package breakout_interface;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.cell.*;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.collections.*;
import breakout_manager.FileManager;
import breakout_manager.RecordManager;
import breakout_manager.RestrictedWorld;
import breakout_manager.WorldManager;
import javafx.animation.*;

public class GameControler
{
	private String nickname;
	private WorldManager manager;
	
	private Enviroment menu;
	private Enviroment selection;
	private Enviroment playing;
	private Enviroment scores;
	
	private Enviroment state;
	
	private void changeEnviroment(Enviroment e)
	{
		e.enterScene();
		this.state = e;
	}
	
	public Scene getState()
	{
		return state.getScene();
	}
	
	//-----------------------------------------------------------------------
	
	private abstract class Enviroment
	{
		protected Group root = new Group();
		protected Scene scene;
		
		abstract public void enterScene();
		
		public Scene getScene()
		{
			return scene;
		}
	}
	
	private class Menu extends Enviroment
	{
		public void enterScene()
		{
			
		}
		
		public Menu()
		{
			scene = new Scene(root,  400, 512);
			
	        TextField username = new TextField();
	        username.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
	        username.setPrefColumnCount(16);
	        username.setPrefWidth(scene.getWidth()/2);
	        username.setPrefHeight(scene.getHeight()/10);
	        username.setLayoutX(scene.getWidth()/4);
	        username.setLayoutY(scene.getHeight()/3.5);
	        
	        Text please_user = new Text("Username: ");
	        please_user.setFill(Color.BLACK);
	        please_user.setLayoutX(scene.getWidth()/4);
	        please_user.setLayoutY(username.getLayoutY() - 5);
			
			StackPane play_button = GameGraphics.createMenuButton(scene.getWidth()/4,
					username.getLayoutY() + 1.2*scene.getHeight()/8,
					scene.getWidth()/2, scene.getHeight()/8, Color.LIGHTGREY, "PLAY");
			
			StackPane score_button = GameGraphics.createMenuButton(scene.getWidth()/4,
					play_button.getLayoutY() + 1.2*scene.getHeight()/8,
					scene.getWidth()/2, scene.getHeight()/8, Color.LIGHTGREY, "SCORES");
			
			StackPane quit_button = GameGraphics.createMenuButton(scene.getWidth()/4 + scene.getWidth()/12,
					score_button.getLayoutY() + scene.getHeight()/5,
					scene.getWidth()/3, scene.getHeight()/16, Color.RED, "QUIT");
			
			StackPane title = GameGraphics.createTitle(0, scene.getHeight()/20, scene.getWidth(),
					scene.getHeight()/10, Color.BLACK, "BREAKOUT4");
			
	        play_button.setOnMouseClicked(new EventHandler<MouseEvent>() 
	        {
	            public void handle(MouseEvent me) 
	            {
	            	nickname = username.getText();
	            	manager.enableRecorder(nickname);
	                changeEnviroment(selection);
	            }
	        });
	        
	        score_button.setOnMouseClicked(new EventHandler<MouseEvent>() 
	        {
	            public void handle(MouseEvent me) 
	            {
	                changeEnviroment(scores);
	            }
	        });
	        
	        quit_button.setOnMouseClicked(new EventHandler<MouseEvent>() 
	        {
	            public void handle(MouseEvent me) 
	            {
	                Platform.exit();
	            }
	        });

			root.getChildren().add( please_user );
	        root.getChildren().add( username );
			root.getChildren().add( play_button );
			root.getChildren().add( score_button );
			root.getChildren().add( quit_button );
			root.getChildren().add( title );
		}
	}

	private class Playing extends Enviroment
	{
		AnimationTimer animation;
		
		public void enterScene()
		{
			root = new Group();
			scene = new Scene(root, manager.accessWorld().getX(), manager.accessWorld().getY());
	        
	        scene.setOnKeyPressed(
	                new EventHandler<KeyEvent>()
	                {
	                    public void handle(KeyEvent event)
	                    {
	                        if (event.getCode() == KeyCode.A || event.getCode() == KeyCode.LEFT) manager.accessWorld().paddleLeft();
	                        if (event.getCode() == KeyCode.D || event.getCode() == KeyCode.RIGHT) manager.accessWorld().paddleRight();
	                        if (event.getCode() == KeyCode.ENTER) manager.accessWorld().spawnBall();
	                        if (event.getCode() == KeyCode.R) manager.resetWorld();
	                        if (event.getCode() == KeyCode.ESCAPE) 
	                        { 
	                        	if(animation != null) animation.stop();
	                        	changeEnviroment(selection); 
	                        }
	                    }
	                });
		    
	        scene.setOnKeyReleased(
	                new EventHandler<KeyEvent>()
	                {
	                    public void handle(KeyEvent event)
	                    {
	                        if((event.getCode() == KeyCode.A || event.getCode() == KeyCode.LEFT) &&
	                        	manager.accessWorld().getPaddle().getMovement().equals(new game_objects.Vector2d(-manager.accessWorld().getPaddle().getSpeed(),0)))
	                        	manager.accessWorld().paddleStop();
	                        
	                        if((event.getCode() == KeyCode.D || event.getCode() == KeyCode.RIGHT) &&
	                        	manager.accessWorld().getPaddle().getMovement().equals(new game_objects.Vector2d(manager.accessWorld().getPaddle().getSpeed(),0)))
	                        	manager.accessWorld().paddleStop();
	                    }
	                });
	        
	        Canvas canvas = new Canvas(manager.accessWorld().getX(), manager.accessWorld().getY());
	        GraphicsContext gc = canvas.getGraphicsContext2D();
	        
	        animation = new AnimationTimer()
	        {	    
		        public void handle(long currentNanoTime)
		        {	
		        	RestrictedWorld world = manager.accessWorld();
		        	
		        	GameGraphics.draw(gc, world);
		            world.update();world.update();world.update();
		        }
		    };
	        
		    animation.start();
		    
	        root.getChildren().add(canvas);		
		}
		
		public Playing()
		{

		}
	}
	
	private class Selection extends Enviroment
	{
		ListView<String> maps = new ListView<String>();
		
		public void enterScene()
		{
			ObservableList<String> mapList = FXCollections.<String>observableArrayList();
			mapList.addAll(FileManager.mapNames());
			
			maps.getItems().clear();
			maps.getItems().addAll(mapList);
			maps.getSelectionModel().select(0);
		}
		
		public Selection()
		{
			scene = new Scene(root,  400, 512);
			
			Button play_button = new Button("Select");
			play_button.setPrefHeight(scene.getHeight()/16);
			play_button.setPrefWidth(scene.getWidth()/5);
	        play_button.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
	        play_button.setLayoutY(scene.getHeight()/2 - play_button.getPrefHeight());
			play_button.setLayoutX(3*scene.getWidth()/4);
			
			Button back_button = new Button("Back");
			back_button.setPrefHeight(scene.getHeight()/16);
			back_button.setPrefWidth(scene.getWidth()/5);
			back_button.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
			back_button.setLayoutY(scene.getHeight()/2 + play_button.getPrefHeight());
			back_button.setLayoutX(3*scene.getWidth()/4);

			ObservableList<String> mapList = FXCollections.<String>observableArrayList();
			mapList.addAll(FileManager.mapNames());
			
			maps.getItems().addAll(mapList);
			maps.setPrefWidth(3*scene.getWidth()/4 - 0.05*scene.getWidth());
			maps.setPrefHeight(scene.getHeight());
			maps.getSelectionModel().select(0);
			
			play_button.setOnMouseClicked(new EventHandler<MouseEvent>() 
	        {
	            public void handle(MouseEvent me) 
	            {
	                manager.setupWorld( FileManager.loadMap( maps.getSelectionModel().getSelectedItem() ) );
	                changeEnviroment(playing);
	            }
	        });

			back_button.setOnMouseClicked(new EventHandler<MouseEvent>() 
	        {
	            public void handle(MouseEvent me) 
	            {
	            	manager.disableRecorder();
	                changeEnviroment(menu);
	            }
	        });
			
			root.getChildren().add( maps );
			root.getChildren().add( play_button );
			root.getChildren().add( back_button );
		}
	}
	
	private class Scores extends Enviroment
	{
		TableView<RecordManager.Record> table = new TableView<RecordManager.Record>();
		
		public void enterScene()
		{
	        ObservableList<RecordManager.Record> recordList = FXCollections.<RecordManager.Record>observableArrayList();
			recordList.addAll(RecordManager.loadRecords());
	        
			table.getItems().clear();
			table.getItems().addAll(recordList);
		}
		
		public Scores()
		{
			scene = new Scene(root,  512, 400);
			
			Button back_button = new Button("Back");
			back_button.setPrefHeight(scene.getHeight()/16);
			back_button.setPrefWidth(scene.getWidth()/5);
			back_button.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
			
			back_button.setLayoutY(0.9*scene.getHeight());
			back_button.setLayoutX(scene.getWidth()/2 - scene.getWidth()/10);
			
			back_button.setOnMouseClicked(new EventHandler<MouseEvent>() 
	        {
	            public void handle(MouseEvent me) 
	            {
	                changeEnviroment(menu);
	            }
	        });
			
			TableColumn name = new TableColumn("Username");
	        TableColumn score = new TableColumn("Score");
	        TableColumn start = new TableColumn("Started playing");
	        TableColumn time_played = new TableColumn("Play time (seconds)");
	        
	        name.setPrefWidth(scene.getWidth()/4);
	        score.setPrefWidth(scene.getWidth()/8);
	        start.setPrefWidth(0.49*scene.getWidth());
	        time_played.setPrefWidth(scene.getWidth()/8);
	        
	        table.getColumns().addAll(name, score, start, time_played);
	        
			table.setPrefWidth(scene.getWidth());
			table.setPrefHeight(0.85*scene.getHeight());
			
	        ObservableList<RecordManager.Record> recordList = FXCollections.<RecordManager.Record>observableArrayList();
			recordList.addAll(RecordManager.loadRecords());
	        
			name.setCellValueFactory(
	                new PropertyValueFactory<RecordManager.Record, String>("user_name"));

			score.setCellValueFactory(
	                new PropertyValueFactory<RecordManager.Record, String>("score"));
	
			start.setCellValueFactory(
	                new PropertyValueFactory<RecordManager.Record, String>("start_time"));
	
			time_played.setCellValueFactory(
	                new PropertyValueFactory<RecordManager.Record, String>("time_played"));
			
			table.getItems().addAll(recordList);
			
			root.getChildren().add(table);
			root.getChildren().add( back_button );
		}
	}
	
	//-----------------------------------------------------------------------
	public GameControler()
	{
		this.manager = new WorldManager();
		
		menu = new Menu();
		playing = new Playing();
		selection = new Selection();
		scores = new Scores();
		
		this.state = menu;
	}
}
