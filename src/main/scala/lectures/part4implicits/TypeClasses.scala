package lectures.part4implicits

object TypeClasses extends App {

  trait HTMLWritable {
    def toHtml: String

  }

  case class User(name: String, age: Int, email: String) extends HTMLWritable {
    override def toHtml: String = s"<div> $name ($age yo) <a href=$email> </div>"
  }

  User("Carlos", 42, "carlos@gmail.com").toHtml

  /*
    disadvantages of the code above
    1 - fo the types WE write
    2 - ONE implementation out of quite a number
   */

  // option 2 pattern matching
  object HTMLSerializerPM {
    def serializeToHtml(value: Any) = value match {
      case User(n, a, e) =>
      //case java.util.Date =>
      case _ =>
    }
  }

  /*
    disadvantages of the code above

    1 - lost type safety
    2 - need to modify the code every time
    3 - still ONE implementation
   */

  trait HTMLSerializer[T] {
    def serialize(value: T): String
  }

  implicit object UserSerializer extends HTMLSerializer[User] {
    def serialize(user: User): String = s"<div>${user.name} (${user.age} yo) <a href=${user.email}> </div>"
  }

  val carlos = User("Carlos", 42, "carlos@gmail.com")
  println(UserSerializer.serialize(carlos))

  // advantages of the code above
  // 1 - we can define serializers for other types
  import java.util.Date
  object DataSerializer extends HTMLSerializer[Date] {
    override def serialize(date: Date): String = s"<div>${date.toString()}</div>"
  }

  // 2 - we can define multiple serializers
  object PartialUserSerializer extends HTMLSerializer[User] {
    def serialize(user: User): String = s"<div>${user.name}</div>"
  }


  // part 2
  object HTMLSerializer {
    def serialize[T](value: T)(implicit serializer: HTMLSerializer[T]): String =
      serializer.serialize(value)

    def apply[T](implicit serializer: HTMLSerializer[T]) = serializer
  }

  implicit object IntSerializer extends HTMLSerializer[Int] {
    override def serialize(value: Int): String = s"<div style= {color: blue}>$value</div>"
  }

  // using implicit objects
  println(HTMLSerializer.serialize(42))
  println(HTMLSerializer.serialize(carlos))

  // using a factory method apply
  // access to the entire type class interface
  println(HTMLSerializer[User].serialize(carlos))

  // part 3
  implicit class HTMLEnrichment[T](value: T) {
    def toHTML(implicit serializer: HTMLSerializer[T]): String =
      serializer.serialize(value)
  }

  println(carlos.toHTML) // println(new HTMLEnrichment[User](john).toHTML(UserSerializer))

  /*
    - extend to new types
    - choose implementation
    - super expressive
   */

  println(2.toHTML)
  println(carlos.toHTML(PartialUserSerializer))

  /*
    - type class itself
    - type class instances (some if which are implicit) --- UserSerializer, IntSerializer
    - conversion with implicit classes --- HTMLEnrichment
   */

  // context bounds
  def htmlBoilerPlate[T](content: T)(implicit serializer: HTMLSerializer[T]): String
  = s"<html><body> ${content.toHTML(serializer)}</body></html>"

  def htmlSugar[T: HTMLSerializer](content: T):/*context implicit serializer bounded */ String = {
    val serializer = implicitly[HTMLSerializer[T]]
    // user serializer
     s"<html><body> ${content.toHTML(serializer)}</body></html>"
  }

  // implicitly
  case class Permissions(mask: String)
  implicit val defaultPermissions: Permissions = Permissions("0744")

  // in some other part of the code
  val standardPermissions = implicitly[Permissions]

}
