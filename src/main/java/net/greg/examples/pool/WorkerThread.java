package net.greg.examples.pool;

import java.util.*;

import java.util.concurrent.TimeUnit;



public final class WorkerThread extends Thread  implements Runnable {

  // spread determines Latency VARIABILITY
  // which encourages the thread scheduler
  // to interleave/time-slice when there
  // are enough contending threads that
  // each do long-lasting operations
  private static final int MIN = 3;
  private static final int MAX = 17;

  /*
    This construnctor provides a 'hook' for the overridden
    method in ThreadFactory, the signature of which is:

       public Thread newThread(Runnable runnable)
  */
  public WorkerThread(Runnable runnable) {

  }


  @Override
  public void run() {

    long LATENCY =
      new Random().nextInt(
        (MAX - MIN)) + MIN;

    System.err.println(
      "WorkerThread: " +
      "  Random Latency == " + LATENCY +
      " | Daemon == " + isDaemon() +
      " | Priority == " + getPriority());

    try {

      TimeUnit.SECONDS.sleep(LATENCY);

      while (true) {

        System.err.println(
          "     Signal From -->  " +
          getClass().getName() +
          "  [ latency:" + LATENCY + " ]");
      }

    }
    catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
