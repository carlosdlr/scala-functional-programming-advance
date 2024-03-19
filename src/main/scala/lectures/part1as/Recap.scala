package lectures.part1as

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


}
