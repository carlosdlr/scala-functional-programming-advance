package exercises

import lectures.part4implicits.TypeClasses.{User}

object EqualityPlayground extends App {

  /*
  Equality class implementation
   */
  trait Equal[T] {
    def apply(a: T, b: T): Boolean
  }

  implicit object NameEquality extends Equal[User] {
    override def apply(a: User, b: User): Boolean = a.name == b.name
  }

  object fullEquality extends Equal[User] {
    override def apply(a: User, b: User): Boolean =
      a.name == b.name && a.email == b.email
  }

  /*
   Exercise: implement the Type class pattern for the equality tc
    */

  object Equal {
    def apply[T](a: T, b: T)(implicit equalizer: Equal[T]): Boolean =
      equalizer.apply(a, b)
  }

  val carlos = User("Carlos", 42, "carlos@gmail.com")
  val anotherCarlos = User("Carlos", 45, "anotherCarlos@gmail.com")
  println(Equal(carlos, anotherCarlos))

  // AD-HOC polymorphism

  /*
   Exercise - improve the Equal TC with an implicit conversion
   ===(anotherValue: T)
   !==(anotherValue: T)
   */
  implicit class TypeSafeEqual[T](value: T) {
    def ===(other: T)(implicit equalizer: Equal[T]): Boolean
    = equalizer.apply(value, other)

    def !==(other: T)(implicit equalizer: Equal[T]): Boolean
    = ! equalizer.apply(value, other)
  }

  println(carlos === anotherCarlos)
  /*
   steps done by the compiler
   carlos.===(anotherCarlos)
   new TypeSafeEqual[User](john).===(anotherJohn)
   new TypeSafeEqual[User](john).===(anotherJohn)(NameEquiality)
   */
  /*
  TYPE SAFE
   */
  //println(carlos == 42)
  //println(carlos === 42) type safe

}
