package com.miCats.Functor

import org.scalatest.{FunSuite, TestSuite}

class MiFunctorTest extends FunSuite{

  /*
  Un funtor:
   * Es un type class
   * Tiene una función clave llamada map => def map[A, B](fa: F[A])(f: A => B): F[B]
      - (fa: F[A]) es un tipo de dato envuelto en un Functor
      - (f: A => B) es una función que transforma de un tipo A a un tipo B. Convertir un String a Int.
      - F[B] Es el resultado final envuelto en un functor.

   */

  test("") {
    import cats.Functor


  }

}
