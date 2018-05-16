package view;

import java.util.Observable;
import java.util.Observer;

import controller.Controller;
import controller.ControllerImp;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import model.Access;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * 
 * @author <a href="https://docs.oracle.com/javafx/2/get_started/css.htm">Fancy Forms with JavaFX CSS</a>
 * @author Shahriar (Shawn) Emami
 */
public class Login extends Application implements Observer{
	
	private Controller c;
	private Text actiontarget;
	
	/**
	 * this function runs before start
	 */
	@Override
	public void init() {
		c = new ControllerImp();
		Access m = new Access("Shawn", "123");
		c.setModel(m);
		c.addObserver(this);
	}
	
	@Override
	public void start( Stage primaryStage){
		
		primaryStage.setTitle( "JavaFX Welcome");
		GridPane grid = new GridPane();
		grid.setAlignment( Pos.CENTER);
		grid.setHgap( 10);
		grid.setVgap( 10);
		grid.setPadding( new Insets( 25, 25, 25, 25));

		Text scenetitle = new Text( "Welcome");
		scenetitle.setId("welcome-text");
		scenetitle.setFont( Font.font( "Tahoma", FontWeight.NORMAL, 20));
		grid.add( scenetitle, 0, 0, 2, 1);

		Label userName = new Label( "User Name:");
		grid.add( userName, 0, 1);

		TextField userTextField = new TextField();
		grid.add( userTextField, 1, 1);

		Label pw = new Label( "Password:");
		grid.add( pw, 0, 2);

		PasswordField pwBox = new PasswordField();
		grid.add( pwBox, 1, 2);
		
		Label userPass = new Label( "User: Shawn, Pass:123");
		grid.add( userPass, 1, 3, 1, 1);

		Button btn = new Button( "Sign in");
		HBox hbBtn = new HBox( 10);
		hbBtn.setAlignment( Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add( btn);
		grid.add( hbBtn, 1, 5);

		actiontarget = new Text();
		actiontarget.setId("actiontarget");
		grid.add( actiontarget, 1, 7);
		//same as creating a new EventHandler< ActionEvent>()
		btn.setOnAction( e->{
			c.login( userTextField.getText(), pwBox.getText());
		});

		Scene scene = new Scene( grid, 325, 325);
		scene.getStylesheets().add( Login.class.getResource( "/css/login.css").toExternalForm());
		primaryStage.setScene( scene);
		primaryStage.show();
	}

	@Override
	public void update(Observable o, Object arg) {
		if( o instanceof Access){
			String[] info = (String[]) arg;
			if(info[1].equals("has access")){
				actiontarget.setFill( Color.LIME);
			}else{
				actiontarget.setFill( Color.FIREBRICK);
			}
			actiontarget.setText( info[0]+" "+info[1]);
		}
	}

	public static void main( String[] args){
		launch( args);
	}
}
