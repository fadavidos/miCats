package com.miCats.Functor


import org.scalatest.FunSuite

class WriteMonadTest extends FunSuite {

  /*

  WriteMonad nos permite ir llevando o registrando logs a lo largo de un computo, dando el resultado al final del mismo.
  Sirve para registrar logs o los pasos del computo.

   */

  test("Se crea nuestro primer Writer y usamos la función `writter` para obtener " +
    "los logs y la función `value` para obtener el resultado") {
    import cats.Id
    import cats.data.Writer
    import cats.data.WriterT

    /*
    Al crear el Writer nosotros esperamos un [List[String],Int], pero realmente obtenemos un
    WriterT[Id, List[String], Int], dado que Write implementa realmente a WriteT
    ( A esto se le llama Monad Transformer).

    Para este primero test relamente no debe importar el tema de monad transformer.
     */
    val miWriter: WriterT[Id, List[String], Int] = Writer(List("Acá tenemos un log","Acá tenemos otro log"), 2)

    assert( miWriter.written == List("Acá tenemos un log","Acá tenemos otro log") )
    assert( miWriter.value == 2 )
  }

  test("Es posible creawr un Writer solo con la información de los logs, " +
    "para esto podemos usar la función `tell` que nos provee `cats.syntax.writter`") {
    import cats.data.Writer
    import cats.syntax.writer._

    val miWriter: Writer[Vector[String], Unit] = Vector("uno", "dos").tell

    assert( miWriter.written == Vector("uno", "dos"))

  }

}
