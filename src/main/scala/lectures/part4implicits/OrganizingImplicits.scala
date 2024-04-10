package lectures.part4implicits

object OrganizingImplicits extends App {
  // implicit function definition without parentheses is used by sorted function
  // with parentheses, not
  implicit def reverseOrdering: Ordering[Int] = Ordering.fromLessThan(_ > _)
  //implicit val normalOrdering: Ordering[Int] = Ordering.fromLessThan(_ < _)
  println(List(1,4,5,3,2).sorted)

  // scala.Predef this package contains all the predefined implicit values
  // it's imported by default

  /*
    potential implicit (used as implicit parameters):
        - val/var
        - objects
        - accessor methods = definitions with no parentheses
   */

  case class Person(name: String, age: Int)

  val persons = List(
    Person("Steve", 30),
    Person("Amy", 22),
    Person("John", 66)
  )

//  object Person {
//    implicit val alphabeticOrdering: Ordering[Person] =
//      Ordering.fromLessThan((a, b) => a.name.compareTo(b.name) < 0)
//  }

//  implicit val ageOrdering: Ordering[Person] =
//    Ordering.fromLessThan((a, b) => a.age < b.age)

//  println(persons.sorted)

  /*
    Implicit scope
      - normal scope = LOCAL SCOPE
      - imported scope
      - companions of all types involved in the method signature
        - List
        - Ordering
        - all the types involved = A or any supertype
   */

  // def sorted[B >: A](implicit ord: Ordering[B]): Repr

  object AlphabeticNameOrdering {
    implicit val alphabeticOrdering: Ordering[Person] =
      Ordering.fromLessThan((a, b) => a.name.compareTo(b.name) < 0)
  }

  object AgeOrdering {
    implicit val ageOrdering: Ordering[Person] =
      Ordering.fromLessThan((a, b) => a.age < b.age)
  }

  import AlphabeticNameOrdering._
  println(persons.sorted)

  /*
    Best Practices
      When defining an implicit val:
      #1
        - if there is a single possible value for it
          and you can edit the code for the type
      then define the implicit in the companion object of the class

     #2 - if there are many possible values for it but but a single good one
          and you can edit the code for the type
      then define the good implicit in the companion
   */

  /*
    Exercise.
      types of ordering
        - totalPrice = most used (50%) by the code
        - by unit count = 25%
        - by unit price = 25%
   */

  case class Purchase(nUnits: Int, unitPrice: Double)
  // put the most used in the companion object
  object Purchase {
    implicit val totalPriceOrdering: Ordering[Purchase] =
      Ordering.fromLessThan(
        (a, b) => a.nUnits * a.unitPrice < b.nUnits * b.unitPrice)
  }

  // for the non common used ones use imports
  object UnitCountOrdering {
    implicit val unitCountOrdering: Ordering[Purchase] =
      Ordering.fromLessThan(_.nUnits < _.nUnits)
  }

  object UnitPriceOrdering {
    implicit val unitPriceOrdering: Ordering[Purchase] =
      Ordering.fromLessThan(_.unitPrice < _.unitPrice)
  }



}
