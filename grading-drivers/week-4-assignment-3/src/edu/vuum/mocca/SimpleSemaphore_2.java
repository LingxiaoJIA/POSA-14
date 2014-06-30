package edu.vuum.mocca;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @class SimpleSemaphore
 *
 * @brief This class provides a simple counting semaphore
 *        implementation using Java a ReentrantLock and a
 *        ConditionObject.  It must implement both "Fair" and
 *        "NonFair" semaphore semantics, just liked Java Semaphores. 
 */
public class SimpleSemaphore {
    /**
     * Define a ReentrantLock to protect the critical section.
     */
    // TODO - you fill in here

    private ReentrantLock mRLock;

    /**
     * Define a ConditionObject to wait while the number of
     * permits is 0.
     */
    // TODO - you fill in here
    private final Condition zeroCondition;

    /**
     * Define a count of the number of available permits.
     */
    // TODO - you fill in here.  Make sure that this data member will
    // ensure its values aren't cached by multiple Threads..
    private volatile int permitCount;

    /**
     * Constructor initialize the data members.  
     */
    public SimpleSemaphore (int permits,
                            boolean fair)
    { 
        // TODO - you fill in here
    	this.permitCount = permits;
    	this.mRLock = new ReentrantLock(fair);
    	this.zeroCondition = mRLock.newCondition();
    }

    /**
     * Acquire one permit from the semaphore in a manner that can
     * be interrupted.
     */
    public void acquire() throws InterruptedException {
        // TODO - you fill in here
	    try
	    {
	    	this.mRLock.lock();
	    	
	    	while (this.permitCount == 0) 
			{
				this.zeroCondition.wait();
			}
	    	
	    	this.permitCount--;
	    	
	    }
	    finally
	    {
	    	this.mRLock.unlock();
	    }
		
    }

    /**
     * Acquire one permit from the semaphore in a manner that
     * cannot be interrupted.
     */
    public void acquireUninterruptibly() {
        // TODO - you fill in here
	    try
	    {
	    	this.mRLock.lock();
	    	
	    	while (this.permitCount == 0) 
			{
				this.zeroCondition.awaitUninterruptibly();
			}
	    	
	    	this.permitCount--;
	    	
	    }
	    finally
	    {
	    	this.mRLock.unlock();
	    }
    }

    /**
     * Return one permit to the semaphore.
     */
    void release() {
        // TODO - you fill in here
    	 try
 	    {
 	    	this.mRLock.lock();
 	    	this.permitCount++;
 	    	this.zeroCondition.signal(); 	    	
 	    }
 	    finally
 	    {
 	    	this.mRLock.unlock();
 	    }
    }
    
    /**
     * Return the number of permits available.
     */
    public int availablePermits(){
    	// TODO - you fill in here
    	return this.permitCount; // You will change this value. 
    }
}
