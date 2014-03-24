import java.util.*;

public class Elevator extends AbstractElevator implements Runnable{

	private EventBarrier curEventBarrier;
	private Hotel myHotel;
	private HashMap<Integer, Integer> curOccupants;
	private int numRequests;
	private int occupantsCount;
	private boolean goingUp;

	public Elevator(int numFloors, int elevatorId, int maxOccupancyThreshold, Hotel hotel) {
		super(numFloors, elevatorId, maxOccupancyThreshold);
		this.myHotel = hotel;
		this.curOccupants = new HashMap<Integer, Integer>();
		numRequests = 0;
		occupantsCount = 0;
		this.goingUp = true;
	}

	@Override
	public void OpenDoors() {
		// TODO Auto-generated method stub
		this.curEventBarrier.raise();
	}
	
	private synchronized int getNumRequests(){
		return numRequests;
	}

	@Override
	public void ClosedDoors() {
		// TODO Auto-generated method stub

	}

	@Override
	public void VisitFloor(int floor) {
		this.curEventBarrier = this.myHotel.getFloor(floor);
		if (curEventBarrier.waiters() > 0)
			//System.out.printf("On floor %d, %d riders waiting\n", floor, curEventBarrier.waiters());
		if (this.curEventBarrier.waiters() > 0){
			System.out.printf("E%d on F%d opens\n", this.elevatorId, floor);
			OpenDoors();

			System.out.printf("E%d on F%d closes\n", this.elevatorId, floor);
			ClosedDoors();
		}

	}

	@Override
	public synchronized boolean Enter() {
		// TODO Auto-generated method stub
		this.curEventBarrier.complete();
		occupantsCount+=1;
		return true;
	}

	@Override
	public void Exit() {
		// TODO Auto-generated method stub
		this.curEventBarrier.complete();
		occupantsCount-=1;
		decrementRequests();
	}

	@Override
	public void RequestFloor(int floor) {
		// TODO Auto-generated method stub
		EventBarrier destFloorGuard = myHotel.getFloor(floor);
		destFloorGuard.arrive();
	}

	public void SetOccupancy(int floor, int riderId){
		this.curOccupants.put(floor, riderId);
	}
	
	public synchronized void incrementRequests(){
		numRequests += 1;
	}
	
	public synchronized void decrementRequests(){
		numRequests -= 1;
	}
	
	public synchronized void setGoingUp(){
		this.goingUp = true;
	}
	
	public synchronized void setGoingDown(){
		this.goingUp = false;
	}
	
	public synchronized boolean elevatorDirectionIsUp(){
		return this.goingUp;
	}

	public void run(){
		//TODO: add in thread logic here

		while(true){
			if (getNumRequests() > 0){
				for (int i = 1; i < numFloors; i++) {
					setGoingUp();
					VisitFloor(i);
					//System.out.println(i);
				}
				for (int i = numFloors; i > 1; i--) {
					setGoingDown();
					VisitFloor(i);
					//System.out.println(i);
				}
			}
		}
	}


}
