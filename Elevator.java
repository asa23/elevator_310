
public class Elevator extends AbstractElevator implements Runnable{

	private EventBarrier curEventBarrier;
	private Hotel myHotel;

	public Elevator(int numFloors, int elevatorId, int maxOccupancyThreshold, Hotel hotel) {
		super(numFloors, elevatorId, maxOccupancyThreshold);
		this.myHotel = hotel;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void OpenDoors() {
		// TODO Auto-generated method stub
		this.curEventBarrier.raise();
	}

	@Override
	public void ClosedDoors() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void VisitFloor(int floor) {
		this.curEventBarrier = this.myHotel.getFloor(floor);
		if (this.curEventBarrier.waiters() > 0){
			OpenDoors();
			Exit();
			Enter();
			ClosedDoors();
		}
		
	}

	@Override
	public boolean Enter() {
		// TODO Auto-generated method stub
		
		return true;
	}

	@Override
	public void Exit() {
		// TODO Auto-generated method stub
		this.curEventBarrier.complete();
	}

	@Override
	public void RequestFloor(int floor) {
		// TODO Auto-generated method stub
		
	}

	public void run(){
		// TODO: add in thread logic here
	}

}
