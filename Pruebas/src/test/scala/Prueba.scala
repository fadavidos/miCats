
import org.scalatest.FunSuite

class Prueba extends FunSuite {

  test("Tema: F-Bounded Types" +
    "Url: http://tpolecat.github.io/2015/04/29/f-bounds.html") {

    trait Pet[A <: Pet[A]] { this: A => // self-type
      def name: String
      def renamed(newName: String): A // note this return type
    }

    case class Fish(name: String, age: Int) extends Pet[Fish] { // note the type argument
      def renamed(newName: String) = copy(name = newName)
    }

    case class Kitty(name: String, color: Int) extends Pet[Kitty] { // oops
      def renamed(newName: String): Kitty = new Kitty( newName, 42 )
    }

    class Mammal(val name: String) extends Pet[Mammal] {
      def renamed(newName: String) = new Mammal(newName)
    }

    class Monkey(name: String) extends Mammal(name)

    def esquire[A <: Pet[A]](a: A): A = a.renamed(a.name + ", Esq.")

    val a = Fish("Jimmy", 2)
    println(s"Fish es: ${a}")
    val b = a.renamed("Bob")
    println(s"Fish es: ${b}")
    val funcion = esquire( b )
    println(s"Fish es: ${funcion}")
    val p = Kitty("cat", 2)
    println(s"Kitty es: ${p}")
    val q = p.renamed( "hola" )
    println(s"Kitty es: ${q}")
    val mo = new Monkey( "Monkey" )
    println(s"Monkey es: ${mo}")
    val omo = mo.renamed( "otro" )
    println(s"Monkey es: ${omo}")

  }

  test( "Typeclass and Traits" +
    "Url: http://tpolecat.gi  thub.io/2015/04/29/f-bounds.html" ) {
    trait Pet {
      def name: String
    }

    trait Rename[A] {
      def rename( a: A, newName: String ): A
    }

    case class Fish( name: String, age: Int ) extends Pet

    case class Kitty( name: String, age: Int ) extends Pet

    object Fish {
      implicit val fishRename =  new Rename[Fish] {
        override def rename(a: Fish, newName: String): Fish = a.copy( name = newName)
      }
    }

    object Kitty {
      implicit val kittyRename = new Rename[Kitty] {
        override def rename(a: Kitty, newName: String): Kitty = a.copy( name = newName)
      }
    }

    implicit class RenameOps[A]( a: A )( implicit ev: Rename[A] ) {
      def renamed( newName: String ) = ev.rename( a, newName )
    }

    val f = Fish("Jimmy", 2)
    println(s"Fish es: ${f}")
    val bf = f.renamed( "Bob" )
    println(s"Fish es: ${bf}")

    val k = Kitty("Jimmy", 2)
    println(s"Kitty es: ${k}")
    val bk = k.renamed( "Bob" )
    println(s"Kitty es: ${bk}")

  }


  test(" Typeclass " +
    "Url: http://tpolecat.github.io/2015/04/29/f-bounds.html") {

    trait Pet[A] {
      def name(a: A): String
      def renamed(a: A, newName: String): A
    }

    implicit class PetOps[A](a: A)(implicit ev: Pet[A]) {
      def name = ev.name(a)
      def renamed(newName: String): A = ev.renamed(a, newName)
    }

    case class Fish(name: String, age: Int)

    object Fish {
      implicit val FishPet = new Pet[Fish] {
        def name(a: Fish) = a.name
        def renamed(a: Fish, newName: String) = a.copy(name = newName)
      }
    }

    val nuevo = Fish("Bob", 42).renamed("Steve")
    println( nuevo )

  }

  test( "Associativity" ) {

    import cats.instances.all._
    import cats.syntax.semigroup._


    val list = List(1, 2, 3, 4, 5)
    val (left, right) = list.splitAt(2)
    println(left)
    println(right)

    val sumLeft = left.foldLeft(0)(_ |+| _)
    println(sumLeft)
    // sumLeft: Int = 3

    val sumRight = right.foldLeft(0)(_ |+| _)
    // sumRight: Int = 12

    val result = sumLeft |+| sumRight
    // result: Int = 15

  }

  test( "" ) {
    val op: Option[String] = ???
    op.map( v => v + "" )

  }

































}
