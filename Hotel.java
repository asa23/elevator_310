import java.util.*;

public class Hotel extends AbstractBuilding{

	private HashMap<Integer, EventBarrier> FloorGuards;

	public Hotel(int numFloors, int numElevators){
		super(numFloors, numElevators);
	}

	@Override
	public AbstractElevator CallUp(int fromFloor){

	}

	@Override
	public AbstractElevator CallDown(int fromFloor){

	}

	public EventBarrier getFloor(int floor){
		return FloorGuards.get(floor);
	}


}