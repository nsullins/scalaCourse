package org.functionalkoans.forscala
import org.scalatest.matchers.ShouldMatchers
import support.BlankValues._
import support.KoanSuite
import scala.collection._
import mutable.ListBuffer

abstract class RollingStock{
  val name: String
}

abstract class Car extends RollingStock{
  val carries: String
  def pulled(): String = {
    name + " carrying " + carries
  }
}

class PassengerCar extends Car{
  val name = "Passenger car"
  val carries = "people"
}

class CargoCar extends Car{
  val name = "Cargo car"
  val carries = "cargo"
}

abstract class Engine extends RollingStock{

  val cars: ListBuffer[Car] = ListBuffer[Car]()
  val maxCars: Int
  def pull: String = {
    name + " pulls " + cars.map(f => f.pulled).mkString(" and ")
  }
  def add(car: Car){
    if(cars.length < maxCars) cars.+=(car) else throw new IllegalStateException
  }
}

class SteamEngine extends Engine{
  val name = "Steam engine"
  val maxCars = 3
}

class DieselEngine extends Engine{
  val name = "Diesel engine"
  val maxCars = 6
}

class ShuntEngine extends Engine{
  val name = "Shunt engine"
  val maxCars = 10
  override def pull: String ={
    name + " doesn't pull, it pushes "  + cars.map(f => f.pulled).mkString(" and ")
  }
}

class Flight07 extends KoanSuite with ShouldMatchers {

  // Using the object model on the last slide of Flight 7, and the instructions below, create
  // a class heirarchy that satisfies the tests below when they are uncommented

  // abstract class RollingStock should have an abstract field name of type String

  // abstract class Car should subclass RollingStock and add a field carries of type String
  // and a method (with implementation) called pulled that returns a string formed of
  // name + " carrying " + carries

  // concrete class PassengerCar should subclass Car, set the name field to be "Passenger car" and
  // the carries field to "people"

  // concrete class CargoCar should subclass Car, set the name field "Cargo car" and
  // the carries field to "cargo"

  // now create an abstract Engine class that subclasses RollingStock and has the following:
  // a cars field with a mutable.ListBuffer of Cars
  // a method pull that returns a string consisting of name + " pulls " + the list of cars in the
  // ListBuffer using the pulled method to get the descriptive string of each car. The cars details should
  // be joined with " and ", and there should be no trailing " and " after the last car
  // an abstract maxCars field of type Int (that specifies the maximum number of cars that may
  // be added to the engine), and finally
  // an add method that takes a Car, and adds it to the ListBuffer *unless* that would exceed the
  // maximum number of Cars allowed in the maxCars field. If there are too many cars, throw an
  // IllegalStateException with a suitable message

  // Create a concrete class SteamEngine that subclasses Engine, sets the name field to "Steam Engine"
  // and the maxCars field to 3

  // Create a concrete class DieselEngine that subclasses Engine, sets the name field to "Diesel Engine"
  // and the maxCars field to 6

  // now uncomment the following tests (first comment block only) and run them to make sure they pass
   test ("2 passenger car steam train") {
    val steamEngine = new SteamEngine
    steamEngine.add(new PassengerCar)
    steamEngine.add(new PassengerCar)

    steamEngine.pull should be ("Steam engine pulls Passenger car carrying people and Passenger car carrying people")
  }

  test ("4 car steam train should throw exception") {
    val steamEngine = new SteamEngine
    steamEngine.add(new PassengerCar)
    steamEngine.add(new CargoCar)
    steamEngine.add(new PassengerCar)

    intercept[IllegalStateException] {
      steamEngine.add(new CargoCar)
    }

    steamEngine.pull should be ("Steam engine pulls Passenger car carrying people and " +
                                "Cargo car carrying cargo and Passenger car carrying people")
  }

  test ("5 car diesel train") {
    val dieselEngine = new DieselEngine
    dieselEngine.add(new CargoCar)
    dieselEngine.add(new PassengerCar)
    dieselEngine.add(new CargoCar)
    dieselEngine.add(new PassengerCar)
    dieselEngine.add(new CargoCar)

    dieselEngine.pull should be ("Diesel engine pulls Cargo car carrying cargo and " +
                                 "Passenger car carrying people and " +
                                 "Cargo car carrying cargo and " +
                                 "Passenger car carrying people and " +
                                 "Cargo car carrying cargo")
  }

