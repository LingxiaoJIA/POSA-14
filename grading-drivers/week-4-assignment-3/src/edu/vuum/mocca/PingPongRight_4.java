package edu.vuum.mocca;

// Import the necessary Java synchronization and scheduling classes.
import java.util.concurrent.CountDownLatch;

/**
 * @class PingPongRight
 * 
 * @brief This class implements a Java program that creates two
 *        instances of the PlayPingPongThread and start these thread
 *        instances to correctly alternate printing "Ping" and "Pong",
 *        respectively, on the console display.
 */
public class PingPongRight {
    /**
     * Number of iterations to run the test program.
     */
    public final static int mMaxIterations = 10;

    /**
     * Latch that will be decremented each time a thread exits.
     */
    public static CountDownLatch mLatch = null;

    /**
     * @class PlayPingPongThread
     * 
     * @brief This class implements the ping/pong processing algorithm
     *        using the SimpleSemaphore to alternate printing "ping"
     *        and "pong" to the console display.
     */
    public static class PlayPingPongThread extends Thread {

        /**
         * Constants to distinguish between ping and pong Semaphores.
         */
        private final static int FIRST_SEMA = 0;
        private final static int SECOND_SEMA = 1;

        private int mMaxLoopIterations = 0;

        /**
         * Constructor initializes the data member(s).
         */
        public PlayPingPongThread(String stringToPrint,
                                  SimpleSemaphore semaphoreOne,
                                  SimpleSemaphore semaphoreTwo,
                                  int maxIterations) {
            // TODO - You fill in here.
        	mMaxLoopIterations = maxIterations;
        	this.stringToPrint = stringToPrint;
        	simpleSemaphores[FIRST_SEMA] = semaphoreOne;
        	simpleSemaphores[SECOND_SEMA] = semaphoreTwo;
        	
        }

        /**
         * Main event loop that runs in a separate thread of control
         * and performs the ping/pong algorithm using the
         * SimpleSemaphores.
         */
        public void run() {
            /**
             * This method runs in a separate thread of control and
             * implements the core ping/pong algorithm.
             */

            // TODO - You fill in here.
        	
            for (int i = 1 ; i <= mMaxLoopIterations ; i++) {
                	acquire();
                    System.out.println(stringToPrint + "(" + i + ")");
                    release();
            }
            mLatch.countDown();        	
        }

        /**
         * Hook method for ping/pong acquire.
         */
        void acquire() {
            // TODO fill in here
        	
        	if (this.stringToPrint.equalsIgnoreCase(mPingString)) {
        		try {
					simpleSemaphores[FIRST_SEMA].acquire();
					//simpleSemaphores[SECOND_SEMA].release();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        	if (this.stringToPrint.equalsIgnoreCase(mPongString)) {
        		try {
					//simpleSemaphores[FIRST_SEMA].release();
					simpleSemaphores[SECOND_SEMA].acquire();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}        	
        }

        /**
         * Hook method for ping/pong release.
         */
        void release() {
            // TODO fill in here
        	if (this.stringToPrint.equalsIgnoreCase(mPingString)) {
        		//simpleSemaphores[FIRST_SEMA].release();
				simpleSemaphores[SECOND_SEMA].release();
        	}
        	if (this.stringToPrint.equalsIgnoreCase(mPongString)) {
        		simpleSemaphores[FIRST_SEMA].release();
				//simpleSemaphores[SECOND_SEMA].acquire();
        	}         	
        }

        /**
         * String to print (either "ping!" or "pong"!) for each
         * iteration.
         */
        // TODO - You fill in here.
        private String stringToPrint;

        /**
         * An array of two SimpleSemaphores use to alternate pings and
         * pongs.
         */
        // TODO - You fill in here.
        private SimpleSemaphore[] simpleSemaphores = new SimpleSemaphore[2];
    }

    /**
     * The method that actually runs the ping/pong program.
     */
    public static void process(String startString, 
                               String pingString,
                               String pongString, 
                               String finishString, 
                               int maxIterations) throws InterruptedException {
    	mPingString = pingString;
    	mPongString = pongString;
    	
        // TODO initialize this by replacing null with the appropriate
        // constructor call.
        mLatch = new CountDownLatch(2);

        // Create the ping and pong SimpleSemaphores that control
        // alternation between threads.

        // TODO - You fill in here, make pingSema start out unlocked.
        SimpleSemaphore pingSema = new SimpleSemaphore(1, true);
        
        // TODO - You fill in here, make pongSema start out locked.
        SimpleSemaphore pongSema = new SimpleSemaphore(0, true);

        System.out.println(startString);

        // Create the ping and pong threads, passing in the string to
        // print and the appropriate SimpleSemaphores.
        PlayPingPongThread ping = new PlayPingPongThread(pingString, pingSema, pongSema, maxIterations);
        PlayPingPongThread pong = new PlayPingPongThread(pongString, pingSema, pongSema, maxIterations);

        // TODO - Initiate the ping and pong threads, which will call
        // the run() hook method.
        ping.start();
        pong.start();
        
        // TODO - replace the following line with a CountDownLatch
        // barrier synchronizer call that waits for both threads to
        // finish.
        mLatch.await();
        
        System.out.println(finishString);
    }

    /**
     * The main() entry point method into PingPongRight program.
     * 
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        process("Ready...Set...Go!", 
                "Ping!  ",
                " Pong! ",
                "Done!",
                mMaxIterations);
    }
    
    private static String mPingString;
    private static String mPongString;
}
