package lectures.part4implicits

object ImplicitsIntro extends App {

  val pair = "Carlos" -> "666"  //arrowAssoc instance and will return a tuple
  val intPair = 1 -> 2

  case class Person(name: String) {
    def greet = s"Hi, my name is $name!"
  }

  implicit def fromStringToPerson(str: String): Person = Person(str)
  println("John".greet) // println(fromStringToPerson("John").greet)

//  class A {
//    def greet: Int = 2
//  }
//
//  will make the previous implicit definition not compile ambiguity
//  implicit def fromStringToA(str: String): A = new A

  // implicit parameters
  def increment(x: Int)(implicit amount: Int) = x + amount
  implicit val defaultAmount: Int = 10

  increment(2)
  // NOT default args


}
