package sudoku
import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import Sudoku._
import org.scalatest.Suite
import org.scalatest.AbstractSuite
import org.scalatest.StackDepthException

trait TruncatedStackTraces extends AbstractSuite { this: Suite =>

  abstract override def withFixture(test: NoArgTest) {
    try {
      super.withFixture(test)
    }
    catch {
      case e: StackDepthException =>
        val truncated = e.getStackTrace.drop(e.failedCodeStackDepth)
        e.setStackTrace(truncated)
        throw e
    }
  }
}

class SudokuSuite extends FunSuite with TruncatedStackTraces {

  val puzzle1 =
    new Puzzle(makeBoard(List(
      0, 0, 5,  1, 0, 0,  9, 0, 8,
      0, 0, 3,  0, 0, 8,  0, 4, 0,
      9, 0, 2,  0, 7, 0,  0, 6, 0,

      0, 4, 0,  6, 0, 0,  5, 0, 1,
      0, 0, 6,  0, 0, 0,  2, 0, 0,
      8, 0, 1,  0, 0, 9,  0, 3, 0,

      0, 9, 0,  0, 1, 0,  8, 0, 7,
      0, 1, 0,  5, 0, 0,  4, 0, 0,
      2, 0, 7,  0, 0, 4,  3, 0, 0
    )))

  val puzzle1Str =
    """
      |  _______________ _______________ ______________ 
      | |               |               |              |
      | |           5.  |   1.          |   9.      8. |
      | |           3.  |           8.  |       4.     |
      | |   9.      2.  |       7.      |       6.     |
      | |_______________|_______________|______________|
      | |               |               |              |
      | |       4.      |   6.          |   5.      1. |
      | |           6.  |               |   2.         |
      | |   8.      1.  |           9.  |       3.     |
      | |_______________|_______________|______________|
      | |               |               |              |
      | |       9.      |       1.      |   8.      7. |
      | |       1.      |   5.          |   4.         |
      | |   2.      7.  |           4.  |   3.         |
      | |_______________|_______________|______________|
      |""".stripMargin


  val puzzle1aStr =
    """
      |  _______________ _______________ ______________ 
      | |               |               |              |
      | |   4       5.  |   1.          |   9.      8. |
      | |           3.  |           8.  |       4.     |
      | |   9.      2.  |       7.      |       6.     |
      | |_______________|_______________|______________|
      | |               |               |              |
      | |       4.      |   6.          |   5.      1. |
      | |           6.  |               |   2.         |
      | |   8.      1.  |           9.  |       3.     |
      | |_______________|_______________|______________|
      | |               |               |              |
      | |       9.      |       1.      |   8.      7. |
      | |       1.      |   5.          |   4.         |
      | |   2.      7.  |           4.  |   3.         |
      | |_______________|_______________|______________|
      |""".stripMargin

  val puzzle1bStr =
    """
      |  _______________ _______________ ______________ 
      | |               |               |              |
      | |           5.  |   1.          |   9.      8. |
      | |           3.  |           8.  |       4.     |
      | |   9.      2.  |       7.      |       6.     |
      | |_______________|_______________|______________|
      | |               |               |              |
      | |       4.      |   6.          |   5.      1. |
      | |           6.  |               |   2.         |
      | |   8.      1.  |           9.  |       3.     |
      | |_______________|_______________|______________|
      | |               |               |              |
      | |       9.      |       1.      |   8.      7. |
      | |       1.      |   5.          |   4.         |
      | |   2.      7.  |           4.  |   3.      2  |
      | |_______________|_______________|______________|
      |""".stripMargin

  val puzzle1cStr =
    """
      |  _______________ _______________ ______________ 
      | |               |               |              |
      | |           5.  |   1.          |   9.      8. |
      | |           3.  |           8.  |       4.     |
      | |   9.      2.  |       7.      |       6.     |
      | |_______________|_______________|______________|
      | |               |               |              |
      | |       4.      |   6.          |   5.      1. |
      | |           6.  |               |   2.         |
      | |   8.      1.  |           9.  |   4   3.     |
      | |_______________|_______________|______________|
      | |               |               |              |
      | |       9.      |       1.      |   8.      7. |
      | |       1.      |   5.          |   4.         |
      | |   2.      7.  |           4.  |   3.         |
      | |_______________|_______________|______________|
      |""".stripMargin

