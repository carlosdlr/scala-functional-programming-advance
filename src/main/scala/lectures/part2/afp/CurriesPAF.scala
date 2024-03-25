package lectures.part2.afp

/**
 * Curries and partially applied functions
 */
object CurriesPAF extends App {

  //curried functions
  val supperAdder: Int => Int => Int =
    x => y => x + y // returns another function

  val add3 = supperAdder(3) // Int => Int = y => 3 + y
  println(add3(5))
  println(supperAdder(3)(5)) // curried function

  def curriedAdder(x: Int)(y: Int): Int = x + y // curried method

  val add4: Int => Int = curriedAdder(4)
  // lifting = ETA-EXPANSION

  // functions != methods (JVM limitation)
  def inc(x: Int): Int = x + 1
  List(1,2,3).map(x => inc(x)) //ETA-EXPANSION

  // Partial functions applications
  val add5 = curriedAdder(5) _ // converts this to a function Int => Int

  //EXERCISE
  val simpleAddFunction = (x: Int, y: Int) => x + y
  def simpleAddMethod(x: Int, y: Int) = x + y
  def curriedAddMethod(x: Int)(y: Int) = x + y

  // add7: Int => Int = y => 7 + y
  // as many different implementations of add7 using the above
  val add7 = (x: Int) => simpleAddFunction(7, x) // simplest
  val add7_2 = simpleAddFunction.curried(7)

  val add7_3 = curriedAddMethod(7) _ // Partial function
  val add7_4 = curriedAddMethod(7)(_)

  val add7_5 = simpleAddMethod(7, _: Int) // alternative syntax for turning methods into functions

  // underscores are powerful
  def concatenator(a: String, b: String, c: String): String = a + b + c
  val insertName = concatenator("Hello, I'm ", _: String, ", how are you?")
  // ETA-expansion converts to x: String => concatenator(hello, x, how are you)
  println(insertName("Carlos"))

 val fillInTheBlanks = concatenator("Hello, ", _:String, _:String) // (x, y) => concatenator("Hello," , x, y)
 println(fillInTheBlanks("Carlos", " Scala rocks in the JVM"))

 //EXERCISES
 /*
  1. Process a list of numbers and return their string representations with different formats
     Use the %4.2f, %8.6f and %14.12f with a curried formatter function
  */

  //println("%8.6f".format(Math.PI))

  def curriedFormatter(s: String)(number: Double): String = s.format(number)
  val numbers = List(Math.PI, Math.E, 1, 9.8, 1.3e-12)

  val simpleFormat = curriedFormatter("%4.2f") _ // lift
  val seriousFormat = curriedFormatter("%8.6f") _
  val preciseFormat = curriedFormatter("%14.12f") _

  println(numbers.map(preciseFormat))

  /*
  2. difference between
      - functions vs methods
      - parameters: by-name vs 0-lambda
   */
  def byName(n: => Int): Int = n + 1
 def byFunction(f: () => Int): Int = f() + 1

 def method: Int = 42
 def parentMethod(): Int = 42

 /*
 calling byName and byFunction
  - int
  - method
  - parentMethod
  - lambda
  - PAF
  */

 byName(23) // ok
 byName(method) // ok
 byName(parentMethod())
 //byName(parentMethod) // in scala 3 this syntax doesn't work
 //byName(() => 42) // not ok expects and int and instead a lambda is passed
  byName((() => 42)()) // it's ok is callign the function and getting the value
  // byName(parentMethod() _) // not ok just the underscore can be applied just to ypes


  //byFunction(45) // not ok expects a lambda
  //byFunction(method) // not ok does not do ETA-expansion
  byFunction(parentMethod) // ok compiles does ETA-expansion
  byFunction(() => 46) // works a lambda is passed
  byFunction(parentMethod _) //also works but warning s that the underscore is unnecessary
}
