package tetrisgame.pieces

case class LShapePiece(var x: Int = 0, var y: Int = 0) extends TetrisPiece {
  var shape: Array[Array[Int]] = Array(
    Array(0, 1),
    Array(0, 1),
    Array(1, 1)
  )

  override def createNewInstance(x: Int, y: Int, shape: Array[Array[Int]]): TetrisPiece = {
    LShapePiece(x, y)
  }
}
