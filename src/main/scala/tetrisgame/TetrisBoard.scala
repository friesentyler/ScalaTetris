package tetrisgame

import scalafx.scene.input.KeyCode.A
import tetrisgame.pieces.{IShapePiece, JShapePiece, LShapePiece, OShapePiece, SShapePiece, TShapePiece, TetrisPiece, ZShapePiece}

import scala.annotation.tailrec
import scala.util.Random

object TetrisBoard {
  val rows: Int = 20
  val columns: Int = 10
  var score: Int = 0
  var gameBoard: Array[Array[Int]] = Array.fill(rows, columns)(0)
  var tetrisPieces: Array[TetrisPiece] = Array(LShapePiece(), TShapePiece(), OShapePiece(), IShapePiece(), JShapePiece(), SShapePiece(), ZShapePiece())
  var currentPiece: TetrisPiece = generateNewPiece()

  def generateNewPiece(): TetrisPiece = {
    val piece = tetrisPieces(Random.nextInt(tetrisPieces.length))
    piece.x = columns / 2 - 1
    piece.y = 0
    piece
  }

  def mergePieceToBoard(): Array[Array[Int]] = {
    val tempBoard = gameBoard.map(_.clone())

    currentPiece.shape.zipWithIndex.foreach { case (row, dy) =>
      row.zipWithIndex.foreach { case (block, dx) =>
        if (block == 1) {
          val x = currentPiece.x + dx
          val y = currentPiece.y + dy
          println(s"merging piece at: (x: $x, y: $y)")
          if (y >= 0 && y < rows && x >= 0 && x < columns) {
            tempBoard(y)(x) = 1
          }
        }
      }
    }
    tempBoard
  }

  def printCurrentPiece() = {
    currentPiece.shape.foreach(row => println(row.mkString(" ")))
  }

  def rotateCurrentPiece() = {
    //println("rotate")
    var clonePiece = currentPiece.copy
    clonePiece.rotate()
    if (isValidMove(clonePiece)) {
      currentPiece.rotate()
    }
  }

  def moveCurrentPieceDown() = {
    //println("down")
    var clonePiece = currentPiece.copy
    clonePiece.moveDown()
    if (isValidMove(clonePiece)) {
      currentPiece.moveDown()
    } else if (clonePiece.y == 0 || clonePiece.y == 1) {
      clearBoard()
    } else {
      gameBoard = mergePieceToBoard()
      score += 5
      clearFullRows()
      currentPiece = generateNewPiece()
    }
  }

  def moveCurrentPieceRight() = {
    //println("right")
    var clonePiece = currentPiece.copy
    clonePiece.moveRight()
    if (isValidMove(clonePiece)) {
      currentPiece.moveRight()
    }
  }

  def moveCurrentPieceLeft() = {
    //println("left")
    var clonePiece = currentPiece.copy
    clonePiece.moveLeft()
    if (isValidMove(clonePiece)) {
      currentPiece.moveLeft()
    }
  }

  def isValidMove(piece: TetrisPiece): Boolean = {
    currentPiece.shape.zipWithIndex.forall { case (row, dy) =>
      row.zipWithIndex.forall { case (block, dx) =>
        if (block == 1) {
          //val x = currentPiece.x + dx
          val x = piece.x + dx
          val y = piece.y + dy

          if (x < 0 || x >= columns || y < 0 || y >= rows) {
            false
          } else {
            gameBoard(y)(x) == 0
          }
        } else {
          true
        }
      }
    }
  }

  def clearBoard(): Unit = {
    Thread.sleep(5000)
    gameBoard.zipWithIndex.foreach { case (row, rowIdx) =>
      row.zipWithIndex.foreach { case (value ,colIdx) =>
        gameBoard(rowIdx)(colIdx) = 0
      }
    }
    score = 0
  }

  def clearFullRows(): Unit = {
    var isFullRow = true
    for (row <- gameBoard.indices) {
      isFullRow = true
      for (element <- gameBoard(row).indices) {
        if (gameBoard(row)(element) == 0) {
          isFullRow = false
        }
      }
      if (isFullRow) {
        for (element <- gameBoard(row).indices) {
          gameBoard(row)(element) = 0
        }
        score += 300
        shiftRowsDown(row)
      }
    }
  }

  @tailrec
  private def shiftRowsDown(startRow: Int): Unit = {
    if (startRow <= 0) {
      gameBoard(0) = Array.fill(gameBoard(0).length)(0)
      return
    }
    gameBoard(startRow) = gameBoard(startRow-1)
    shiftRowsDown(startRow-1)
  }
}
