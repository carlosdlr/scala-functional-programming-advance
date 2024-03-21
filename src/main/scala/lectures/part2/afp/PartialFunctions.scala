package lectures.part2.afp

object PartialFunctions extends App {

  val aFunction = (x: Int) => x + 1 // Function[Int, Int] === Int => Int
  val aFussyFunction = (x: Int) =>
    if (x == 1) 42
    else if (x == 2) 56
    else if (x == 5) 999
    else throw new FunctionNotApplicableException

    class FunctionNotApplicableException extends RuntimeException

    val aNiceFussyFunction = (x: Int) => x match {
      case 1 => 42
      case 2 => 56
      case 5 => 999
    }
    // (1,2,5) => Int

    // partial function syntax
    val aPartialFunction: PartialFunction[Int, Int] = {
      case 1 => 42
      case 2 => 56
      case 5 => 999
    }

    println(aPartialFunction(1))
    //println(aPartialFunction(57273)) // throws patter matching exception

    // PF utilities
    println(aPartialFunction.isDefinedAt(66)) // checks if the implementation with the given value exists

    // (lift == mount) a safe way to test unimplemented cases
    val lifted = aPartialFunction.lift // Int => Option[Int]
    println(lifted(2)) // returns 42
    println(lifted(98)) // returns None due to the case doesn't exists

    // adds implementations to the existing implementation
    val pfChain = aPartialFunction.orElse[Int, Int] {
      case 45 => 67
    }

    println(pfChain(2)) // returns 56
    println(pfChain(45)) // returns 67 was added in the previous step to the chain

    // partial functions extends normal functions

    val aTotalFunction: Int => Int = {
      case 1 => 99
    }

    // High order functions accept partial functions as well
    val aMappedList = List(1,2,3).map {
      case 1 => 42
      case 2 => 78
      case 3 => 1000
    }

    println(aMappedList)

    /*
    Note: Partial functions can have only one parameter type
     */

  /**
   * Exercises
   * 1 - construct a PF instance (anonymous class)
   * 2 - dumb chatbot as a PF
   */

  val aManualFussyFunction = new PartialFunction[Int, Int] {
    override def apply(x: Int): Int = x match {
      case 1 => 42
      case 2 => 56
      case 5 => 999
    }

    override def isDefinedAt(x: Int): Boolean =
      x == 1 || x == 2 || x == 5
  }

  val chatbot: PartialFunction[String , String] = {
    case "hello" => "Hi, my name is HAL9000"
    case "goodbye" => "Once you start talking to me, there is no return, human"
    case "call mom" => "Unable to find your phone without your credit card"
  }

  scala.io.Source.stdin.getLines().map(chatbot).foreach(println)

}
