package lectures.part1as

import scala.annotation.tailrec

object Recap extends App {

  //variables declaration
  val aCondition: Boolean = false
  val aCondtionalVal = if (aCondition) 42 else 46
  // instructions -> java vs expression -> scala

  //compiler infers types for us
  val aCodeBlock = {
    if (aCondition) 54
    56
  }

  //unit = void to generate side effects equivalent in java to Consumer interface
  val theUnit = println("Scala")

  //functions
  def aFunction(x: Int): Int = x + 1

  //recursion: stack and tail recursion
  @tailrec def factorial(n: Int, accumulator: Int): Int =
    if(n <= 0) accumulator
    else factorial(n-1, n * accumulator)

  // oop programming
  class Animal
  class Dog extends Animal
  val aDog: Animal = new Dog // subtyping polymorphism

  trait Carnivore {
    def eat(a: Animal): Unit
  }

  class Crocodile extends Animal with Carnivore {
    override def eat(a: Animal): Unit = println("Crunch")
  }

  val aCroc = new Crocodile
  aCroc.eat(aDog)
  aCroc eat aDog // natural language

  // anonymous classes
  val aCarnivore = new Carnivore {
    override def eat(a: Animal): Unit = println("roar!")
  }

  // generics
  abstract class MyList[+A] //variance and variance problems in this course
  // singleton and companion
  object MyList

  //case classes
  case class Person(name: String, age: Int)

  //exceptions and try/catch/finally
  val throwException = throw new RuntimeException // nothing
  val aPotentialFailure = try {
    throw new RuntimeException
  } catch {
    case e: Exception => "I caught and exception"
  } finally {
    println("some logs")
  }

  // functional programming
  val incrementer = new Function1[Int, Int] {
    override def apply(v1: Int): Int = v1 + 1
  }

  incrementer(1)

  val anonymousIncrementer = (x: Int) => x + 1
  List(1,2,3).map(anonymousIncrementer) // High order function
  // map, flatmap, filter

  // for comprehension
  val pair = for {
    num <- List(1,2,3)
    char <- List('a', 'b', 'c')
  } yield num +"-"+ char

  // scala collections: Seqs, Arrays, Lists, Vectors, Maps, Tuples
  val aMap = Map(
    "Carlos" -> 789
  )

  // "collections": Options, Try
  val anOption = Some(2)

  // pattern matching
  val x = 2
  val order = x match {
    case 1 => "first"
    case 2 => "second"
    case 3 => "third"
    case _ => x + "th"
  }

  val bob = Person ("Bob", 22)
  val greeting = bob match {
    case Person(n, _) => s"Hi, my name is $n"
  }
}