  val puzzle1dStr =
    """
      |  _______________ _______________ ______________ 
      | |               |               |              |
      | |           5.  |   1.          |   9.      8. |
      | |           3.  |           8.  |       4.     |
      | |   9.      2.  |       7.      |       6.     |
      | |_______________|_______________|______________|
      | |               |               |              |
      | |       4.      |   6.          |   5.      1. |
      | |           6.  |               |   2.         |
      | |   8.      1.  |           9.  |       3.     |
      | |_______________|_______________|______________|
      | |               |               |              |
      | |       9.      |       1.  5   |   8.      7. |
      | |       1.      |   5.          |   4.         |
      | |   2.      7.  |           4.  |   3.         |
      | |_______________|_______________|______________|
      |""".stripMargin

  val puzzle2 =
    new Puzzle(makeBoard(List(
      4, 7, 5,  1, 3, 6,  9, 2, 8,
      1, 6, 3,  2, 9, 8,  7, 4, 5,
      9, 8, 2,  4, 7, 5,  1, 6, 3,

      7, 4, 9,  6, 2, 3,  5, 8, 1,
      5, 3, 6,  8, 4, 1,  2, 7, 9,
      8, 2, 1,  7, 5, 9,  6, 3, 4,

      6, 9, 4,  3, 1, 2,  8, 5, 7,
      3, 1, 8,  5, 6, 7,  4, 9, 2,
      2, 5, 7,  9, 8, 4,  3, 1, 0
    )))

  val puzzle2aStr =
    """
      |  _______________ _______________ ______________ 
      | |               |               |              |
      | |   4.  7.  5.  |   1.  3.  6.  |   9.  2.  8. |
      | |   1.  6.  3.  |   2.  9.  8.  |   7.  4.  5. |
      | |   9.  8.  2.  |   4.  7.  5.  |   1.  6.  3. |
      | |_______________|_______________|______________|
      | |               |               |              |
      | |   7.  4.  9.  |   6.  2.  3.  |   5.  8.  1. |
      | |   5.  3.  6.  |   8.  4.  1.  |   2.  7.  9. |
      | |   8.  2.  1.  |   7.  5.  9.  |   6.  3.  4. |
      | |_______________|_______________|______________|
      | |               |               |              |
      | |   6.  9.  4.  |   3.  1.  2.  |   8.  5.  7. |
      | |   3.  1.  8.  |   5.  6.  7.  |   4.  9.  2. |
      | |   2.  5.  7.  |   9.  8.  4.  |   3.  1.  6  |
      | |_______________|_______________|______________|
      |""".stripMargin

  val puzzle2bStr =
    """
      |  _______________ _______________ ______________ 
      | |               |               |              |
      | |   4.  7.  5.  |   1.  3.  6.  |   9.  2.  8. |
      | |   1.  6.  3.  |   2.  9.  8.  |   7.  4.  5. |
      | |   9.  8.  2.  |   4.  7.  5.  |   1.  6.  3. |
      | |_______________|_______________|______________|
      | |               |               |              |
      | |   7.  4.  9.  |   6.  2.  3.  |   5.  8.  1. |
      | |   5.  3.  6.  |   8.  4.  1.  |   2.  7.  9. |
      | |   8.  2.  1.  |   7.  5.  9.  |   6.  3.  4. |
      | |_______________|_______________|______________|
      | |               |               |              |
      | |   6.  9.  4.  |   3.  1.  2.  |   8.  5.  7. |
      | |   3.  1.  8.  |   5.  6.  7.  |   4.  9.  2. |
      | |   2.  5.  7.  |   9.  8.  4.  |   3.  1.  1  |
      | |_______________|_______________|______________|
      |""".stripMargin

