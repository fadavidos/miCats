package com.miCats

import org.scalatest.AsyncFunSuite

class ApplicativeTest extends AsyncFunSuite {

  /*
    Applicative Functors o más fácil Applicative: Applicative[F[_]]

    * Extiende de Functor, pero a diferencia de éste, es capaz de trabajar con multiples
       efectos independientes y posteriormente componerlos.

    * Tiene una función `pure`, que permite embolver un dato dentro de un type constructor =>  def pure[A](x: A): F[A]

   */

  test("Podemos crear un Applicative de Option dando simplemente " +
    "el valor interno que tendrá el Option") {
    import cats.Applicative
    import cats.implicits._

    val valorOpcional: Option[Int] = Applicative[Option].pure(1)

    assertResult( Some(1 )) {
      valorOpcional
    }
  }

  test("Los Applicatives también se pueden componer... ") {
    import cats.Applicative
    import cats.implicits._

    import scala.concurrent.Future

    type PosiblesValoresOpcionales[A] = Future[List[Option[A]]]

    implicit val composeApplicatives = Applicative[Future] compose Applicative[List] compose Applicative[Option]

    val primerValor: PosiblesValoresOpcionales[Int] = Future(List(None, Some(10)))
    val segundoValor: PosiblesValoresOpcionales[Int] = Future(List(Some(20), None))
    val tercerValor: PosiblesValoresOpcionales[Int] = Future(List(Some(5), Some(5)))

    val resultado: PosiblesValoresOpcionales[Int] = Applicative[PosiblesValoresOpcionales](composeApplicatives)
      .map3( primerValor, segundoValor, tercerValor )((diez, veinte, cinco) => diez + veinte + cinco)

    // TODO: Por qué da este resutlado ???
    resultado.map( algo =>
      assert(algo == List(None, None, None, None, Some(35), Some(35), None, None))
    )
  }

  test("Los applicative son capaz de trabajar con multiples efectos. " +
    "el for-comprehension es fail fast, lo que quiere decir que al 'fallar' uno de las instrucciones " +
    "que están dentro del for, harán que las siguientes instrucciones no se ejecuten. Esto es un " +
    "problema cuando queremos acumular todos los errores que están dentro del for." +
    "Con los Applicatives podemos hacer acumulación de errores. ") {

    import cats.data.Validated.{Invalid, Valid, _}
    import cats.data._
    import cats.implicits._

    // MODELO
    case class Usuario(nombre: String, edad: Int, email: String, genero: Char)

    // Alias para Validated[NonEmptyChain[E], A]
    type DatoValidado[A] = ValidatedNec[ErrorNegocio, A]

    sealed trait ErrorNegocio {
      def mensajeDeError: String
    }

    case object MenorEdad extends ErrorNegocio {
      override def mensajeDeError: String = "Es menor de edad"
    }

    case object EmailInvalido extends ErrorNegocio {
      override def mensajeDeError: String = "El correo no es correcto"
    }

    case object GeneroInexistente extends ErrorNegocio {
      override def mensajeDeError: String = "El negero no existe"
    }

    // VALIDACIONES DE NEGOCIO.
    def validarMayoriaEdad(edad: Int): DatoValidado[Int] = {
      if ( edad >= 18 )
      edad.validNec
      else
      MenorEdad.invalidNec
    }

    def validarEmail(email: String): DatoValidado[String] = {
      if( email.contains("@"))
      email.validNec
      else
      EmailInvalido.invalidNec
    }

    def validarGenero(char: Char): DatoValidado[Char] = {
      if(char.equals('M') || char.equals('F'))
      char.validNec
      else
      GeneroInexistente.invalidNec
    }

    // OBJETO A VALIDAR.
    val miUsarioConErrores = Usuario("Juan", 10, "invalido",'M')

    /*
    Con ayuda del `mapN` podemos hacer multiples validaciones y
    acumular los errores.
     */
    val validacionFallida = (
      validarEmail(miUsarioConErrores.email),
      validarMayoriaEdad(miUsarioConErrores.edad),
      validarGenero(miUsarioConErrores.genero)
    ).mapN((mail, edad, genero) => {
      Usuario(miUsarioConErrores.nombre, edad, mail, genero)
    } )

    /*
    Las validaciones de Mayoría de edad y Email valido fallaron,
    ya que no cumplía con las condiciones, pero se logran obtener
    todos los errores en una sola validación.
     */
    validacionFallida match {
      case Valid( _ ) => assert(false)
      case Invalid( errores ) => {
        assert( errores.toList.contains(MenorEdad) )
        assert( errores.toList.contains(EmailInvalido))
      }
    }

    // OBJETO A VALIDAR.
    val miUsarioCorrecto = Usuario("Juan", 19, "correo@gmail.com",'M')

    /*
    Con ayuda del `mapN` podemos hacer multiples validaciones y
    acumular los errores.
     */
    val validacionExitosa = (
      validarEmail(miUsarioCorrecto.email),
      validarMayoriaEdad(miUsarioCorrecto.edad),
      validarGenero(miUsarioCorrecto.genero)
    ).mapN((mail, edad, genero) => {
      Usuario(miUsarioCorrecto.nombre, edad, mail, genero)
    } )

    /*
    Todas las validaciones son exitosas, así que podemos obtener
    una respuesta positiva.
     */
    validacionExitosa match {
      case Valid( usuarioValidado ) => assert(usuarioValidado.edad == 19 )
      case Invalid( _ ) => assert( false )
    }
  }

}
