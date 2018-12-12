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

  test("Es posible crear un Writer solo con la información de los logs, " +
    "para esto podemos usar la función `tell` que nos provee `cats.syntax.writter`") {
    import cats.data.Writer
    import cats.syntax.writer._

    val miWriter: Writer[Vector[String], Unit] = Vector("uno", "dos").tell

    assert( miWriter.written == Vector("uno", "dos"))

  }

  test("Concatenando Writer con `flatmap`") {

    import cats.data.Writer
    import cats.instances.vector._
    import cats.syntax.writer._
    import cats.Id
    import cats.data.WriterT

    def contadorPeticiones(ip: String ): Writer[Vector[String], Int] =
      Writer(Vector(s"Petición de la ip: ${ip}"), 1)

    val resultado: WriterT[Id, Vector[String], Int] = for {
      _ <- Vector("Inicia el proceso...").tell
      a <- contadorPeticiones("123")
      b <- contadorPeticiones("234")
      c <- contadorPeticiones("456")
      d <- contadorPeticiones("981")
      _ <- Vector("... Finaliza el proceso").tell
    } yield a + b + c + d


    assert( resultado.written == Vector(
      "Inicia el proceso...",
      "Petición de la ip: 123",
      "Petición de la ip: 234",
      "Petición de la ip: 456",
      "Petición de la ip: 981",
      "... Finaliza el proceso"
    ))

    assert( resultado.value == 4 )

  }

  test("Los logs se pueden transformar con `mapWritten`, aplicándoles alguna función. ") {
    import cats.syntax.writer._
    import cats.data.Writer
    import cats.instances.vector._
    import cats.Id
    import cats.data.WriterT

    val resultado: WriterT[Id, Vector[String], Int] = for {
      _ <- Vector("hola").tell
      _ <- Vector("Writer").tell
      _ <- Vector("Monad").tell
      a <- Writer(Vector("Desde acá"), 1 )
    } yield a

    val transformacion = resultado.mapWritten( mensajes => mensajes.map( mensaje => mensaje.toUpperCase ) )

    assert( transformacion.written == Vector(
      "HOLA",
      "WRITER",
      "MONAD",
      "DESDE ACÁ"
    ))
  }

  test("Transformando ambos lados del Writer con `bimap` y `mapBoth`") {
    import cats.data.WriterT
    import cats.Id
    import cats.syntax.writer._
    import cats.data.Writer
    import cats.instances.vector._

    val resultado: WriterT[Id, Vector[String], Int] = for {
      _ <- Vector("hola").tell
      _ <- Vector("Writer").tell
      _ <- Vector("Monad").tell
      a <- Writer(Vector("Desde acá"), 1 )
    } yield a

    // BIMAP
    val transformacionBimap = resultado.bimap(mensajes => mensajes.map(_.toUpperCase), valor => valor * 2)
    // MAPBOTH
    val transformacionmapBoth = resultado.mapBoth((mensajes, valor) =>(mensajes.map(_.toUpperCase), valor * 2) )

    val vectorEsperado = Vector(
      "HOLA",
      "WRITER",
      "MONAD",
      "DESDE ACÁ"
    )

    assert( transformacionBimap.written == vectorEsperado )
    assert( transformacionBimap.value == 2 )

    assert( transformacionmapBoth.written == vectorEsperado )
    assert( transformacionmapBoth.value == 2 )

  }

}
