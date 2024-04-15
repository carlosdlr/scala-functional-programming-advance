package lectures.part5typesystem

object Variance extends App {

  trait Animal

  class Dog extends Animal

  class Cat extends Animal

  class Crocodile extends Animal

  // what is variance?
  // "inheritance" - type substitution of generics

  class Cage[T]

  // yes - covariance
  class CCage[+T]

  val ccage: CCage[Animal] = new CCage[Cat]

  // no - invariance
  class ICage[T]
  //  val icage: ICage[Animal] = new ICage[Cat]
  //  val x: Int = "Hello"

  // opposite = contravariance
  class XCage[-T]

  val xcage: XCage[Cat] = new XCage[Animal]

  class InvariantCage[T](animal: T) // invariant

  // covariant positions
  class CovariantCage[+T](val animal: T) // COVARIANT POSITION

  // class ContravariantCage[-T](val animal: T)
  /*
   val catCage: XCage[Cat] = new XCage[Animal](new Crocodile)
   */
  // class CovariantVariableCage[+T](var animal: T) // types of vars are in contravariant position
  /*
   val ccage: CCage[Animal] = new CCage[Cat](new Cat)
  cage.animal = new Crocodile
   */
  class InvariantVariableCage[T](var animal: T) // ok

  // trait AnotherCovariantCage[+T] {
  //   def addAnimal(animal: T) = true // CONTRAVARIANT POSITION
  // }

  /*
    val ccage: CCage[Animal] = new CCage[Dog]
    ccage.add(new Cat)
   */

  class AnotherContraVariantCage[-T] {
    def addAnimal(animal: T) = true
  }

  val acc: AnotherContraVariantCage[Cat] = new AnotherContraVariantCage[Animal]
  acc.addAnimal(new Cat)

  class Kitty extends Cat

  acc.addAnimal(new Kitty)

  class MyList[+A] {
    def add[B >: A](element: B): MyList[B] = new MyList[B] // widening the type
  }

  val emptyList = new MyList[Kitty]
  val animals = emptyList.add(new Kitty)
  val moreAnimals = animals.add(new Cat)
  val evenMoreAnimal = moreAnimals.add(new Dog)

  // METHOD ARGUMENTS ARE IN CONTRAVARIANT POSITION.

  //return types
  class PetShop[-T] {
    //def get(isITaPuppy: Boolean): T // method return tyoes are in a covariant position
    /*
    val catShop = new PetShop[Animal] {
      def get(isITaPuppy: Boolean): Animal = new Cat
    }

    val dogShop PetShop = catShop
    dogShop.get(true)

     */

    def get[S <: T](isITaPuppy: Boolean, defaultAnimal: S): S = defaultAnimal
  }

  val shop: PetShop[Dog] = new PetShop[Animal]
  //val evilCat = shop.get(true, new Cat)
  class Terranova extends Dog
  val bigFurry = shop.get(true, new Terranova)

  /*
    BIG RULE
    - method arguments are in contravariant position
    - return types are in covariant position
   */

  /**
   * 1. Invariant, covariant, contravariant
   * Parking[T](things: List[T]) {
   *  park(vehicle: T)
   *  impound(vehicles: List[T])
   *  checkVehicles(conditions: String): List[T]
   * }
   *
   * 2. used someone else's API: IList[T]
   * 3. Parking = monad!
   *  - flatMap
   */

  class Vehicle
  class Bike extends Vehicle
  class Car extends Vehicle

  class IList[T]

  class IParking[T](vehicles: List[T]) {
    def park(vehicle: T): IParking[T] = ???
    def impound(vehicles: List[T]): IParking[T] = ???
    def checkVehicles(conditions: String): List[T] = ???

    def flatMap[S](f: T => IParking[S]): IParking[S] = ???
  }

  class CParking[+T](vehicles: List[T]) {
    def park[S >: T](vehicle: S): CParking[S] = ???
    def impound[S >: T](vehicles: List[S]): CParking[S] = ???
    def checkVehicles(conditions: String): List[T] = ???
    
    def flatMap[S](f: T => CParking[S]): CParking[S] = ???
  }

  class XParking[-T](vehicles: List[T]) {
    def park(vehicle: T): XParking[T] = ???
    def impound(vehicles: List[T]): XParking[T] = ???
    def checkVehicles[S <: T](conditions: String): List[S] = ???

    def flatMap[R <: T, S](f: R => XParking[S]): XParking[S] = ???
  }

  /*
  Rule of thumb
      - use covariance = collection of things
      - use contravariance = group of actions
   */

  class CParking2[+T](vehicles: IList[T]) {
    def park[S >: T](vehicle: S): CParking2[S] = ???
    def impound[S >: T](vehicles: IList[S]): CParking2[S] = ???
    def checkVehicles[S >: T](conditions: String): IList[S] = ???
  }

  class XParking2[-T](vehicles: IList[T]) {
    def park(vehicle: List[T]): XParking2[T] = ???
    def impound[S <: T](vehicles: IList[S]): XParking2[S] = ???
    def checkVehicles[S <: T](conditions: String): IList[S] = ???
  }


  // flatMap






}
