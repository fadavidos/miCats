package com.miCats.datatypes

import org.scalatest.FunSuite


class NestedTest extends FunSuite {

  test("Nested permite componer sobre dos maps") {

    import cats.data._
    import cats.implicits._

    val list = List(Some(1), Some(2), None, Some(4))

    val nested: Nested[List, Option, Int] = Nested(list)

    println(nested.map( valor => valor * 2 ))



  }

}
