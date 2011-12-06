package org.functionalkoans.forscala
import org.scalatest.Spec
import org.scalatest.matchers.ShouldMatchers
import scala.actors.Actor
import scala.actors.Actor.self
import support.BlankValues._
import scala.collection._


// First we are going to create a Logger actor that takes a String message
// and adds it to a running list of log messages - starting out easy
// also make it clear the log on receipt of the Reset case class below

case object Reset

object Logger extends Actor {
  private[this] var log = List[String]()
  def logEntries = log

  // you need to add the act method here to handle the messages
  def act() {
    while(true){
      receive{
        case msg: String => log = msg :: log
        case Reset => log = List()
      }

    }
  }
}


class LoggerSpec extends Spec with ShouldMatchers {
  describe("Logger") {
    // start the logger - if you leave this out, it won't work
    Logger.start()

    it("should log messages passed in as Strings") {
      Logger !"Hello, world!"
      Logger ! "It appears to work"
    }

    it("should contain the messages logged so far") {
      Logger.logEntries should contain ("Hello, world!")
      Logger.logEntries should contain ("It appears to work")
    }

    it("should not contain other stuff") {
      Logger.logEntries.size should be (2)
      Logger.logEntries.contains("Random crap") should be (false)
    }

    it("should reset when requested") {
      Logger ! Reset
      Thread.sleep(20)  // let things settle
      Logger.logEntries should be ('empty)
    }
  }
}

// Now, let's have some fun

// Below are some tests for a Trader actor, that trades something specified in the constructor and has a
// quantity also specified in the constructor, you can buy and, sell a certain amount, but the buy
// will only be successful if there is a sufficient quantity (no short-selling on our trader).
// Uncomment the tests, and provide the case classes and trader actor to satisfy the tests below. Each trade
// should be logged with the Logger actor so we can check everything afterwards.
// Use a loop/react in the trader for now, and you can get the right format for the log entries in the specs
// (item + " " + Case class standard toString should be fine - you just need to log the errors in trading)

case class Buy(quantity: Int)
case class Sell(quantity: Int)
case object Quantity

class Trader(val item: String, q: Int) extends Actor {
  private[this] var quant = q
  def quantity = quant

  // you need to define the act method here to handle the buys and sells, and other stuff
  def act() {
    loop{
      react{
        case Buy(q) if this.quant >= q => {
          quant = quant - q
          Logger ! "%s Buy(%d)".format(item,q)
        }
        case Buy(q) if this.quant < q => {
          Logger ! "Insufficient %s to sell".format(item)
        }
        case Sell(q) => {
          quant = quant + q
          Logger ! "%s Sell(%d)".format(item,q)
        }
        case (Quantity, x: Actor) => x ! quantity
      }
    }
  }
}

class TraderSpec extends Spec with ShouldMatchers {
  describe("Trader") {
    val beanTrader = new Trader("Beans", 100)
    val porkBelliesTrader = new Trader("Pork Bellies", 100)

    it("should allow multiple traders to be run at the same time") {
      beanTrader.start()
      porkBelliesTrader.start()
    }

    it("should log all trades") {
      Logger ! Reset
      beanTrader ! Buy(20)
      porkBelliesTrader ! Buy(30)
      porkBelliesTrader ! Sell(20)

      Thread.sleep(20)  // let things settle

      Logger.logEntries should contain ("Beans Buy(20)")``                           `
      Logger.logEntries should contain ("Pork Bellies Buy(30)")
      Logger.logEntries should contain ("Pork Bellies Sell(20)")
    }

    it ("should keep a track of the item quantities") {
      beanTrader.quantity should be (80)
      porkBelliesTrader.quantity should be (90)
    }

    it ("should not sell more than it has") {
      Logger ! Reset
      beanTrader ! Buy(200)

      Thread.sleep(20)  // let things settle

      Logger.logEntries should contain ("Insufficient Beans to sell")

      // Quantities should be unaffected
      beanTrader.quantity should be (80)
      porkBelliesTrader.quantity should be (90)
    }

    // Instead of getting the quantity directly, which is not very actor-like, to prevent our actor being thrown
    // out of the actors guild, use the Quantity case object in a tuple with self to respond to a Quantity
    // request, so that the test below passes. Note how we request the result to the query
    it("should answer the quantity when requested") {
      beanTrader ! (Quantity, self)
      // we have to wait on the reply, but since it is not implemented to start with, we don't wait forever
      val quantity = self.receiveWithin(100) { case x => x }

      quantity should be (80)
    }
  }

  // Extra credit
  // Alter the logger so that instead of getting the log entries direct from the object it also uses an actor
  // case to request and receive the log. Add the request case class, and the case handler to the logger

  // Even more credit
  // We are using loop/react based actors for this, but we could use thread/receive based ones instead. If
  // you have time, convert the actors to use threads and receive instead, the tests should all pass unaltered.
  // How hard was that?

  // Super-mega-credit - doubtful that you will have time for this, but perhaps you will treat it as an
  // off-line exercise. The previous flight had a banking example, which used pattern-matching throughout. This
  // makes it ready (easy even) to adapt to use actors. You can change the implementation of the Account object
  // to be an actor, and adapt the tests to use message passing instead of direct calls to the methods.

  //could be some weirdness with deadlocking mac you will have to kill java

}
