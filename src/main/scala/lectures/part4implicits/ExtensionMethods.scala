package lectures.part4implicits

object ExtensionMethods extends App {

  case class Person(name: String) {
    def greet(): String = s"Hi, I'm $name, how can i help?"
  }

  extension(string: String) { // extension methods
    def greetAsPerson(): String = Person(string).greet()
  }

  val carlosGreeting = "Carlos".greetAsPerson()

  // extension methods are equivalent to implicit classes
  object Scala2ExtensionMethods {
    implicit class RichInt(val value: Int) {
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
    }
  }

  val is3Even = 3.isEven // new RichInt(3).isEven

  extension(value: Int) {
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
  }

  // generic extensions
  extension[A](list: List[A]) {
    def ends: (A, A) = (list.head, list.last)
    def extremes(using ordering: Ordering[A]): (A, A) = list.sorted.ends // <-- can call an extension method
  }

}
