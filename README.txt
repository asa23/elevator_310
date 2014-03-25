/**********************************************
 * Please DO NOT MODIFY the format of this file
 **********************************************/

/*************************
 * Team Info & Time spent
 *************************/

	Name1: Rachel Harris
	NetId1: rah35
	Time spent: 15 hours

	Name2: Brian Bullins
	NetId2: bab41
	Time spent: 15 hours  

	Name3: Ariba Aboobakar 
	NetId3: asa23	
	Time spent: 15 hours

/******************
 * Files to submit
 ******************/

	lab3.jar // An executable jar including all the source files and test cases.
	README	// This file filled with the lab implementation details
	Elevator.input // You can submit a sample input and log file
        Elevator.log   // corresponding to the input but Elevator.log should be 
		       // auto-generated on execution of jar file

/************************
 * Implementation details
 *************************/

/* 
 * This section should contain the implementation details and a overview of the
 * results. 

 * You are required to provide a good README document including the
 * implementation details. In particular, you can use pseudocode to describe
 * your implementation details where necessary. However that does not mean to
 * copy/paste your C code. Specifically, explain the synchronization primities
 * used in implmenting the elevator, scheduling choices used, how the capacity
 * constraint is handled, and how the mulitple elevators are supported. Also,
 * explain how to run the test cases for your EventBarrier and Elevator and how
 * to interpret the results. Check the README.OUTPUTFORMAT for the acceptable
 * input/output format for the elevator. Expect the design and implementation
 * details to be at most 2-3 pages.  A plain textfile is encouraged. However, a
 * pdf is acceptable.  No other forms are permitted.

 * In case of lab is limited in some functionality, you should provide the
 * details to maximize your partial credit.  
 * */
 
 Multiple elevators are handled in the Hotel class, where the hotel assigns an 
 elevator to a rider based on a distance function.  The function calculates the 
 distance of all the elevators in the building from the floor of the rider requesting 
 an elevator, taking into account the current direction (up or down) of the 
 elevator's path and the direction that the rider wants to go.  We experimented with 
 having a cache of previous elevator-to-floor assignments, but the threads didn't 
 necessarily release the assignments in a predictable or timely fashion, and there 
 should not be cases where multiple riders are assigned to different elevators without 
 this functionality, anyway.  (The closest elevator to a rider should also be closest 
 to the riders near that first rider, so it should receive assignments for the nearby
 floors as well.)  This functionality is centralized in the pickAnElevator method in 
 the Hotel class.

/************************
 * Feedback on the lab
 ************************/

Lab was challenging but a good demonstration of threads, scheduling and locks.

/************************
 * References
 ************************/

Java concurrency tutorials:
http://docs.oracle.com/javase/tutorial/essential/concurrency/guardmeth.html
http://www.journaldev.com/1037/java-thread-wait-notify-and-notifyall-example

We also recieved help from Nisarg Raval
