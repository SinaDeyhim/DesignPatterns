package test.elevator;

import static org.junit.Assert.*;

import org.junit.Test;

import elevator.ElevatorImp;
import elevator.MovingState;
import elevatorsystem.ElevatorPanel;
import elevatorsystem.ElevatorSystemImp;

public class TestElevatorSystemImp {

	

	@Test
	public void testCallUp() {
		ElevatorSystemImp elSys = new ElevatorSystemImp( 0, 20);
		elSys.addElevator( new ElevatorImp( 1, (ElevatorPanel)elSys));
		elSys.callUp(10);
		assertTrue(elSys.getCurrentFloor()==10);
		
	}

	@Test
	public void testCallDown() {
		ElevatorSystemImp elSys = new ElevatorSystemImp( 0, 20);
		elSys.addElevator( new ElevatorImp( 1, (ElevatorPanel)elSys));
		elSys.callDown(10);
		assertTrue(elSys.getCurrentFloor()==10);
	}

}
