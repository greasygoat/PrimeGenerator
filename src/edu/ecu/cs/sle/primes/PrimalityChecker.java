package edu.ecu.cs.sle.primes;

import java.util.concurrent.BlockingQueue;


/**
 * The PrimalityChecker class is used to check individual numbers to see if
 * they are prime.
 * 
 * @author Mark Hills
 * @version 1.0
 */
public class PrimalityChecker implements Runnable{
	/** A queue that holds generated numbers that we have not yet checked */
	private final BlockingQueue<Integer> candidateQueue;

	/** A queue where we place numbers when we determine they are prime */
	private final BlockingQueue<Integer> primesQueue;
	
	
	/**
	 * Create an instance of the PrimalityChecker class, which will check numbers to see if
	 * they are prime and then insert them into a result queue.
	 * 
	 * @param candidates queue that holds the candidates to check
	 * @param primes queue that holds prime numbers we have found
	 */
	public PrimalityChecker(BlockingQueue<Integer> candidates, BlockingQueue<Integer> primes) {
		candidateQueue = candidates;
		primesQueue = primes;
	}

	boolean isPrime(int number){
		if(number == 1){
			return false;
		}
		int sqrt = (int) Math.sqrt(number);
		for (int i = 3; i <= sqrt; i +=2 ){
			if(number % i == 0){
				return false;
			}
		}
		return true;
	}
	@Override
	public void run() {
		boolean run = true;
		
		while(run){
			
			Integer test;
			try {
				test = candidateQueue.take();
				
			
				if(isPrime(test)){
					primesQueue.add(test);
				}
				run = (candidateQueue.peek()!=1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (NullPointerException e) {
					
				}
		}	
	}
	
}
