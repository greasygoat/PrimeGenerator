package edu.ecu.cs.sle.primes;

import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * The PrimesPrinter class is used to print prime numbers and add them into a result set.
 * 
 * @author Mark Hills
 * @version 1.0
 */
public class PrimesPrinter implements Runnable{
	/** A queue that holds numbers we have determined to be prime but have not yet processed */
	private final BlockingQueue<Integer> primesQueue;
	
	/** A set that holds all the prime numbers we have found */
	private final Set<Integer> primesSet;
	
	/**
	 * Create an instance of the PrimesPrinter class, which will print any numbers that
	 * come in on the primes queue and then place them in the primes set.
	 * 
	 * @param primes queue that holds prime numbers we have found but not yet processed
	 * @param primesSet set that holds all the prime numbers we have found
	 */
	public PrimesPrinter(BlockingQueue<Integer> primes, Set<Integer> primesSet) {
		primesQueue = primes;
		this.primesSet = primesSet;
	}


	@Override
	public void run() {
		boolean run = true;
		while(run){
		
			try {
				int prime = primesQueue.poll(100, TimeUnit.MILLISECONDS);
				
				System.out.println(prime);
				primesSet.add(prime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				System.out.println("There are " + primesSet.size() + " primes in the range.");
				run=false;
			}
			
		}

	}
	
}
