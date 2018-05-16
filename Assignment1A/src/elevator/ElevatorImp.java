package elevator;

import java.util.Arrays;
import java.util.Observable;

import elevatorsystem.ElevatorPanel;

public class ElevatorImp extends Observable implements Elevator{
	private int POWER_START_STOP=2;
	private int POWER_CONTINUOUS=1;
	private long SLEEP_START_STOP=25;
	private long SLEEP_CONTINUOUS=25;
	private int MAX_CAPACITY_PERSONS;
	private double powerUsed;
	private int currentFloor=0;
	private int capacity;
	private ElevatorPanel panel;
	private MovingState state=MovingState.Idle;
	private static int counter=0;


	
	public ElevatorImp(int MAX_CAPACITY_PERSONS, ElevatorPanel panel) {
		this.MAX_CAPACITY_PERSONS=MAX_CAPACITY_PERSONS;
		this.panel=panel;	
	}

	/**
	 * This method moves the elevator to a new floor. 
	 * @param floor is the target floor.
	 */
	@Override
	public void moveTo(int floor) {
		
		while(getFloor()!=floor) {
			processMovingState(floor);
			counter++; //  helper variable  used by the processMovingState to set the stage to slow at the start
			setChanged(); 
			notifyObservers(Arrays.asList(getFloor(), floor ,getPowerConsumed()));
		}
		setCurrentFloor(floor);
		counter=0;
	}


	private void processMovingState(int floor) {
		int step = (floor>getFloor())? 1:-1;
		state =(floor>getFloor())? MovingState.SlowUp:MovingState.SlowDown;
		switch(Math.abs(floor - getFloor())){
		case 0:
			state=MovingState.Idle;
			break;
		case 1:
			state=state.slow();
			powerUsed+=2;
			setCurrentFloor(getFloor()+step);
			break;
		default:
			if (counter!=0) {
				state=state.normal();
				powerUsed+=1;
			} else {//this is here to cover the elevator start, and avoid designating a normal state for it.
				state=state.slow();
				powerUsed+=2;
			}
			setCurrentFloor(getFloor()+step);
		}
	}

	@Override
	public int getCapacity() {
		return capacity;
	}

	@Override
	public boolean isFull() {
		return capacity==0;
	}

	@Override
	public boolean isEmpty() {
		return capacity==MAX_CAPACITY_PERSONS;
	}

	@Override
	public MovingState getState() {
		return state;
	}

	@Override
	public double getPowerConsumed() {
		return powerUsed;
	}

	@Override
	public int getFloor() {
		return currentFloor;
	}

	@Override
	public void addPersons(int persons) {
		if (persons<0) {
			throw new IllegalArgumentException("Invalid input; number of persons cannot be negative");	
		}else if(MAX_CAPACITY_PERSONS<persons+capacity) {
			throw new IllegalArgumentException("There is not enough room for all you guys!");
		}
		capacity-=persons;
	}

	@Override
	public void requestStop(int floor) {
		panel.requestStop(floor, this);
	}
	
	private void setCurrentFloor(int floor) {
		this.currentFloor=floor;
	}
	
}
