package lectures.part4implicits

object Givens extends App {

  val aList = List(2,4,3,1)
  val anOrderedList = aList.sorted // implicit Ordering[Int]

  // scala 2
  object Implicits {
    implicit val descendingOrdering: Ordering[Int] = Ordering.fromLessThan(_ > _)
  }

  // scala 3 style
  object Givens {
    // givens are equivalent to implicit vals
    given descendingOrdering: Ordering[Int] = Ordering.fromLessThan(_ > _)
  }

  // instantiating an anonymous class
  object GivenAnonymousClassNaive {
    given descendingOrdering_V2: Ordering[Int] = new Ordering[Int] {
      override def compare(x: Int, y: Int) = x - y
    }
  }

  object GivenWith {
    given descendingOrdering_V3: Ordering[Int] with {
      override def compare(x: Int, y: Int) = x - y
    }
  }

  import GivenWith._ // in scala 3, this import does not import givens as well
  import GivenWith.given // this imports all givens

  // implicit arguments
  def extremes[A](list: List[A])(implicit ordering: Ordering[A]): (A, A) = {
    val sortedList = list.sorted
    (sortedList.head, sortedList.last) // tuple
  }

  def extremes_v2[A](list: List[A])(using /*scala 3*/ ordering: Ordering[A]): (A, A) = {
    val sortedList = list.sorted
    (sortedList.head, sortedList.last) // tuple
  }

  // implicit def (synthesize new implicit values)
  trait Combinatior[A] {
    def combine(x: A, y: A) : A
  }

  implicit def listOrdering[A](implicit simpleOrdering: Ordering[A],
                               combinator: Combinatior[A]): Ordering[List[A]] = {
    new Ordering[List[A]] {
      override def compare(x: List[A], y: List[A]) = {
        val sumX = x.reduce(combinator.combine)
        val sumY = y.reduce(combinator.combine)
        simpleOrdering.compare(sumX, sumY)
      }
    }
  }

  // scala 3 givens equivalent
  given listOrdering_v2[A](using simpleOrdering: Ordering[A],
                           combinator: Combinatior[A]): Ordering[List[A]] with {
    override def compare(x: List[A], y: List[A]) = { // synthesize new implicit values
      val sumX = x.reduce(combinator.combine)
      val sumY = y.reduce(combinator.combine)
      simpleOrdering.compare(sumX, sumY)
    }
  }

  println(anOrderedList)

  // implicit conversions (abused in Scala 2)
  case class Person(name: String) {
    def greet(): String = s"Hi, my name is $name"
  }

  implicit def string2Person(string: String): Person = Person(string)
  val carlosGreet = "Daniel".greet() // string2Person("Daniel").greet()

  // in Scala 3
  import scala.language.implicitConversions // required in scala 3
  given string2PersonConversion: Conversion[String, Person] with {
    override def apply(x: String) = Person(x)
  }

}
