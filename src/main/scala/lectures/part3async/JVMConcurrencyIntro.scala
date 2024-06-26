package lectures.part3async

import java.util.concurrent.Executors

object JVMConcurrencyIntro extends App {

  /*
  interface Runnable {
    public void run()
  }
   */
  // JVM threads
  val runnable = new Thread(new Runnable {
    override def run(): Unit = println("Running in parallel")
  })

  val aThread = new Thread(runnable)

  aThread.start() // gives the signal to the jvm to start jvm thread
  // create a JVM thread => OS thread
  runnable.run() // doesn't do anything in parallel
  aThread.join() // blocks until aThread finishes running

  val threadHello = new Thread(() => (1 to 5).foreach(_ => println("Hello")))
  val threadGoodbye = new Thread(() => (1 to 5).foreach(_ => println("Goodbye")))
  threadHello.start()
  threadGoodbye.start()
  // different runs produce different results

  // executors to reuse threads
  val pool = Executors.newFixedThreadPool(10)
  pool.execute(() => println("something in the thread pool"))

  pool.execute(() => {
    Thread.sleep(1000)
    println("done after 1 second")
  })

  pool.execute(() => {
    Thread.sleep(1000)
    println("almost done")
    Thread.sleep(1000)
    println("done after 2 seconds")
  })

  pool.shutdown()
//  pool.execute(() => println("should not appear")) // this throws an exception in the calling thread

//  pool.shutdownNow()
  println(pool.isShutdown) // true
}
