package net.greg.examples.pool;

import java.util.concurrent.*;


public final class PoolLifecycle {

  private static ExecutorService pool;


  public static void main(String any[]) {


    // CLI in origin
    int capacity = Integer.parseInt(any[0]);

    pool =
      Executors.newFixedThreadPool(
        capacity, DaemonThreadFactory.INSTANCE);

    new PoolLifecycle().start(capacity);
  }


  /**
   *
   */
  private void start(int capacity) {

    System.err.println(
      "\nPoolLifecycle.start() w/capacity " + capacity);

    for (int i= 0; i < capacity; i++) {

      pool.submit(() -> {
        System.err.println("QQQ");
      });
    }

    boolean isDaemon =
      Boolean.valueOf(System.getenv("DAEMON"));

    String KIND =
      isDaemon ? " Daemon " :  " Non-Daemon ";


    System.err.println(
      "\nPoolLifecycle class Starts " +
      capacity + KIND + "Threads ...\n");

    // affirm
    DaemonThreadFactory.publish(capacity);


    /*
     * postpone the termination of the main thread os
     * execution in order to inspect the printStackTrace
     * of the daemon threads which the daemom thread pool
     * conditionally spawns
     */
    try {
      Thread.currentThread().sleep(60_000);
    }
    catch (InterruptedException e) {  }

  }
}
