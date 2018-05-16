package simulator;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;
import elevator.ElevatorImp;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ElevatorApplication extends Application implements Observer {

	private Label[] floors;
	private static final int FLOOR_COUNT = 21;
	private ElevatorAnimator animator;
	private Simulator simulator;
	private TextField textfieldCF = new TextField();
	private TextField textfieldTF = new TextField();
	private TextField textfieldPU = new TextField();
	private Label labelCF = new Label();
	private Label labelTF = new Label();
	private Label labelPU = new Label();

	@Override
	public void init() throws Exception {

		super.init();

		simulator = new Simulator(this);
		animator = new ElevatorAnimator();
		floors = new Label[FLOOR_COUNT];
		for (int i = 0; i < floors.length; i++) {
			floors[i] = new Label();
			floors[i].setId("empty");
		}
		floors[0].setId("elevator");

	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		GridPane main = new GridPane();

		main.add(createElevatorFloors(), 0, 0);
		Scene scene = new Scene(main, Color.LIGHTGRAY);
		scene.getStylesheets().add(ElevatorApplication.class.getResource("elevator.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Elevator Simulator");
		primaryStage.show();
		animator.start();
		simulator.start();
	}

	private GridPane createElevatorFloors() {

		GridPane floorPane = new GridPane();

		labelTF.setText("  Target Floor");
		labelCF.setText("  Current Floor");
		labelPU.setText("  Power Used");

		for (int i = floors.length - 1; i > 0; i--) {
			floorPane.add(floors[i], 0, (floors.length - 1) - i);
		}

		floorPane.add(labelTF, 2, 2);
		floorPane.add(textfieldTF, 3, 2);
		floorPane.add(labelCF, 2, 3);
		floorPane.add(textfieldCF, 3, 3);
		floorPane.add(labelPU, 2, 4);
		floorPane.add(textfieldPU, 3, 4);

		return floorPane;

	}

	@Override
	public void stop() throws Exception {
		// TODO Auto-generated method stub
		super.stop();
		animator.stop();

	}

	private class ElevatorAnimator extends AnimationTimer {

		private static final long SECOND = 1000000000;
		private static final long NORMAL = SECOND/10;
		private static final long SLOW = SECOND/3;

		private int currentFloor;
		private int targetFloor;
		private double powerUsed;
		private long lastUpdate = 0;
		private long update = NORMAL;

		private Queue<List<Number>> queue = new LinkedList<>();

		@Override
		public void handle(long now) {
			// TODO Auto-generated method stub

			if (queue.isEmpty() || now - lastUpdate < update) {
				return;
			}

			lastUpdate = now;

			floors[currentFloor].setId("empty");
			
			List<Number> data = queue.poll();
			currentFloor = data.get(0).intValue();
			targetFloor = data.get(1).intValue();
			powerUsed =  data.get(2).doubleValue();
			floors[targetFloor].setId("target");
			floors[currentFloor].setId("elevator");
			textfieldCF.setText(String.valueOf(currentFloor));
			textfieldTF.setText(String.valueOf(targetFloor));
			textfieldPU.setText(String.valueOf(powerUsed));
			
			update=(Math.abs(currentFloor-targetFloor)<=1)?SLOW:NORMAL;	
		}

		public void addData(List<Number> data) {
			queue.add(data);
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof ElevatorImp) {
			List<Number> arr = (List<Number>) arg;
			animator.addData(arr);
		}
	}

}
