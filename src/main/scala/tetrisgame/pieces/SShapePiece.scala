package tetrisgame.pieces

case class SShapePiece(var x: Int = 0, var y: Int = 0) extends TetrisPiece {
  var shape: Array[Array[Int]] = Array(
    Array(0, 1, 1),
    Array(1, 1, 0)
  )

  override def createNewInstance(x: Int, y: Int, shape: Array[Array[Int]]): TetrisPiece = {
    SShapePiece(x, y)
  }
}
