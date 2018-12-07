package com.miCats.Functor

import org.scalatest.{FunSuite, TestSuite}

class FunctorTest extends FunSuite{

  /*
  Un funtor:
   * Es un type class
   * Tiene una función clave llamada map => def map[A, B](fa: F[A])(f: A => B): F[B]
      - (fa: F[A]) es un tipo de dato envuelto en un Functor
      - (f: A => B) es una función que transforma de un tipo A a un tipo B. Convertir un String a Int.
      - F[B] Es el resultado final envuelto en un functor.

    * Las leyes que se deben cumplir para la función map son:
        - Identity: Cuando se llama la función map sobre un Functor, éste devuelve el mismo Fuctor.
            Esto garantiza que si se aplica una función sobre el map, este no hará ninguna otra operación
            sobre el valor del Functor.


   Qué es un Type Class ?
    * Los Type Class nos permiten agregar nuevas funcionalidades a librerías ya existentes. Sin usar la herencia tradicional.
    * En Cats, una Type Class se representa por un Trait y al menos un parámetro.


   */

  test("") {
    import cats.Functor


  }

}