  // Tests toString
  test("Verify toString of puzzle created from canned values.") {
    assert(puzzle1Str === puzzle1.toString)
  }

  // Tests update
  test("Verify update modifies proper cell.") {
    val puzzle1a = puzzle1.update(0, 0, Some(4))
    assert(puzzle1aStr === puzzle1a.toString)
  }

  // Tests update
  test("Verify update modifies proper cell in puzzle1b") {
    val puzzle1b = puzzle1.update(8, 8, Some(2))
    assert(puzzle1bStr === puzzle1b.toString)
  }

  // Tests update
  test("Verify update modifies proper cell in puzzle1c.") {
    val puzzle1c = puzzle1.update(5, 6, Some(4))
    assert(puzzle1cStr === puzzle1c.toString)
  }

  // Tests update
  test("Verify update modifies proper cell in puzzle1d.") {
    val puzzle1d = puzzle1.update(6, 5, Some(5))
    assert(puzzle1dStr === puzzle1d.toString)
  }

  // Tests update
  test("Verify update modifies proper cell in puzzle2a.") {
    val puzzle2a = puzzle2.update(8, 8, Some(6))
    assert(puzzle2aStr === puzzle2a.toString)
  }

  // Tests update
  test("Verify update modifies proper cell in puzzle2b.") {
    val puzzle2b = puzzle2.update(8, 8, Some(1))
    assert(puzzle2bStr === puzzle2b.toString)
  }

  // Tests update
  test("Verify an illegal update throws an exception.") {
    // no problem example
    puzzle1.update(1, 1, Some(1))

    // row number out of bounds
    intercept[IllegalArgumentException] {
      puzzle1.update(9, 1, Some(1))
    }

    // column number out of bounds
    intercept[IllegalArgumentException] {
      puzzle1.update(1, 9, Some(1))
    }

    // value out of bounds
    intercept[IllegalArgumentException] {
      puzzle1.update(1, 1, Some(10))
    }

    // cell is fixed
    intercept[IllegalArgumentException] {
      puzzle1.update(0, 2, Some(1))
    }
  }

  // Tests isScrewedUp
  ignore("Verify isScrewedUp returns false for canned puzzle with valid values.") {
    assert(!puzzle1.isScrewedUp)
  }

  // Tests isScrewedUp
  ignore("Verify isScrewedUp returns false for updated puzzle with valid values.") {
    val puzzle1a = puzzle1.update(0, 0, Some(4))
    assert(!puzzle1a.isScrewedUp)
  }

  // Tests isScrewedUp
  ignore("Verify isScrewedUp returns true when there is a duplicate " +
       "value in same row.") {
    val puzzle1b = puzzle1.update(8, 8, Some(2))
    assert(puzzle1b.isScrewedUp)
  }

  // Tests isScrewedUp
  ignore("Verify isScrewedUp returns true when there is a duplicate value in same column.") {
    val puzzle1c = puzzle1.update(5, 6, Some(4))
    assert(puzzle1c.isScrewedUp)
  }

  // Tests isScrewedUp
  ignore("Verify isScrewedUp returns true when there is a duplicate " +
      "value in same column in puzzle1d.") {
    val puzzle1d = puzzle1.update(6, 5, Some(5))
    assert(puzzle1d.isScrewedUp)
  }

  // Tests isScrewedUp
  ignore("Verify isWon returns false for an incomplete puzzle.") {
    assert(!puzzle2.isWon)
  }

  // Tests isWon, which uses isScrewedUp
  ignore("Verify isWon returns true for a solved puzzle.") {
    val puzzle2a = puzzle2.update(8, 8, Some(6))
    assert(puzzle2aStr === puzzle2a.toString)
    assert(puzzle2a.isWon)
  }

  // Tests isWon, which uses isScrewedUp
  ignore("Verify isWon returns false for a complete but incorrect puzzle.") {
    val puzzle2b = puzzle2.update(8, 8, Some(1))
    assert(puzzle2bStr === puzzle2b.toString)
    assert(!puzzle2b.isWon)
  }
}
