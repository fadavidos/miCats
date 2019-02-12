package com.miCats.Functor

import org.scalatest.AsyncFunSuite

class FunctorTest extends AsyncFunSuite{

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

  test("Option es un functor, con el cual demostramos que cumple la ley de Identity") {
    val valorOpcional: Option[Int] = Some(10)
    assertResult( Some(10) ) {
      valorOpcional.map( identity )
    }
  }

  test("Future es un functor, con el cual demostramos que cumple la ley de Composition") {
    import scala.concurrent.{Future, Await}
    import scala.concurrent.duration._

    val futuro:Future[String] = Future("Cats")

    def mayusculas(valor: String): String = {
      valor.toUpperCase
    }

    def saludar(valor: String): String = {
      s"Hola ${valor}"
    }

    val saludo1: Future[String] = futuro.map(valor => saludar(mayusculas(valor)))
    val saludo2: Future[String] = futuro.map(valor => mayusculas(valor)).map( valor => saludar(valor))

    val resultadoSaludo1: String = Await.result( saludo1, 3 seconds )
    val resultadoSaludo2: String = Await.result( saludo2, 3 seconds )

    assert( resultadoSaludo1 == resultadoSaludo2)
    assert( resultadoSaludo1 == "Hola CATS")
  }

  test("Componiendo diferentes Functores. " +
    "Para componer diferentes functores se usa la función `compose` y así evitamos tener que estar " +
    "usando repetidamente la función `map`") {

    import cats.Functor
    import cats.implicits._
    import scala.concurrent.Future

    val dato: Future[Option[Int]] = Future(Some(5))

    def doblarNumero(numero: Int): Int = numero * 2

    /*
    Al llamar la función `map` se reciben dos parámetros:
      1. (fa: Future[Option[A]) un tipo de dato que cumpla con F[G[_]]
      2. (f: A => B) la función que parsará del tipo de dato A a B
     */
    val resultadoCombinacion: Future[Option[Int]] = Functor[Future].compose[Option].map(dato)(doblarNumero)

    /*
    Sin hacer composición la manera para aplicar la función al tipo de dato Int sería
    hacer map sobre el Futuro, posteriormente map sobre el Option, y en ese momento
    aplicar la función sobre el valor del Option.

      dato.map(_.map(doblarNumero))

     */
    resultadoCombinacion map { valor => assert(valor == Some(10)) }
  }

  test("otra composición") {

//    val listOption: List[Option[Int]] = List(Some(1), None, Some(2))
//    val r: List[Option[Int]] = Functor[List].compose[Option].map(listOption)(_ + 1)
//
//    def anidado[F[_]: Functor, A](elemento: F[A]): F[A] = {
//      Functor[F].map(elemento)( e => {
//        //println(e)
//        e
//      })
//    }


//    val composicion = Functor[List].compose[Option]
//    type tipoListaOpcional[A] = List[Option[A]]
//    val listOptionFuture: List[Option[Future[Int]]] = List(Some(Future(1)), None, Some(Future(2)))
//    val x: tipoListaOpcional[Int] = anidado[tipoListaOpcional, Int](listOption)(composicion)
//
//    println(s"si: ${x}")


    ???

  }

  /*
    Type constructor:
    * Son como tipos de tipos. Se diferencian de los tipos (type) normales porque tienen un "hole" (_).
    * Option es un type constructor porque tiene un "hole" Option[_], donde podemos reemplazar el _ por el
        tipo que queremos, ejemplo: Option[Int], Option[Persona].
    *Se debe diferenciar los type de los type constructor
        List    => type constructor
        List[A] => type
        Se puede hacer el simil con las funciones y los valores. Una función necesita parámetros para dar un resultado
        Mientras que el valor ya es el resultado en si. Los Type Constructor necesitan un "parámetro"
   */

}
