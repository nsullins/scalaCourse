package org.functionalkoans.forscala
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.prop.Checkers
import support.BlankValues._
import org.scalatest.Spec

class Flight11BehaviorDrivenDesign extends Spec with ShouldMatchers with Checkers {

  class Fraction(n: Int, d: Int) {

    require(d != 0, "denominator cannot be zero")

    val numer = if (d < 0) -1 * n else n
    val denom = d.abs

    override def toString = numer + " / " + denom
  }

  // Please replace each pending test with a body that
  // actually verifies the Fraction class behaves as specified
  describe("A Fraction") {
    describe("when a zero denominator is passed") {
      it("should throw IllegalArgumentException") {
        intercept[IllegalArgumentException]{
          new Fraction(0, 0)
        }
      }
    }
    describe("when a positive denominator is passed") {
      it("should leave the numerator at the same sign") (pending)
      it("should leave the denominator positive") (pending)
    }
    describe("a negative denominator is passed") {
      it("change the sign of the numerator") (pending)
      it("change the sign of the denominator") (pending)
    }
  }

  // Now write a property based test of the same Fraction class
  // Replace (pending) with a call to check, passing in the
  // property function.
//  describe("That same Fraction") {
//    it("the denominator should always be normalized to positive") {
//      check((a: Int, b: Int) =>
//        if (b == 0) true else false
//      )
//    }
//
//  }
}