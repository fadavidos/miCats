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
    case class Persona(nombre: String, apellidos: String)

    /*
    Hacemos la instancia del Type Class para los Int y para Persona.
    Agregamos nueva funcionalidad sin necesidad a la función de imprimir, sin modificar
    el código ya existente hasta este punto.
     */
    object ImpresoraInstancias {
        implicit val imprimirNumeros: Impresora[Int] = new Impresora[Int] {
          override def imprimir(elemento: Int): String = s"El número es: ${elemento} "
        }
      implicit val imprimirPersona: Impresora[Persona] = new Impresora[Persona] {
        override def imprimir(elemento: Persona): String = s"La persona se llama: ${elemento.nombre} ${elemento.apellidos} "
      }
    }

    /*
    Se define un objeto que hace las veces de interfaz
     */
    object Impresora {
      def imprimir[A](elemento: A)(implicit imp: Impresora[A]): String = {
        imp.imprimir(elemento)
      }
      def imprimirMuchos[A](elemnto: List[A])(implicit imp: Impresora[A]): String = {
        elemnto.map(imp.imprimir).mkString
      }
    }

    // Los objetos que van a ser imprimidos
    val numero: Int = 9
    val listaNumeros: List[Int] = List(1,2,3)
    val persona: Persona = Persona("Juan", "Pérez")
    val listaPersonas: List[Persona] = List(Persona("Juan","Pérez"), Persona("Ana","Álvarez"))


    /*
     Se llama la función imprimir de Int y Persona, únicamente pasándole implicitamente (para este test es explicitamente)
     la instancia de cómo imprimir Int o imprimir Persona.
      */
    assert( Impresora.imprimir(numero)(ImpresoraInstancias.imprimirNumeros) == "El número es: 9 ")
    assert( Impresora.imprimir(persona)(ImpresoraInstancias.imprimirPersona) == "La persona se llama: Juan Pérez ")

    /*
    Adicionalmente podemos llamar otras funciones que usen el imprimir internamente.
     */
    assert( Impresora.imprimirMuchos(listaNumeros)(ImpresoraInstancias.imprimirNumeros) == "El número es: 1 El número es: 2 El número es: 3 " )
    assert( Impresora.imprimirMuchos(listaPersonas)(ImpresoraInstancias.imprimirPersona) == "La persona se llama: Juan Pérez La persona se llama: Ana Álvarez " )

  }

}
