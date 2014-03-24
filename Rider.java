public class Rider implements Runnable {
    private Hotel myHotel;
    private Elevator myElevator;
    private int fromFloor;
    private int destFloor;
    private int riderId;
    private boolean onElevator;

    public Rider(Hotel hotel, int id) {
        this.myHotel = hotel;
        this.riderId = id;
        this.onElevator = false;
    }

    public void setFromFloor(int myFromFloor){
        fromFloor = myFromFloor;
    }

    public void setDestFloor(int myDestFloor){
        destFloor = myDestFloor;
    }
    
    public synchronized boolean isOnElevator(){
        return this.onElevator;
    }
    
    public synchronized void setOnElevator(boolean riderOnElevator){
        this.onElevator = riderOnElevator;
    }

    public void run() {
        // TODO: add in thread logic here       
        while (!this.onElevator){
            if (destFloor > fromFloor){
                System.out.printf("R%d pushes U%d\n", riderId, fromFloor);
                myElevator = myHotel.CallUp(fromFloor);
                if (myElevator.elevatorDirectionIsUp() == true){
                    setOnElevator(true);
                    System.out.printf("R%d enters E%d on F%d\n", riderId, myElevator.elevatorId, fromFloor);
                    myElevator.Enter();
                    myElevator.SetOccupancy(destFloor, riderId);
                    myElevator.RequestFloor(destFloor);
                }
                else {
                    myElevator.decrementRequests();
                    EventBarrier fromFloorGuard = myHotel.getFloor(fromFloor);
                    fromFloorGuard.complete();
                }
            }
            else {
                System.out.printf("R%d pushes D%d\n", riderId, fromFloor);
                myElevator = myHotel.CallDown(fromFloor);
                if (myElevator.elevatorDirectionIsUp() == false){
                    setOnElevator(true);
                    System.out.printf("R%d enters E%d on F%d\n", riderId, myElevator.elevatorId, fromFloor);
                    myElevator.Enter();
                    myElevator.SetOccupancy(destFloor, riderId);
                    myElevator.RequestFloor(destFloor);
                }
                else {
                    myElevator.decrementRequests();
                    EventBarrier fromFloorGuard = myHotel.getFloor(fromFloor);
                    fromFloorGuard.complete();
                }
            }       
        }

        System.out.printf("R%d exits E%d on F%d\n", riderId, myElevator.elevatorId, destFloor);
        myElevator.Exit();
    }
}