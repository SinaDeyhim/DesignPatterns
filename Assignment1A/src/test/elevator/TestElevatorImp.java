package test.elevator;
import elevator.Elevator;
import elevator.ElevatorImp;
import elevator.MovingState;
import elevatorsystem.ElevatorPanel;
import elevatorsystem.ElevatorSystem;
import elevatorsystem.ElevatorSystemImp;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestElevatorImp {

	@Test
	public void testMoveTo() {
		ElevatorSystem system = new ElevatorSystemImp(0,20); 
		Elevator el = new ElevatorImp(1,(ElevatorPanel)system);
		((ElevatorImp) el).setState(MovingState.SlowUp);
		el.moveTo(8);
		assertTrue(el.getFloor()==8);
	}

	@Test
	public void testGetPowerConsumed() {
		ElevatorSystem system = new ElevatorSystemImp(0,20); 
		Elevator el = new ElevatorImp(1,(ElevatorPanel)system);
		((ElevatorImp) el).setState(MovingState.SlowUp);
		el.moveTo(6);
		assertEquals(8.0, el.getPowerConsumed(), 0.000001);
		
	}

	@Test
	public void testRequestStop() {
		ElevatorSystem system = new ElevatorSystemImp(0,20); 
		Elevator el = new ElevatorImp(1,(ElevatorPanel)system);
		el.requestStop(8);
		assertTrue(el.getFloor()==8);
	}

}
