package tetrisgame.pieces

trait TetrisPiece {
  var x: Int
  var y: Int
  var shape: Array[Array[Int]]

  def rotate(): Unit = {
    shape = shape.transpose.map(_.reverse)
  }
  def moveLeft(): Unit = x -= 1
  def moveRight(): Unit = x += 1
  def moveDown(): Unit = y += 1

  def copy: TetrisPiece = {
    val newShape = shape.map(_.clone())
    createNewInstance(x, y, newShape)
  }

  def createNewInstance(x: Int, y: Int, shape: Array[Array[Int]]): TetrisPiece
}
