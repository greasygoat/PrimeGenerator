package edu.ecu.cs.sle.primes;

import java.util.concurrent.BlockingQueue;

/**
 * The NumberGenerator class is used to hand out numbers that will be checked
 * by other threads.
 * 
 * @author Mark Hills
 * @version 1.0
 */
public class NumberGenerator implements Runnable{
	/** The biggest number we plan to check */
	private final int biggestNumberToCheck;
	
	/** A queue where we will place the numbers that we generate, which need to be checked */
	private final BlockingQueue<Integer> candidateQueue;
	
	private final int smallestNumberToCheck;
	
	/**
	 * Create a new instance of the NumberGenerator class, which will hand out numbers
	 * that need to be checked.
	 * 
	 * @param smallest the smallest number to check
	 * @param biggest the biggest number to check
	 * @param queue the queue that we will put numbers to check into
	 */
	public NumberGenerator(int smallest, int biggest, BlockingQueue<Integer> queue) {
		biggestNumberToCheck = biggest;
		candidateQueue = queue;
		smallestNumberToCheck = smallest;
	}

	@Override
	public void run() {
	
		if(smallestNumberToCheck <= 2 && biggestNumberToCheck > 1){
			candidateQueue.add(2);
			for(int i = 3; i <= biggestNumberToCheck; i=i+2){
				candidateQueue.add(i);
			}
			candidateQueue.add(1);
		}
		else if(smallestNumberToCheck % 2 == 0){
			for(int i = smallestNumberToCheck+1; i <= biggestNumberToCheck; i=i+2){
				candidateQueue.add(i);
			}
			candidateQueue.add(1);
		}
		else{
			for(int i = smallestNumberToCheck; i <= biggestNumberToCheck; i=i+2){
				candidateQueue.add(i);
			}
			candidateQueue.add(1);
		}	
	}
	
}
