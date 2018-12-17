package com.miCats.Functor

import org.scalatest.FunSuite

class StateMonadTest extends FunSuite {

  /*
    State Monad se usa para pasar un estado como parte de un computo.
    Con State Monad podemos modelar mutabilidad en la programación funcional pura, sin usar mutabilidad.

   */

  test("Se crea un State que dado un número lo multiplica por 5 y devuelve su valor y un String " +
    "indicando cuándo dió la multiplicación. ") {
    import cats.data.State

    val por5 = State[Int, String] { numero =>
      val resultado = numero * 5
      (resultado, s"${numero} multiplicado por 5 es ${resultado}")
    }

    val numeroAMultiplicar = 7
    /*
    La función `run` permite pasar un estado inicial, además que es la función que nos permite
    obtener tanto estado final "trasnformado" (por no llamarlo mutado) y el valor final.
     */
    assert( por5.run(numeroAMultiplicar).value == (35,"7 multiplicado por 5 es 35"))
    /*
    Con la función `runS` obtenemos el valor del estado final
     */
    assert( por5.runS(numeroAMultiplicar).value == 35 )
    /*
    Con la función `runA` obtenemos el valor final
     */
    assert( por5.runA(numeroAMultiplicar).value == "7 multiplicado por 5 es 35" )

  }

}
