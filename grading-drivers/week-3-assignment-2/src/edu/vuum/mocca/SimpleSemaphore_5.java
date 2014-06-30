package edu.vuum.mocca;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @class SimpleSemaphore
 * 
 * @brief This class provides a simple counting semaphore implementation using
 *        Java a ReentrantLock and a ConditionObject. It must implement both
 *        "Fair" and "NonFair" semaphore semantics, just liked Java Semaphores.
 */
public class SimpleSemaphore_5 {
	/**
	 * Constructor initialize the data members.
	 */
	public SimpleSemaphore_5(int permits, boolean fair) {
		// TODO - you fill in here
		mReentrantLock = new ReentrantLock(fair);
		mCondition = mReentrantLock.newCondition();
		mPermits = permits;
	}

	/**
	 * Acquire one permit from the semaphore in a manner that can be
	 * interrupted.
	 */
	public void acquire() throws InterruptedException {
		// TODO - you fill in here

		mReentrantLock.lock();
		try {
			while (mPermits == 0) {
				mCondition.await();
			}

			mPermits--;

			mCondition.signal();
		} finally {
			mReentrantLock.unlock();
		}
	}

	/**
	 * Acquire one permit from the semaphore in a manner that cannot be
	 * interrupted.
	 */
	public void acquireUninterruptibly() {
		// TODO - you fill in here

		mReentrantLock.lock();
		try {

			while (mPermits == 0) {
				mCondition.awaitUninterruptibly();
			}
			mPermits--;
			mCondition.signal();
		} finally {
			mReentrantLock.unlock();
		}
	}

	/**
	 * Return one permit to the semaphore.
	 */
	void release() {
		// TODO - you fill in here

		try {
			mReentrantLock.lock();
			mPermits++;
			if (mPermits != 0)
				mCondition.signal();
		} finally {
			mReentrantLock.unlock();
		}

	}

	/**
	 * Return the number of permits available.
	 */
	public int availablePermits() {
		// TODO - you fill in here
		return mPermits; // You will change this value.
	}

	/**
	 * Define a ReentrantLock to protect the critical section.
	 */
	// TODO - you fill in here
	final private Lock mReentrantLock;

	/**
	 * Define a ConditionObject to wait while the number of permits is 0.
	 */
	// TODO - you fill in here
	final private Condition mCondition;

	/**
	 * Define a count of the number of available permits.
	 */
	// TODO - you fill in here
	private volatile int mPermits;
}
