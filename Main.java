import java.util.*;
import java.io.*;

public class Main{
	
	private int FloorCount;
	private int ElevatorCount;
	private int RiderCount;
	private int MaxOccupancy;
	private HashMap<Integer, Rider> RiderSet;

	public static void main(String[] args) {
		try {
			new Main().go();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void go() throws FileNotFoundException {
		Scanner in = new Scanner(new BufferedReader(new FileReader("test.txt")));
		FloorCount = in.nextInt();
		ElevatorCount = in.nextInt();
		RiderCount = in.nextInt();
		MaxOccupancy = in.nextInt();

		Hotel testHotel = new Hotel(FloorCount, ElevatorCount);
		testHotel.setElevators(MaxOccupancy, testHotel);

		// initialize RiderSet
		RiderSet = new HashMap<Integer, Rider>();
		for (int i = 0; i < RiderCount; i++){
			Rider newRider = new Rider(testHotel, i+1);

			RiderSet.put(i+1, newRider);
		}
		
		

		
		Thread elevatorThread = new Thread(testHotel.getElevator(1));
		elevatorThread.start();
		
		
		

		while (in.hasNext()) {
			
			int curRiderIndex = in.nextInt();
			int startFloor = in.nextInt();
			int destFloor = in.nextInt();

			Rider curRider = RiderSet.get(curRiderIndex);
			curRider.setFromFloor(startFloor);
			curRider.setDestFloor(destFloor);

			Thread t1 = new Thread(curRider); 
			t1.start();
			
			
		}
		
	}
}