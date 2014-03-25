import java.util.*;

public class Elevator extends AbstractElevator implements Runnable{

	private EventBarrier curOnElevatorEventBarrier;
	private EventBarrier curOffElevatorEventBarrier;
	private Hotel myHotel;
	private HashMap<Integer, Integer> curOccupants;
	private int numRequests;
	private int occupantsCount;
	private boolean goingUp;
	private int curFloor;

	public Elevator(int numFloors, int elevatorId, int maxOccupancyThreshold, Hotel hotel) {
		super(numFloors, elevatorId, maxOccupancyThreshold);
		this.myHotel = hotel;
		this.curOccupants = new HashMap<Integer, Integer>();
		numRequests = 0;
		occupantsCount = 0;
		this.goingUp = true;
	}

	@Override
	public void OpenDoors(){
		
	}
	
	public void OpenOnElevatorDoors() {
		// TODO Auto-generated method stub
		this.curOnElevatorEventBarrier.raise();
	}
	
	public void OpenOffElevatorDoors() {
		this.curOffElevatorEventBarrier.raise();
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
		this.curOnElevatorEventBarrier = this.myHotel.getOnElevatorFloorGuard(floor);
		this.curOffElevatorEventBarrier = this.myHotel.getOffElevatorFloorGuard(floor);

		if (this.curOnElevatorEventBarrier.waiters() > 0 || this.curOffElevatorEventBarrier.waiters() > 0){
			System.out.printf("E%d on F%d opens\n", this.elevatorId, floor);
			OpenOnElevatorDoors();
			OpenOffElevatorDoors();

			System.out.printf("E%d on F%d closes\n", this.elevatorId, floor);
			ClosedDoors();
		}

	}

	@Override
	public synchronized boolean Enter() {
		// TODO Auto-generated method stub
		this.curOffElevatorEventBarrier.complete();
		occupantsCount+=1;
		return true;
	}

	@Override
	public void Exit() {
		// TODO Auto-generated method stub
		this.curOnElevatorEventBarrier.complete();
		occupantsCount-=1;
		decrementRequests();
	}

	@Override
	public void RequestFloor(int floor) {
		// TODO Auto-generated method stub
		EventBarrier destFloorGuard = myHotel.getOnElevatorFloorGuard(floor);
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
	
	public synchronized void setFloor(int floor) {
		this.curFloor = floor;
	}
	
	public synchronized int getFloor() {
		return this.curFloor;
	}

	public void run(){
		//TODO: add in thread logic here

		while(true){
			if (getNumRequests() > 0){
				for (int i = 1; i < numFloors; i++) {
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					setGoingUp();
					VisitFloor(i);
					setFloor(i);
					//System.out.println(i);
				}
				for (int i = numFloors; i > 1; i--) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					setGoingDown();
					VisitFloor(i);
					setFloor(i);
					//System.out.println(i);
				}
			}
		}
	}


}
