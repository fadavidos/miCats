package com.miCats.datatypes

import cats.arrow.FunctionK
import org.scalatest.AsyncFunSuite

class FreeMonadTest extends AsyncFunSuite{

  test("") {

    import cats.free.Free.liftF
    import cats.free.Free
    import cats.arrow.FunctionK
    import cats.{Id, ~>}
    import scala.collection.mutable
    import cats.free.Free._
    import cats.syntax.traverse._
    import cats.{ Id, ~> }
    import java.util.UUID
    import cats.arrow.FunctionK
    import cats.free.Free
    import cats.{Id, ~>}
    import cats.syntax.traverse._

    case class Person(id: String, name: Option[String])

    sealed trait PersonRepository[A]
    case class Consultar(id: String) extends PersonRepository[Person]
    case class Contar(id: String) extends PersonRepository[Int]
    case class Actualizar(person: Person) extends PersonRepository[Int]


    type miTipo[A] = Free[PersonRepository, A]

    def consultar(elId: String): miTipo[Person] =
      liftF[PersonRepository, Person](Consultar(elId))

    def validar(id: String): miTipo[Int] =
      liftF[PersonRepository, Int](Contar(id))

    def actualizar(thePerson: Person): miTipo[Int] =
      liftF[PersonRepository, Int](Actualizar(thePerson))


    for {
      c <- consultar("123")
      v <- validar("123")
    } yield {
      v
    }

    def algo: miTipo[Option[Int]] = {
      for {
        c <- consultar("123")
        v <- validar("123")
      } yield {
        Some(v)
      }
    }


    println(algo)


    assert(true)
  }

  test("https://github.com/softwaremill/free-tagless-compare/blob/master/src/main/scala/com/softwaremill/Refactoring.scala") {


    import cats.free.Free._
    import cats.syntax.traverse._

    import cats.{Monad, ~>}
    import java.util.UUID
    import cats.free.Free
    import cats.{Id, ~>}
    import scala.concurrent.ExecutionContext.Implicits.global
    import scala.concurrent.Future
    import cats.implicits._

    case class User(id: UUID, email: String, loyaltyPoints: Int) {
      def serialize: String = id.toString + "," + loyaltyPoints + "," + email
    }

    object User {
      def parse(s: String): User = {
        val parts = s.split(",")
        User(UUID.fromString(parts(0)), parts(2), parts(1).toInt)
      }
    }

    sealed trait UserRepositoryAlg[T]
    case class FindUser(id: UUID) extends UserRepositoryAlg[Option[User]]
    case class UpdateUser(u: User) extends UserRepositoryAlg[Unit]

    type UserRepository[T] = Free[UserRepositoryAlg, T]

    def findUser(id: UUID): UserRepository[Option[User]] = Free.liftF(FindUser(id))
    def updateUser(u: User): UserRepository[Unit] = Free.liftF(UpdateUser(u))

    def addPoints(userId: UUID, pointsToAdd: Int): UserRepository[Either[String, Unit]] = {
      findUser(userId).flatMap {
        case None => Free.pure(Left("User not found"))
        case Some(user) =>
          val updated = user.copy(loyaltyPoints = user.loyaltyPoints + pointsToAdd)
          updateUser(updated).map(_ => Right(()))
      }
    }

    val futureInterpreter = new (UserRepositoryAlg ~> Future) {
      override def apply[A](fa: UserRepositoryAlg[A]): Future[A] = fa match {
        case FindUser(id) => /* go and talk to a database */ Future.successful(None)
        case UpdateUser(u) => /* as above */ Future.successful(())
      }
    }


    val result: Future[Either[String, Unit]] =
      addPoints(UUID.randomUUID(), 10).foldMap(futureInterpreter)

    println(result)
    assert(true)
  }

}
