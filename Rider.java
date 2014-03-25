import java.util.*;

public class Rider implements Runnable {
    private Hotel myHotel;
    private Elevator myElevator;
    private int fromFloor;
    private int destFloor;
    private int riderId;
    private boolean onElevator;
    private Queue<ArrayList<Integer>> requestQueue;

    public Rider(Hotel hotel, int id) {
        this.myHotel = hotel;
        this.riderId = id;
        this.onElevator = false;
        requestQueue = new LinkedList<ArrayList<Integer>>();
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
    
    public synchronized void addRequest(ArrayList<Integer> request){
        requestQueue.add(request);
    }

    public synchronized Queue<ArrayList<Integer>> getRequestQueue(){
        return this.requestQueue;
    }
    
    public synchronized ArrayList<Integer> popRequest() {
        return requestQueue.poll();
    }

    public void run() {
        // TODO: add in thread logic here
        while (!getRequestQueue().isEmpty()){
            ArrayList<Integer> curRequest = popRequest();
            fromFloor = curRequest.get(0);
            destFloor = curRequest.get(1);
            while (!this.onElevator){
                if (destFloor > fromFloor){
                    System.out.printf("R%d pushes U%d\n", riderId, fromFloor);
                    myElevator = myHotel.CallUp(fromFloor);
                    if (myElevator.elevatorDirectionIsUp()){
                        System.out.println("elevator is going UP");
                    }
                    else {
                        System.out.println("elevator is going DOWN");
                    }
                    if (myElevator.elevatorDirectionIsUp() == true && myElevator.getOccupantsCount() < myElevator.maxOccupancyThreshold){
                        setOnElevator(true);
                        System.out.printf("R%d enters E%d on F%d\n", riderId, myElevator.elevatorId, fromFloor);
                        myElevator.Enter();
                        myElevator.SetOccupancy(destFloor, riderId);
                        myElevator.RequestFloor(destFloor);
                    }
                    else {
                        myElevator.decrementRequests();
                        EventBarrier fromFloorGuard = myHotel.getOffElevatorFloorGuard(fromFloor);
                        //System.out.printf("waiters (before): %d\n", fromFloorGuard.waiters());
                        fromFloorGuard.complete();
                        //System.out.printf("waiters (after): %d\n", fromFloorGuard.waiters());
                        EventBarrier incompleteRiderGuard = myHotel.getIncompleteRiderFloorGuard(fromFloor);
                        incompleteRiderGuard.arrive();
                        incompleteRiderGuard.complete();
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
                        EventBarrier fromFloorGuard = myHotel.getOffElevatorFloorGuard(fromFloor);
                        //System.out.printf("waiters (before): %d\n", fromFloorGuard.waiters());
                        fromFloorGuard.complete();
                        //System.out.printf("waiters (before): %d\n", fromFloorGuard.waiters());
                        EventBarrier incompleteRiderGuard = myHotel.getIncompleteRiderFloorGuard(fromFloor);
                        incompleteRiderGuard.arrive();
                        incompleteRiderGuard.complete();
                    }
                }       
            }
    
            System.out.printf("R%d exits E%d on F%d\n", riderId, myElevator.elevatorId, destFloor);
            myElevator.Exit();
            setOnElevator(false);
        }
    }
}