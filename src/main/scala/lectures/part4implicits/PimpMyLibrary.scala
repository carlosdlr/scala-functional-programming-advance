package lectures.part4implicits

object PimpMyLibrary extends App {

  // 2.isPrime

  implicit class RichInt(val value: Int) extends AnyVal {
    def isEven: Boolean = value % 2 == 0
    def sqrt: Double = Math.sqrt(value)

    def times(function: () => Unit): Unit = {
      def timesAux(n: Int): Unit =
        if (n <= 0) ()
        else {
          function()
          timesAux(n - 1)
        }
      timesAux(value)
    }
    def *[T](list: List[T]): List[T] = {
      def concatenate(n: Int): List[T] =
        if (n <= 0) List()
        else concatenate(n - 1) ++ list

      concatenate(value)
    }
  }

  implicit class RicherInt(richInt: RichInt) {
      def isOdd: Boolean = richInt.value % 2 !=0
  }

  new RichInt(42).sqrt

  // type enrichment = pimping
  42.isEven // new RichInt(42).isEven

  1 to 10

  import scala.concurrent.duration._
  3.seconds

  // compiler doesn't do multiple nested implicit searches
  //42.isO

  /*
    Enrich the String class, implementing
    - asInt
    - encrypt
      "Carlos" -> Ectnqu

    Keep enriching the Int class
      - times(function)
        3.times(() => ...)
      - *
        3 * List(1,2) => List(1,2,1,2,1,2)
   */

  implicit class RichString(string: String) {
    def asInt: Int = Integer.valueOf(string)
    def encrypt(cypherDistance: Int): String = string
      .map(c => (c + cypherDistance).asInstanceOf[Char])
  }

  println("3".asInt + 4)
  println("Carlos".encrypt(2))

  3.times(() => println("Scala Rocks!"))
  println(4 * List(1,2))

  // replicating javascript ugliness "3" / 4 heresy!!!
  implicit def stringToInt(string: String): Int = Integer.valueOf(string)
  println("6" / 2)

  // equivalent: implicit class RichAltInt(value: Int)
  class RichAltInt(value: Int)
  implicit def enrich(value: Int): RichAltInt = new RichAltInt(value)

  // danger zone
  implicit def intoBoolean(i: Int): Boolean = i == 1

  /*
    if (n) do something
    else do something else
   */

  val aConditionedValue = if (3) "OK" else "Something wrong"
  println(aConditionedValue)

  /*
   Good practices
      - keep type enrichment to implicit classes and type classes
      - avoid implicit defs as much as possible
      - package implicits clearly, bring into scope only what you need
      - if you need conversions, make them specific
   */
}
