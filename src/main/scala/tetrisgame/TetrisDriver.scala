package tetrisgame

import scalafx.Includes.jfxSceneProperty2sfx
import scalafx.application.{JFXApp3, Platform}
import scalafx.scene.Scene
import scalafx.scene.input.{KeyCode, KeyEvent}
import scalafx.scene.paint.Color._
import scalafx.Includes._
import scalafx.scene.paint.Color
import scalafx.scene.shape.Rectangle
import tetrisgame.TetrisBoard

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.Random

object TetrisDriver extends JFXApp3 {
  private val board = TetrisBoard
  private val blockSize = 25

  def gameLoop(): Unit = {
    Future {
      board.moveCurrentPieceDown()
      updateScene()
      Thread.sleep(500)
    }.flatMap(_ => Future(gameLoop()))
  }

  def block(xr: Double, yr: Double, color: Color): Rectangle = new Rectangle {
    x = xr
    y = yr
    width = blockSize
    height = blockSize
    fill = color
  }

  def randomColor(): Color = {
    val r = Random.nextDouble()
    val g = Random.nextDouble()
    val b = Random.nextDouble()

    Color.rgb((r * 255).toInt, (g * 255).toInt, (b * 255).toInt)
  }

  def renderBoard(): Array[Rectangle] = {
    val tempBoard = board.mergePieceToBoard()

    tempBoard.zipWithIndex.flatMap { case (row, y) =>
      val currentColor = randomColor()
      row.zipWithIndex.collect {
        case (1, x) =>
          block(x * blockSize, y * blockSize, currentColor)
      }
    }
  }

  def updateScene(): Unit = {
    Platform.runLater {
      val newContent = renderBoard()
      stage.scene().content = newContent
    }
  }

  override def start(): Unit = {
    stage = new JFXApp3.PrimaryStage {
      width = 250
      height = 525
      scene = new Scene {
        fill = White
        onKeyPressed = (event: KeyEvent) => {
          //board.printCurrentPiece()
          event.code match {
            case KeyCode.Left => board.moveCurrentPieceLeft()
            case KeyCode.Right => board.moveCurrentPieceRight()
            case KeyCode.Down => board.moveCurrentPieceDown()
            case KeyCode.Up => board.rotateCurrentPiece()
            case _ => // ignore
          }
          updateScene()
        }
      }
    }
    gameLoop()
  }
}