package com.miCats.Functor

import org.scalatest.{FunSuite, TestSuite}

class FunctorTest extends FunSuite{

  /*
  Básicamente un Fuctor es cualquier cosa que tenga la función `map`.

  Un funtor F[A]:
   * Es un type class
   * Tiene una función clave llamada map => def map[A, B](fa: F[A])(f: A => B): F[B]
      - (fa: F[A]) es un tipo de dato envuelto en un Functor
      - (f: A => B) es una función que transforma de un tipo A a un tipo B. Convertir un String a Int.
      - F[B] Es el resultado final envuelto en un functor.

    * Las leyes que se deben cumplir para la función map son:
        - Identity: Cuando se llama la función map sobre un Functor, éste devuelve el mismo Fuctor.
            Esto garantiza que si se aplica una función sobre el map, este no hará ninguna otra operación
            sobre el valor del Functor.
        - Composition: hacer map sobre las funciones g y f es lo mismo que hacer map sobre g y luego map sobre f
            fa.map(g(f(_))) == fa.map(f).map(g)
   */

  test("") {
    import cats.Functor


  }

}
