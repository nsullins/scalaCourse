package sudoku
////
// A sudoku puzzle.
//
// Implemented as a two-dimensional list of cells, with each cell
// representing an element of the puzzle.  Each cell contains an
// Option containing the cell's numeric content (None if blank),
// and a Boolean which is true if the number is fixed, i.e. if
// it's part of the puzzle definition and can't be modified.

import List.flatten

case class Cell(value: Option[Int], isFixed: Boolean) {
  require(!value.isDefined || (value.get >= 1 && value.get <= 9))

  def isBlank = !value.isDefined
}

object Sudoku {

  type Row = List[Cell]
  type Board = List[Row]

  class Puzzle(board: Board) {

    //
    // Prints a dot next to fixed values to distinguish them from
    // modifiable ones.
    //
    override def toString: String = {

      def toStringMain =
        "\n"                                                  +
        "  _______________ _______________ ______________ \n" +
        " |               |               |              |\n" +
        rows2Str(board.slice(0, 3))                           +
        " |_______________|_______________|______________|\n" +
        " |               |               |              |\n" +
        rows2Str(board.slice(3, 6))                           +
        " |_______________|_______________|______________|\n" +
        " |               |               |              |\n" +
        rows2Str(board.slice(6, 9))                           +
        " |_______________|_______________|______________|\n"

      def rows2Str(rows: List[Row]): String =
        rows.foldLeft("")(_ + row2Str(_))

      def row2Str(row: Row): String =
        " |  " + cells2Str(row.slice(0,3)) +
        " |  " + cells2Str(row.slice(3,6)) +
        " |  " + cells2Str(row.slice(6,9)) + "|\n"

      def cells2Str(cells: List[Cell]): String =
        cells.foldLeft("")(_ + cell2Str(_))

      def cell2Str(cell: Cell): String =
        if (cell.isFixed)      " " + cell.value.get + ". "
        else if (cell.isBlank) "    "
        else                   " " + cell.value.get + "  "

      toStringMain
    }

    //
    // Updates one cell of current Puzzle to create a new Puzzle.
    //
    def update(row: Int, col: Int, num: Option[Int]): Puzzle = {

      def update(c: Cell): Puzzle = c match {
        case c: Cell if !c.isBlank => this
        case Cell(Some(x), y) if x > 9 || x < 1  => throw new IllegalArgumentException
        case _ => {
          val cell: Cell = new Cell(num, false)
          val rr = board(row)
          val r: Row = rr.take(col) ::: cell :: rr.drop(col + 1)
          val newBoard: Board = board.take(row) ::: r :: board.drop(row + 1)
          new Puzzle(newBoard)
        }
      }
      update(board(row).apply(col))
    }
    
    //
    // true = specified cell is fixed -- i.e. is part of original puzzle
    // and can't be modified.
    //
    def isFixed(row: Int, col: Int): Boolean = board(row)(col).isFixed

    //
    // true = winner!
    //
    def isWon(): Boolean = {
      val isPopulated =
        !board.exists(row => row.exists(cell => !cell.value.isDefined))

      isPopulated && !isScrewedUp
    }

    //
    // true = board contains duplicate values -- i.e. the board is in an
    // illegal state.  Note that a legal state doesn't mean the  values
    // are correct, just that there aren't any conflicts between populated
    // cells.
    //
    def isScrewedUp = true
  }


  //
  // Builds a Board from a list of fixed values.  List must
  // contain 81 values, the first 9 representing the first row,
  // the second 9 the second row, etc.
  //
  // A zero value represents a blank space on the board.
  //
  def makeBoard(values: List[Int]): Board = {
    def makeBoardMain = {
      if (values.length != 81)
        throw new IllegalArgumentException("wrong size " + values.length)

      init(values)
    }

    //
    // Builds a single row of Cells from a list of Ints, translating
    // each 0 value of an Int to Option None.
    //
    def buildRow(rowVals: List[Int]): Row = {
      rowVals.map(
        num =>
          if (num == 0)
            new Cell(None, false)
          else
            new Cell(Some(num), true))
    }

    //
    // Recursive method to build Board a row at a time.
    //
    def init(values: List[Int]): Board = {
      if (values == Nil)
        Nil
      else
        buildRow(values.take(9)) :: init(values.drop(9))
    }

    makeBoardMain
  }
}

