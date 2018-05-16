package elevatorsystem;

import elevator.Elevator;
import elevator.MovingState;

public class ElevatorSystemImp implements ElevatorPanel, ElevatorSystem {
	
	private int MIN_FLOOR;
	private int MAX_FLOOR;
	private Elevator elevator;
	
	public ElevatorSystemImp(int MIN_FLOOR, int MAX_FLOOR) {
		this.MIN_FLOOR=MIN_FLOOR;
		this.MAX_FLOOR=MAX_FLOOR;
	}

	@Override
	public int getFloorCount() {
		return MAX_FLOOR - MIN_FLOOR;
	}

	@Override
	public int getMaxFloor() {
		return MAX_FLOOR;
	}

	@Override
	public int getMinFloor() {
		return MIN_FLOOR;
	}
	
	
	/**
	 * it is not clear what to check  for
	 */
	private boolean checkForElevator() {
		return elevator.getState()!=MovingState.Off;
	}
	
	//check to see if floor is valid
	private void floorCheck(int floor) {
		if (floor>MAX_FLOOR || floor<MIN_FLOOR) {
			throw new IllegalArgumentException("Invalid input; floor number cannot be between min and max floor numbers");	
		}
	}

	//not right
	private Elevator call(int floor , MovingState direction) {
		if(checkForElevator()) {
			elevator.moveTo(floor);
			return elevator;
		}
		throw new IllegalStateException("No elevator available");
	}
	
	@Override
	public Elevator callUp(int floor) {
		floorCheck(floor);
		return call(floor, MovingState.Up);
		
	}

	@Override
	public Elevator callDown(int floor) {
		floorCheck(floor);
		return call(floor,MovingState.Down);
	}

	@Override
	public int getCurrentFloor() {
		return this.elevator.getFloor();
	}

	@Override
	public double getPowerConsumed() {
		return this.elevator.getPowerConsumed();
	}

	@Override
	public void addElevator(Elevator elevator) {
		this.elevator=elevator;
		//error check
		
	}

	@Override
	public void requestStop(int floor, Elevator elevator) {
		elevator.moveTo(floor);
	}
	

}
