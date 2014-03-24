import java.util.*;

public class Hotel extends AbstractBuilding{

	private HashMap<Integer, EventBarrier> FloorGuards;
	private HashMap<Integer, Elevator> ElevatorSet;
	
	public Hotel(int numFloors, int numElevators){
		super(numFloors, numElevators);

		// initialize FloorGuards map
		FloorGuards = new HashMap<Integer, EventBarrier>();
		for (int i = 0; i < numFloors; i++) {
			FloorGuards.put(i+1, new EventBarrier());
		}

	}

	@Override
	public Elevator CallUp(int fromFloor){
		Elevator curElevator = getElevator(1); // more logic here later, for multiple elevators
		EventBarrier fromFloorGuard = getFloor(fromFloor);
		curElevator.incrementRequests();
		fromFloorGuard.arrive();
		return curElevator;
	}

	@Override
	public Elevator CallDown(int fromFloor){
		Elevator curElevator = getElevator(1); // more logic here later, for multiple elevators
		EventBarrier fromFloorGuard = getFloor(fromFloor);
		fromFloorGuard.arrive();
		return curElevator;
	}

	public void setElevators(int OccupancyThreshold, Hotel thisHotel) {
		// initialize ElevatorSet map
		ElevatorSet = new HashMap<Integer, Elevator>();
		for (int i = 0; i < numElevators; i++) {
			ElevatorSet.put(i+1, new Elevator(this.numFloors, i+1, OccupancyThreshold, thisHotel));
		}
	}

	public Elevator getElevator(int elevatorID){
		return ElevatorSet.get(elevatorID);
	}
	
	public EventBarrier getFloor(int floor){
		return FloorGuards.get(floor);
	}


}