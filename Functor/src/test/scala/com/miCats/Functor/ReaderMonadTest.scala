package com.miCats.Functor

import org.scalatest.FunSuite

class ReaderMonadTest extends FunSuite {

  /*
    Reader nos permite crear operaciones o cumputos que dependen de alguna entrada.
    Un uso común para los Reader es la inyección de dependencias.
    Un Reader representa una función A => B
   */

  test("Creamos una función que necesita una Operacion para poder ejecutarse. " +
    "Dado:" +
    " - Dos funciones que suma y restan dos valores de una operacion. Pero la operación no se pasa " +
    "     como parámetro de las funciones, sino que entra como 'inyeción de dependencias'" +
    " - Una operación con dos números." +
    "Entonces: " +
    " - Al llamar la función de suma o la de resta no se envía la operación, pero al llamar la función " +
    "     `run` nos pide la operación, para poder obtener el valor. " +
    " - Al llamar las dos funciones dentro de un for-comprehension solo es necesario llamar " +
    "     la función `run` una sola vez. ") {
    import cats.data.Reader

    case class Operacion(numero1: Int, numero2: Int)

    //Esta función necesita un Operacion para poder efectuar la suma que tiene dentro de la función
    def redSuma(): Reader[Operacion, Int] = Reader {
      case operacion: Operacion =>
        operacion.numero1 + operacion.numero2
    }

    def redResta(): Reader[Operacion, Int] = Reader {
      case operacion: Operacion =>
        operacion.numero1 - operacion.numero2
    }

    val operacion = Operacion(4, 3)
    // Es necesario llamar la función run y parsarle "lo que necesita" para
    // poder ejecutar el computo interno.
    val resultadoSuma: Int = redSuma().run(operacion)
    assert( resultadoSuma == 7 )
    val resultadoResta: Int = redResta().run(operacion)
    assert( resultadoResta == 1)

    // También podemos lograr lo mismo usando un for-comprehension
    val resultadoTuplaFor = for {
      resSuma <- redSuma()
      resResta <- redResta()
    } yield {
      (resSuma, resResta)
    }
    // Al final solo pasaremos una sola vez la operación
    val resultadoFor: (Int, Int) = resultadoTuplaFor.run(operacion)

    assert( resultadoFor == (7, 1))

  }

}
