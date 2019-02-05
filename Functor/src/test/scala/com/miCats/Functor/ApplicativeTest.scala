package com.miCats.Functor

import org.scalatest.FunSuite

class ApplicativeTest extends FunSuite {

  /*
    Applicative Functors o más fácil Applicative: Applicative[F[_]]
    * Extiende de Functor
    * Tiene una función `pure`, que permite embolver un dato dentro de un type constructor =>  def pure[A](x: A): F[A]

   */

  test("Podemos crear un Applicative de Option dando simplemente " +
    "el valor interno que tendrá el Option") {
    import cats.Applicative
    import cats.implicits._

    val valorOpcional: Option[Int] = Applicative[Option].pure(1)

    assertResult( Some(1 )) {
      valorOpcional
    }
  }

  test("Cartesian Builder") {
    import cats.data.Validated
    import cats.data.Validated.{Valid, Invalid}
    import cats.syntax.apply._

    def validarMayoriaEdad(edad: Int): Validated[String, Int] = {
      if ( edad >= 18 )
        Valid(edad)
      else
        Invalid("Aún no es mayor de edad")
    }

    def validarEmail(email: String): Validated[String, String] = {
      if( email.contains("@"))
        Valid(email)
      else
        Invalid("El correo no es correcto")
    }

    def validarGenero(char: Char): Validated[String, Char] = {
      if(char.equals('M') || char.equals('F'))
        Valid(char)
      else
        Invalid("El genero no existe")
    }

    case class Usuario(nombre: String, edad: Int, email: String, genero: Char)

    val miUsario =Usuario("Juan", 10, "invalido",'M')

//    (
//      validarEmail(miUsario.email),
//      validarMayoriaEdad(miUsario.edad),
//      validarGenero(miUsario.genero)
//    ).mapN((mail, edad, genero) => mail )






  }

  test("por que no sirve") {
    import cats.implicits._

    val f: (Int, Char) => Double = (i, c) => (i + c).toDouble

    val int: Option[Int] = Some(5)
    val char: Option[Char] = Some('a')
    val x: Option[Char => Double] = int.map(i => (c: Char) => f(i, c)) // what now?


  }

  test("traverse") {
    import cats.syntax.traverse._
    import cats.instances.list._
    import cats.instances.option._

    val list = List(0, 1,2,3,4)

    def table2( number: Int ): Option[Int] = {
      if(number == 0)
        None
      else
        Some(number * 2)
    }

    val result: Option[List[Int]] = list.traverse[Option, Int](table2)

    println(result)
  }








}
