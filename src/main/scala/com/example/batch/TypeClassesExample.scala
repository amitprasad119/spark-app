package com.example.batch

import scala.concurrent.ExecutionContext.Implicits

object TypeClassesExample extends App {

  case class User(name: String, age: Int, city: String)

  trait Equality[T] {
    def isEqual(left: T, right: T): Boolean
  }

  object Equality {

    def isEqual[T](left: T, right: T)(implicit equality: Equality[T]) =
      equality.isEqual(left, right)
  }

  implicit object IntEquality extends Equality[Int] {
    override def isEqual(left: Int, right: Int): Boolean = left == right
  }

  implicit object StringEquality extends Equality[String] {
    override def isEqual(left: String, right: String): Boolean = left.equalsIgnoreCase(right)
  }

  implicit object UserEquality extends Equality[User] {
    override def isEqual(left: User, right: User): Boolean =
      left.age == right.age // just override the equal rule on age
  }

  println(Equality.isEqual(1, 2))
  println(Equality.isEqual(User("Peter", 29, "London"), User("John", 29, "Bangalore")))

  Implicits

}
