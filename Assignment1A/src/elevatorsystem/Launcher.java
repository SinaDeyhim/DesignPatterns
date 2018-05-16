package elevatorsystem;
import elevator.Elevator;
import elevator.ElevatorImp;
import elevatorsystem.ElevatorPanel;
import elevatorsystem.ElevatorSystem;
import elevatorsystem.ElevatorSystemImp;

public class Launcher{

	public static void main( String[] args){
		final int MIN_FLOOR = 0;
		final int MAX_FLOOR = 20;
		ElevatorSystem system = new ElevatorSystemImp( MIN_FLOOR, MAX_FLOOR);
		system.addElevator( new ElevatorImp( 5, (ElevatorPanel)system));
		Elevator elevator = system.callDown(6);//change to 12
		System.out.println("Current Floor: "+elevator.getFloor());
		System.out.println("Energy Consumed: "+elevator.getPowerConsumed());
		
		elevator.addPersons(1);
		elevator.requestStop(0);
		System.out.println("Current Floor: "+elevator.getFloor());
		System.out.println("Energy Consumed: "+elevator.getPowerConsumed());

	}
}