import java.util.*;

public class Hotel extends AbstractBuilding{

	private HashMap<Integer, EventBarrier> OnElevatorFloorGuards;
	private HashMap<Integer, EventBarrier> OffElevatorFloorGuards;
	private HashMap<Integer, Elevator> ElevatorSet;
	
	public Hotel(int numFloors, int numElevators){
		super(numFloors, numElevators);

		// initialize FloorGuards map
		OnElevatorFloorGuards = new HashMap<Integer, EventBarrier>();
		for (int i = 0; i < numFloors; i++) {
			OnElevatorFloorGuards.put(i+1, new EventBarrier());
		}
		
		OffElevatorFloorGuards = new HashMap<Integer, EventBarrier>();
		for (int i = 0; i < numFloors; i++) {
			OffElevatorFloorGuards.put(i+1, new EventBarrier());
		}

	}

	@Override
	public Elevator CallUp(int fromFloor){
		Elevator curElevator = pickAnElevator(fromFloor, true); // more logic here later, for multiple elevators
		EventBarrier fromFloorGuard = getOffElevatorFloorGuard(fromFloor);
		curElevator.incrementRequests();
		fromFloorGuard.arrive();
		return curElevator;
	}

	@Override
	public Elevator CallDown(int fromFloor){
		Elevator curElevator = pickAnElevator(fromFloor, false); // more logic here later, for multiple elevators
		EventBarrier fromFloorGuard = getOffElevatorFloorGuard(fromFloor);
		curElevator.incrementRequests();
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
	
	public EventBarrier getOnElevatorFloorGuard(int floor){
		return OnElevatorFloorGuards.get(floor);
	}
	
	public EventBarrier getOffElevatorFloorGuard(int floor){
		return OffElevatorFloorGuards.get(floor);
	}
	
	
	private synchronized Elevator pickAnElevator(int fromFloor, boolean goingUp) {
		Elevator chosen;
		ArrayList<Elevator> chosenElevators = new ArrayList<Elevator>();
		int closest = numFloors * 3;
		for (Elevator curElevator: ElevatorSet.values()){
			int distance = 0;
			if (goingUp == curElevator.elevatorDirectionIsUp()) {						//if the elevator is going in the direction the rider wants to go
				distance = Math.abs(curElevator.getFloor() - fromFloor);
			}
			else {															//if the elevator is going the other direction
				if (curElevator.elevatorDirectionIsUp()) {								//if the elevator is going up
					distance = (numFloors - curElevator.getFloor()) + (numFloors - fromFloor);
				}
				else {														//if the elevator is going down
					distance = curElevator.getFloor() + fromFloor;
				}
			}
			System.out.println("Elevator # is: " + curElevator.elevatorId + "\tElevator distance is: " + distance);
			if (distance <= closest){
				chosenElevators.add(curElevator);
				closest = distance;
			}
		}
		if (chosenElevators.size() == 1) {
			chosen = chosenElevators.get(0);
		}
		else {
			Random generator = new Random();
			int x = generator.nextInt(chosenElevators.size() - 1);
			chosen = chosenElevators.get(x);
		}
		System.out.println("ChosenElevator number: " + chosen.elevatorId);
		return chosen;
	}

}