package tetrisgame.pieces

case class JShapePiece(var x: Int = 0, var y: Int = 0) extends TetrisPiece {
  var shape: Array[Array[Int]] = Array(
    Array(1, 0),
    Array(1, 0),
    Array(1, 1),
  )

  override def createNewInstance(x: Int, y: Int, shape: Array[Array[Int]]): TetrisPiece = {
    JShapePiece(x, y)
  }
}
