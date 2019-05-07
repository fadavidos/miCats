package com.miCats.datatypes

import cats.data.Validated
import org.scalatest.FunSuite

class Validation extends FunSuite {

  test("") {
    import cats.data.{ EitherT, NonEmptyList, ValidatedNel }
    import cats.implicits._

    val x: EitherT[Option, String, Int] = EitherT.right(Some(2))

    val either: Option[ValidatedNel[String, Int]] = x.toValidatedNel
  }

}
