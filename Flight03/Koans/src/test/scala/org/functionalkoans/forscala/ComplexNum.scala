package org.functionalkoans.forscala

/**
 * User: nsullins
 * Date: 1/19/11
 * Time: 1:13 PM
 */

class ComplexNum(val real: Double, val imaginary: Double){


  def this(r: Double) = this(r, 0)
  override def toString = real.toString + (if(imaginary >= 0) " + " else " - ") + imaginary.abs.toString + "i"
  def +(that: ComplexNum) = new ComplexNum(that.real + this.real, that.imaginary + this.imaginary)
  def +(d: Double) = new ComplexNum(d + this.real, this.imaginary)
}

object ComplexNum{

  implicit def doubleToComplex(i: Double): ComplexNum = new ComplexNum(i, 0)

}
