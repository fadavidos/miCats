package com.miCats.Functor

import org.scalatest.FunSuite

class ApplicativeTest extends FunSuite {

  /*
    Applicative Functors o más fácil Applicative: Applicative[F[_]]
    * Extiende de Functor
    * Tiene una función `pure`, que permite embolver un dato dentro de un Functor =>  def pure[A](x: A): F[A]

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

}
