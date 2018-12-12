package com.miCats.Functor

import org.scalatest.FunSuite

class MonadTest extends FunSuite {

  /*
    Monad es básicamente cualquier cosa que tenga la función `flatmap`.
    Una Monada es un mecanismo para computación sequencial.
    Cada monada es también un Fuctor.

    Una Monada tiene:
      - Una función `pure`
      - Una función `flatmap`

    Esta es la definición de una Monada:

    import scala.language.higherKinds

    trait Monad[F[_]] {
      def pure[A](value: A): F[A]
      def flatMap[A, B](value: F[A])(func: A => F[B]): F[B]
    }


    `pure` y `flatmap` deben obedecer 3 leyes:
        - Left identity: pure(a).flatMap(f) == f(a)
        - Right identity: m.flatMap(pure) == m
        - Associativity: m.flatMap(f).flatMap(g) == m.flatMap(x => f(x).flatMap(g))




   */

}
