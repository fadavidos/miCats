package com.miCats.Functor

import org.scalatest.FunSuite

class MonoidTest extends FunSuite {

  test("Puedo crear mi propio Monoid de Int. " +
    "Dado: " +
    " -> Un implicit para sobrescribir el valor inicial de Int por 999. " +
    " -> Una suma de dos número, uno con valor empty y otro con valor de 1. " +
    "Entonces: " +
    " -> El resultado de la suma es 1000. ") {

    // Import que permite trabajar con Monoid
    import cats.Monoid

    // Se define el propio Monoid para Int
    implicit val miMonoidParaInt: Monoid[Int] = new Monoid[Int] {
      /*
       El valor inicial o vacio para los Int en este test será
       de 999
      */
      override def empty: Int = 999
      override def combine(x: Int, y: Int): Int = x + y
    }

    val numero1 = 1
    val numero2: Int = Monoid[Int].empty

    val resultadoSuma = numero1 + numero2

    assert( resultadoSuma == 1000 )

  }

  test("Puedo crear mi propio monoid de Dinero. " +
    "Dado: " +
    " -> Un case clase que representa dinero con un valor interno. " +
    " -> Un implicit de Monoid[Dinero] donde el valor inicial es Dinero con valor 1.0." +
    " -> Una función que suma una lista de Dinero. " +
    "     Con un valor inicial que esta definido por el Monoid[Dinero]. " +
    " -> Dos lista con Dinero. Una vacía y otra con dos instancias del objeto Dinero. " +
    " -> Dos resultados con cada una de las sumas de las dos listas de Dinero. " +
    "Entonces: " +
    " -> La lista con los dos objetos de Dinero da un rasultado con la suma de sus valores internos más " +
    "     el valor inicial establecido (1.0) para el Monoid[Dinero]." +
    " -> La lista sin objetos de Dinero da una sumatoria de sus valores igual al valor inicial " +
    "     establecido por el Monoid[Dinero]. ") {

    // Import que permite trabajar con Monoid
    import cats.Monoid
    // Permite trabajar con la función `combine` pero haciendo referencia a |+|
    import cats.syntax.semigroup._

    case class Dinero(valor: Double)

    // Se define el propio monoid para Int
    implicit val miMonoidParaDinero: Monoid[Dinero] = new Monoid[Dinero] {
      /*
       El valor inicial o vacio para los Int en este test será
       de 999
      */
      override def empty: Dinero = Dinero(1.0)
      override def combine(x: Dinero, y: Dinero): Dinero = Dinero( x.valor + y.valor )
    }

    def sumarDinero( dineros: List[Dinero] ): Dinero =
      dineros.foldRight(Monoid[Dinero].empty)((dinero, acumulado) =>
        acumulado |+| dinero)

    // Lista con varios objetos de Dinero
    val muchoDinero = List[Dinero](Dinero(10.0), Dinero(25.0))
    // Lista sin objetos de Dinero.
    val sinDinero = List[Dinero]()

    // Se suman los dineros.
    val resultadoMuchoDinero: Dinero = sumarDinero( muchoDinero )
    val resultadoSinDinero: Dinero = sumarDinero( sinDinero )

    // El valor mínimo que siempre se sumará es de 1.0, en caso que la lista este vacia.
    assert( Dinero(36.0) == resultadoMuchoDinero)
    assert( Dinero(1.0) == resultadoSinDinero)
  }


}
