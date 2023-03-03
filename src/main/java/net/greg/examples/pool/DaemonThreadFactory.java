package net.greg.examples.pool;

import java.util.*;

import java.util.concurrent.ThreadFactory;


/**
 * https://stackoverflow.com/questions/13883293/turning-an-executorservice-to-daemon-in-java
 * https://bit.ly/3fCxvVK
 *
 * By default, the a daemon inherits its parent thread's daemon status.
 *
 * Lifecycle of a daemon thread:
 *
 *   Under normal shutdown conditions, thee JVM doesn't wait for Daemon threads
 *   when the main thread of execution (the daemon's parent thread) is exiting;
 *   however, the JVM waits for any/all the user (regular) threads to finish
 *   their execution before the JVM shuts down
 *
 * NB  A daemon loses the capablility for stdin/stdout (I/O)
 */
public final class DaemonThreadFactory implements ThreadFactory {

  private int counter = 0;


  private static final List<WorkerThread> members =
    new ArrayList();


  public static final ThreadFactory INSTANCE =
    new DaemonThreadFactory();


  private DaemonThreadFactory() { }


  @Override
  public Thread newThread(Runnable runnable) {

    WorkerThread worker = new WorkerThread(runnable);

    worker.setDaemon(
      Boolean.valueOf(System.getenv("DAEMON")));


    if (counter == 20) {
      worker.setPriority(10);
    }

    if (counter == 1) {
      worker.setPriority(2);
    }

    members.add(worker);

    counter++;

    return worker;
  }


  public static final void publish(int capacity) {

    // thread-safe complement to StringBuilder
    StringBuffer sb = new StringBuffer();

    sb.append("\n\n  Thread Readout Semantics ...\n").
    append("\n    Thread[Thread_3,5,main]").
    append("\n    Thread[Thread_3    | 5        | main] ").
    append("\n    Thread[Thread_name | priority | parent] ");

    // affirm
    System.err.printf(
      "\n" + capacity +
      " Daemon Threads have been created w/these" +
      " characteristics:\n\n");

    members.stream().forEach(item -> {

      // type-safe downcast
      System.err.print(
        "\n  Factory-created --> " + item +
        "  " + ((WorkerThread) item).isDaemon());
    });

    // coerce toString
    System.err.println(sb + "\n\n");
  }
}
