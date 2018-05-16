package elevator;

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


	//elevator constructor. changed the max_capacity to int
	public ElevatorImp(int MAX_CAPACITY_PERSONS, ElevatorPanel panel) {
		this.MAX_CAPACITY_PERSONS=MAX_CAPACITY_PERSONS;
		this.panel=panel;
		setCapacity(MAX_CAPACITY_PERSONS);
	}

	//move elevator do the power here as well
	@Override
	public void moveTo(int floor) {
		
		while(getFloor()!=floor) {
			processMovingState(floor);
			counter++; //  helper variable  used by the processMovingState to set the stage to slow at the start
			//observable
		}

		setCurrentFloor(floor);
		counter=0;
	}


	private void processMovingState(int floor) {
		int step = (floor>getFloor())? 1:-1;
		switch(Math.abs(floor - getFloor())){
		case 0:
			state=MovingState.Idle;
			powerUsed+=0;
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
				setCurrentFloor(getFloor()+step);
			} else {//this is here to cover the elevator start, and avoid designating a normal state for it.
				state=state.slow();
				powerUsed+=2;
				setCurrentFloor(getFloor()+step);
			}
		}
	}

	public void setCapacity(int capacity) {
		this.capacity=capacity;
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
		}else if(getCapacity()<persons) {
			throw new IllegalArgumentException("There is not enough room for all you guys!");
		}else {
			capacity-=persons;
		}
	}

	@Override
	public void requestStop(int floor) {
		powerUsed=0;
		state=floor<getFloor()? MovingState.SlowDown:MovingState.SlowUp;
		moveTo(floor);
	}
	//the comment was to make this public, but I have used it to pass the floor to ElevatorSystemImp
	public void setCurrentFloor(int floor) {
		this.currentFloor=floor;
	}
	

	
	public void setState(MovingState state) {
		 this.state=state;
	}
	
}
