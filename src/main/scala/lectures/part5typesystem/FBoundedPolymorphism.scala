package lectures.part5typesystem

object FBoundedPolymorphism extends App {

//  trait Animal {
//    def breed: List[Animal]
//  }
//
//  class Cat extends Animal {
//    override def breed: List[Animal] = ??? // List[Cat] !!
//  }
//
//  class Dog extends Animal {
//    override def breed: List[Animal] = ??? // List[Dog] !!
//  }
//

//  trait Animal {
//    def breed: List[Animal]
//  }
//
//  class Cat extends Animal {
//    override def breed: List[Cat] = ??? // List[Cat] !!
//  }
//
//  class Dog extends Animal {
//    override def breed: List[Cat] = ??? // List[CatAgain] !!
//  }
//
//  trait Animal {
//    def breed: List[Animal]
//  }
  

  // Solution 2 FBP
//  trait Animal[A <: Animal[A]] { // recursive type: F-Bounded polymorphism
//    def breed: List[Animal[A]]
//  }
//
//  class Cat extends Animal[Cat] {
//    override def breed: List[Cat] = ??? 
//  }
//
//  class Dog extends Animal[Dog] {
//    override def breed: List[Dog] = ???
//  }
//  
//  trait Entity[E <: Entity[E]] // ORM
//  class Person extends Comparable[Person] {
//    override def compareTo(o: Person): Int = ???
//  }
//  
//  class Crocodile extends Animal[Dog] {
//    override def breed: List[Animal[Dog]] = ???
//  }
  
  // Solution 3 FBP + Self types
//  trait Animal[A <: Animal[A]] { self: A => // reinforcing the generic type
//    def breed: List[Animal[A]]
//  }
//
//  class Cat extends Animal[Cat] {
//    override def breed: List[Animal[Cat]] = ???
//  }
//
//  class Dog extends Animal[Dog] {
//    override def breed: List[Animal[Dog]] = ???
//  }
//
////  class Crocodile extends Animal[Dog] { // a crocodile cannot be a dog
////    override def breed: List[Animal[Dog]] = ???
////  }
//
//  trait Fish extends Animal[Fish]
//  class Shark extends Fish {
//    override def breed: List[Animal[Fish]] = List(new Cod)
//  }
//  
//  class Cod extends  Fish {
//    override def breed: List[Animal[Fish]] = ???
//  }
  
  // Solution 4 type classes
//  trait Animal
//  trait CanBreed[A] {
//    def breed(a: A): List[A]
//  }
//  
//  class Dog extends Animal
//  object Dog {
//    implicit object DogsCanBreed extends CanBreed[Dog] {
//      override def breed(a: Dog): List[Dog] = List()
//    }
//  }
//  
//  implicit class CanBreedOps[A](animal: A) {
//    def breed(implicit canBreed: CanBreed[A]): List[A] =
//      canBreed.breed(animal)
//  }
//  
//  val dog = new Dog
//  dog.breed
//  
//  /*
//   new CanBreedOps[Dog](dog).breed
//   implicit value to pass to breed: Dog.DogsCanBreed
//   */
//  
//  class Cat extends Animal
//  object Cat {
//    implicit object CatsCanBreed extends CanBreed[Dog] {
//      override def breed(a: Dog): List[Dog] = List()
//    }
//  }
//  
//  val cat = new Cat
//  cat.breed
  
  trait Animal[A] { // pure type classes
    def breed(a: A): List[A]
  }
  
  class Dog
  object Dog {
    implicit object DogAnimal extends Animal[Dog] {
      override def breed(a: Dog): List[Dog] = List()
    }
  }
  
  implicit class AnimalOps[A](animal: A) {
    def breed(implicit animalTypeClassIntance: Animal[A]): List[A] =
      animalTypeClassIntance.breed(animal)
  }
  
  val dog = new Dog
  dog.breed
}
