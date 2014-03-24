public class Rider implements Runnable {
    private Hotel myHotel;
    private Elevator myElevator;
    private int fromFloor;
    private int destFloor;
    private int riderId;

    public Rider(Hotel hotel, int id) {
        this.myHotel = hotel;
        this.riderId = id;
    }

    public void setFromFloor(int myFromFloor){
        fromFloor = myFromFloor;
    }

    public void setDestFloor(int myDestFloor){
        destFloor = myDestFloor;
    }

    public void run() {
        // TODO: add in thread logic here
        System.out.printf("R%d pushes U%d\n", riderId, fromFloor);
        myElevator = myHotel.CallUp(fromFloor);

        System.out.printf("R%d enters E%d on F%d\n", riderId, myElevator.elevatorId, fromFloor);
        myElevator.Enter();
        myElevator.SetOccupancy(destFloor, riderId);
        myElevator.RequestFloor(destFloor);

        System.out.printf("R%d exits E%d on F%d\n", riderId, myElevator.elevatorId, destFloor);
        myElevator.Exit();
    }
}