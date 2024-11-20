package tetrisgame.pieces

case class IShapePiece(var x: Int = 0, var y: Int = 0) extends TetrisPiece {
  var shape: Array[Array[Int]] = Array(
    Array(1),
    Array(1),
    Array(1),
    Array(1)
  )

  override def createNewInstance(x: Int, y: Int, shape: Array[Array[Int]]): TetrisPiece = {
    IShapePiece(x, y)
  }
}