  // now create a new class - ShuntEngine that overrides the pull method to print the string:
  // name + " doesn't pull, it pushes " and then the rest of the train description as before. It should
  // also provide the name as "Shunt engine" and the maxCars as 10 (shunt engines are strong :-) )
  // then uncomment the test below and make sure it passes

  test ("Shunt engine with 8 cars") {
    val shuntEngine = new ShuntEngine
    shuntEngine.add(new CargoCar)
    shuntEngine.add(new PassengerCar)
    shuntEngine.add(new CargoCar)
    shuntEngine.add(new PassengerCar)
    shuntEngine.add(new CargoCar)
    shuntEngine.add(new PassengerCar)
    shuntEngine.add(new CargoCar)
    shuntEngine.add(new PassengerCar)

    shuntEngine.pull should be ("Shunt engine doesn't pull, it pushes Cargo car carrying cargo and " +
                                 "Passenger car carrying people and " +
                                 "Cargo car carrying cargo and " +
                                 "Passenger car carrying people and " +
                                 "Cargo car carrying cargo and " +
                                 "Passenger car carrying people and " +
                                 "Cargo car carrying cargo and " +
                                 "Passenger car carrying people")  
  }

  // now, let's make the train creation a little more readable with some factory methods. Create
  // an object called Engine with factory methods to create the different engine types (call the methods
  // diesel, steam and shunt) and a Car object with passenger and cargo methods, then uncomment
  // the test below and make sure it passes.

  object Engine {

    def diesel: DieselEngine = new DieselEngine
    def steam: SteamEngine = new SteamEngine
    def shunt: ShuntEngine = new ShuntEngine
  }
  
  object Car {

  }

  /* test ("Train factory") {
    val steamEngine = Engine.steam
    steamEngine add Car.passenger    // crafty use of infix operator syntax to make it read nicely
    steamEngine add Car.passenger

    steamEngine.pull should be ("Steam engine pulls Passenger car carrying people and Passenger car carrying people")

    val dieselEngine = Engine.diesel
    dieselEngine add Car.cargo
    dieselEngine add Car.passenger
    dieselEngine add Car.cargo
    dieselEngine add Car.passenger
    dieselEngine add Car.cargo

    dieselEngine.pull should be ("Diesel engine pulls Cargo car carrying cargo and " +
                                 "Passenger car carrying people and " +
                                 "Cargo car carrying cargo and " +
                                 "Passenger car carrying people and " +
                                 "Cargo car carrying cargo")

    val shuntEngine = Engine.shunt
    shuntEngine add Car.cargo
    shuntEngine add Car.passenger
    shuntEngine add Car.cargo
    shuntEngine add Car.passenger
    shuntEngine add Car.cargo
    shuntEngine add Car.passenger
    shuntEngine add Car.cargo
    shuntEngine add Car.passenger
    
    shuntEngine.pull should be ("Shunt engine doesn't pull, it pushes Cargo car carrying cargo and " +
                                 "Passenger car carrying people and " +
                                 "Cargo car carrying cargo and " +
                                 "Passenger car carrying people and " +
                                 "Cargo car carrying cargo and " +
                                 "Passenger car carrying people and " +
                                 "Cargo car carrying cargo and " +
                                 "Passenger car carrying people")
  } */

  // extra credit - alter the add method in the Engine abstract class to return the engine instance
  // at the end of the method, so that the cars can be added in a chain like this:
  //
  // val steamTrain = Engine.steam add Car.cargo add Car.passenger add Car.cargo
  //
  // and write a test to make sure that it works, as expected, for all trains and carriages
  //
  // Why didn't that change break your existing tests?
  // You have effectively created a simple DSL for creating trains, how do you like that?

  /* test ("Chained train creation") {
    val steamEngine = Engine.steam add Car.cargo add Car.passenger add Car.passenger

    steamEngine.pull should be ("Steam engine pulls Cargo car carrying cargo and Passenger car carrying people and Passenger car carrying people")
    
  } */

  // extra extra credit: there may be some repetition in your ShuntEngine implementation where it lists
  // out the cars in the overridden pull method. Refactor out the car string resolution into a separate
  // method and use that in both the Engine pull method and the overridden ShuntEngine pull method to
  // reduce the duplication. Make sure all of the tests still pass.
  //
  // If you already did this without being prompted, you get a gold star for being a smart-ass :-)
}
