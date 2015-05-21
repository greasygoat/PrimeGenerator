package edu.ecu.cs.sle.primes;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The Primes class contains the main method and handles thread creation
 * and thread monitoring. 
 * 
 * @author Patrick Rider
 * @version 1.0
 */

public class Primes {

	/** 
	 * The queue holding candidates we want to check for primality.
	 */
	private BlockingQueue<Integer> candidateQueue;
	
	/** 
	 * The queue holding primes we want to print before inserting into the result set.
	 */
	private BlockingQueue<Integer> primesQueue;

	/**
	 * The set holding the numbers that we have determined are prime.
	 */
	private Set<Integer> primeNumbers;
	
	/**
	 * Create a new instance of the Primes checker program.
	 */
	public Primes(int size) {
		
		candidateQueue = new ArrayBlockingQueue<Integer>(size/2);
		
		primesQueue = new ArrayBlockingQueue<Integer>(size/2);

		primeNumbers = new HashSet<Integer>();
	}

	/**
	 * Actually run the primes finder, looking for primes between smallest and biggest (inclusive).
	 * 
	 * @param smallest the smallest number to check
	 * @param biggest the biggest number to check
	 * 
	 * @return a {@link Set} containing the prime numbers we have found
	 */
	public Set<Integer> findPrimesInRange(int smallest, int biggest) {
		
		ExecutorService executor = Executors.newFixedThreadPool(4);
	
		Thread generator = new Thread(new NumberGenerator(smallest, biggest, candidateQueue));
		
		Thread primeChecker = new Thread(new PrimalityChecker(candidateQueue, primesQueue));
		
		Thread printer = new Thread(new PrimesPrinter(primesQueue, primeNumbers));
		
		executor.execute(generator);
		executor.execute(primeChecker);
		executor.execute(primeChecker);
		executor.execute(printer);
		executor.shutdown();
		
		return primeNumbers;
	}
	
	public static void main(String[] args) {
		
		int upperLimit = 100;
		
		
		Primes p = new Primes(upperLimit);
		
		p.findPrimesInRange(2, upperLimit);
		
	}

}
