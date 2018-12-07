package com.miCats.Functor

import org.scalatest.FunSuite

class TypeClassTest extends FunSuite{

  /*
   Qué es un Type Class ?
    * Los Type Class nos permiten agregar nuevas funcionalidades a librerías ya existentes. Sin usar la herencia tradicional.
    * En Cats, una Type Class se representa por un Trait y al menos un parámetro.
   */
  test("Crean un mi primer Type Class") {


    // Este es el type class
    trait Impresora[A] {
      def imprimir(elemento: A): String
    }

    // Un case class cualquiera, de dos parámetros
    final case class Persona(nombre: String, apellidos: String)

    /*
    Hacemos la instancia del Type Class para los Int y para Persona.
    Agregamos nueva funcionalidad sin necesidad a la función de imprimir, sin modificar
    el código ya existente hasta este punto.
     */
    object ImpresoraInstancias {
        implicit val imprimirNumeros: Impresora[Int] = new Impresora[Int] {
          override def imprimir(elemento: Int): String = s"El número es: ${elemento}"
        }
      implicit val imprimirPersona: Impresora[Persona] = new Impresora[Persona] {
        override def imprimir(elemento: Persona): String = s"La persona se llama: ${elemento.nombre} ${elemento.apellidos}"
      }
    }

    /*
    Se define un objeto que hace las veces de interfaz
     */
    object Impresora {
      def imprimir[A](elemento: A)(implicit imp: Impresora[A]): String = {
        imp.imprimir(elemento)
      }
    }

    // Los objetos que van a ser imprimidos
    val numero: Int = 9
    val persona: Persona = Persona("Juan", "Pérez")

    /*
     Se llama la función imprimir de Int y Persona, únicamente pasándole implicitamente (para este test es explicitamente)
     la instancia de cómo imprimir Int o imprimir Persona.
      */
    assert( Impresora.imprimir(numero)(ImpresoraInstancias.imprimirNumeros) == "El número es: 9")
    assert( Impresora.imprimir(persona)(ImpresoraInstancias.imprimirPersona) == "La persona se llama: Juan Pérez")

  }

  test("Crean un mi primer Type Class con mejor sintaxis") {

    // Este es el type class
    trait Impresora[A] {
      def imprimir(elemento: A): String
    }

    // Un case class cualquiera, de dos parámetros
    final case class Persona(nombre: String, apellidos: String)

    /*
    Hacemos la instancia del Type Class para los Int y para Persona.
    Agregamos nueva funcionalidad sin necesidad a la función de imprimir, sin modificar
    el código ya existente hasta este punto.
     */
    object ImpresoraInstancias {
      implicit val imprimirNumeros: Impresora[Int] = new Impresora[Int] {
        override def imprimir(elemento: Int): String = s"El número es: ${elemento}"
      }
      implicit val imprimirPersona: Impresora[Persona] = new Impresora[Persona] {
        override def imprimir(elemento: Persona): String = s"La persona se llama: ${elemento.nombre} ${elemento.apellidos}"
      }
    }

    /*
    Se define un objeto que hace las veces de interfaz
     */
    object ImprimirCosas {
      implicit class ImprimirNumero[A](elemento: A) {
        def imprimir(implicit imp: Impresora[A]): String = {
          imp.imprimir(elemento)
        }
      }
    }

    // Los objetos que van a ser imprimidos
    val numero: Int = 9
    val persona: Persona = Persona("Juan", "Pérez")


    import ImprimirCosas._
    import ImpresoraInstancias._
    /*
     Se llama la función imprimir de Int y Persona, e importando los implicitos. Es más natural llamar la función imprimir
     del objeto `numero` y `persona`
      */
    assert( numero.imprimir  == "El número es: 9")
    assert( persona.imprimir == "La persona se llama: Juan Pérez")

  }

}
