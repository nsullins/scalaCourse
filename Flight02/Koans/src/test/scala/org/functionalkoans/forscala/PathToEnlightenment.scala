package org.functionalkoans.forscala
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith

@RunWith(classOf[JUnitRunner])
class PathToEnlightenmentTest extends FunSuite {
  override def nestedSuites = List(new Flight02)
}
