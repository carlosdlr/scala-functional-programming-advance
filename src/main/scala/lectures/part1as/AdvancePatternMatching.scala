package lectures.part1as

object AdvancePatternMatching extends App {

  val numbers = List(1)
  val description = numbers match {
    case head:: Nil => println(s"the only element is $head.")
    case _ =>
  }

  /*
  - constants
  - wildcards
  - case classes
  - tuples
  - some special magic like above
   */

  class Person(val name: String, val age: Int)

  object Person {
    // this method is used to infer in the pattern matching
    def unapply(person: Person): Option[(String, Int)] =
    if (person.age < 21) None
    else Some((person.name, person.age)) // this condition will be check in the pattern matching

    def unapply(age: Int): Option[String] =
      Some(if (age < 21) "minor" else "major")
  }

  val bob = new Person("Bob", 25)
  val greeting = bob match {
    case Person(n, a) => s"Hi, my name is $n and I am $a yo."
  }

  println(greeting)

  val legalStatus = bob.age match {
    case Person(status) => s"My legal status is $status"
  }

  println(legalStatus)

  /*
  Exercise.
   */
  object even {
    def unapply(arg: Int): Boolean = arg % 2 == 0
  }

  object singleDigit {
    def unapply(arg: Int): Boolean = arg > -10 && arg < 10
  }


  val n: Int = 8
  val mathProperty = n match {
    case singleDigit() => "Single digit"
    case even() => "an even number"
    case _ => "no property"
  }

  println(mathProperty)
}