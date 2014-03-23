import java.util.*;

public class Hotel extends AbstractBuilding{

	private HashMap<Integer, EventBarrier> FloorGuards;
	private HashMap<Integer, Elevator> ElevatorSet;
	
	public Hotel(int numFloors, int numElevators){
		super(numFloors, numElevators);
	}

	@Override
	public AbstractElevator CallUp(int fromFloor){
		Elevator curElevator = getElevator(1); // more logic here later, for multiple elevators
		EventBarrier fromFloorGuard = getFloor(fromFloor);
		fromFloorGuard.arrive();
	}

	@Override
	public AbstractElevator CallDown(int fromFloor){
		Elevator curElevator = getElevator(1); // more logic here later, for multiple elevators
		EventBarrier fromFloorGuard = getFloor(fromFloor);
		fromFloorGuard.arrive();
	}

	public AbstractElevator getElevator(int elevatorID){
		return ElevatorSet.get(elevatorID);
	}
	
	public EventBarrier getFloor(int floor){
		return FloorGuards.get(floor);
	}


}