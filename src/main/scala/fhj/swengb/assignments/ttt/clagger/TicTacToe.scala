package fhj.swengb.assignments.ttt.clagger

import javafx.application.Application

import scala.collection.Set

/**
  * models the different moves the game allows
  *
  * each move is made by either player a or player b.
  */
sealed trait TMove {
  def idx: Int
}

case object TopLeft extends TMove {
  override def idx: Int = 0
}

case object TopCenter extends TMove {
  override def idx: Int = 1
}

case object TopRight extends TMove {
  override def idx: Int = 2
}

case object MiddleLeft extends TMove {
  override def idx: Int = 3
}

case object MiddleCenter extends TMove {
  override def idx: Int = 4
}

case object MiddleRight extends TMove {
  override def idx: Int = 5
}

case object BottomLeft extends TMove {
  override def idx: Int = 6
}

case object BottomCenter extends TMove {
  override def idx: Int = 7
}

case object BottomRight extends TMove {
  override def idx: Int = 8
}


/**
  * for a tic tac toe game, there are two players, player A and player B
  */
sealed trait Player

case object PlayerA extends Player

case object PlayerB extends Player

case object noPlayer extends Player //a third player is needed in order to set an "empthy node"

object TicTacToe {

 /*def main(args: Array[String]) {

   val t = TicTacToe().turn(TopRight, PlayerA).turn(MiddleCenter, PlayerA).turn(BottomLeft, PlayerA)
  // val t = TicTacToe().turn(TopRight, PlayerA).turn(MiddleRight, PlayerA).turn(BottomRight, PlayerA)

   //test output
   print(t.asString())


   //test remainingmoves
   println(t.remainingMoves.size)
   println(t.gameOver)
   println(t.winner)


    }*/


  /**
    * creates an empty tic tac toe game
    * @return
    */
  def apply(): TicTacToe = TicTacToe(Map((TopLeft,noPlayer),(TopCenter, noPlayer),(TopRight, noPlayer),
                                          (MiddleLeft, noPlayer),(MiddleCenter, noPlayer),(MiddleRight, noPlayer),
                                          (BottomLeft, noPlayer),(BottomCenter, noPlayer),(BottomRight, noPlayer)))


  /**
    * For a given tic tac toe game, this function applies all moves to the game.
    * The first element of the sequence is also the first move.
    *
    * @param t
    * @param moves
    * @return
    */
  def play(t: TicTacToe, moves: Seq[TMove]): TicTacToe = { //why do we need such a function?!

    var player: Player = PlayerA

    for(move <- moves) {
      t.turn(move, player)
      if(player.equals(PlayerA))
        player = PlayerB
      else
        player = PlayerA
    }

    return t
  }

  /**
    * creates all possible games.
    * @return
    */
  def mkGames(): Map[Seq[TMove], TicTacToe] = ???

}

/**
  * Models the well known tic tac toe game.
  *
  * The map holds the information which player controls which field.
  *
  * The nextplayer parameter defines which player makes the next move.
  */
case class TicTacToe(moveHistory: Map[TMove, Player],
                     nextPlayer: Player = PlayerA) {

  /**
    * outputs a representation of the tic tac toe like this:
    *
    * |---|---|---|
    * | x | o | x |
    * |---|---|---|
    * | o | x | x |
    * |---|---|---|
    * | x | o | o |
    * |---|---|---|
    *
    *
    * @return
    */
  def asString(): String = {

    val indexMap = Map (0->16, 1->20, 2->24,
                        3->44, 4->48, 5->52,
                        6->72, 7->76, 8->80)
    var s: String =
        "|---|---|---|\n" +
        "|   |   |   |\n" +
        "|---|---|---|\n" +
        "|   |   |   |\n" +
        "|---|---|---|\n" +
        "|   |   |   |\n" +
        "|---|---|---|\n"


    for((k,v) <- moveHistory){
      if(v == PlayerA){
        s = s.updated(indexMap(k.idx),"O").mkString
      }
      else if(v == PlayerB){
        s = s.updated(indexMap(k.idx),"X").mkString
      }
      else{
        s = s.updated(indexMap(k.idx),"-").mkString
      }
    }
    s
  }


  /**
    * is true if the game is over.
    *
    * The game is over if either of a player wins or there is a draw.
    */
  val gameOver : Boolean = {

    if(winner != None)
       true
    else
       false

  }

  def checkRow(player:Player) :Boolean = {
    if(moveHistory(TopCenter).equals(player) && moveHistory(TopLeft).equals(player) && moveHistory(TopRight).equals(player))
       true
    else if(moveHistory(MiddleCenter).equals(player) && moveHistory(MiddleLeft).equals(player) && moveHistory(MiddleRight).equals(player))
       true
    else if(moveHistory(BottomCenter).equals(player) && moveHistory(BottomLeft).equals(player) && moveHistory(BottomRight).equals(player))
       true
    else
       false
  }

  def checkColumn(player:Player) :Boolean = {
    if(moveHistory(TopCenter).equals(player) && moveHistory(MiddleCenter).equals(player) && moveHistory(BottomCenter).equals(player))
       true
    else if(moveHistory(TopLeft).equals(player) && moveHistory(MiddleLeft).equals(player) && moveHistory(BottomLeft).equals(player))
       true
    else if(moveHistory(TopRight).equals(player) && moveHistory(MiddleRight).equals(player) && moveHistory(BottomRight).equals(player))
       true
    else
       false
  }

  def checkDiag(player:Player) :Boolean = {
    if(moveHistory(TopRight).equals(player) && moveHistory(MiddleCenter).equals(player) && moveHistory(BottomLeft).equals(player))
       true
    else if(moveHistory(TopLeft).equals(player) && moveHistory(MiddleCenter).equals(player) && moveHistory(BottomRight).equals(player))
       true
    else
       false
  }


  /**
    * the moves which are still to be played on this tic tac toe.
    */
   val remainingMoves: Set[TMove] = this.moveHistory.filter(_._2.equals(noPlayer)).keySet

  /**
    * given a tic tac toe game, this function returns all
    * games which can be derived by making the next turn. that means one of the
    * possible turns is taken and added to the set.
    */
  lazy val nextGames: Set[TicTacToe] = ???

  /**
    * Either there is no winner, or PlayerA or PlayerB won the game.
    *
    * The set of moves contains all moves which contributed to the result.
    */
  def winner: Option[(Player, Set[TMove])] = {
    // player A win
    if(checkRow(PlayerA) || checkDiag(PlayerA) || checkColumn(PlayerA)){
      val set = moveHistory.filter(_._2.equals(PlayerA)).keySet
       Some(PlayerA,set)
    }
    //player B win
    else if(checkRow(PlayerB) || checkDiag(PlayerB) || checkColumn(PlayerB)){
      val set = moveHistory.filter(_._2.equals(PlayerB)).keySet
       Some(PlayerB, set)
    }
    // draw
   else if(this.moveHistory.filter(_._2.equals(noPlayer)).keySet.isEmpty)
      Some(noPlayer, moveHistory.keySet)
    else
       None

  }

  /**
    * returns a copy of the current game, but with the move applied to the tic tac toe game.
    *
    * @param p to be played
    * @param player the player
    * @return
    */
  def turn(p: TMove, player: Player): TicTacToe = {


   /* //switch players
    var nextPlayer: Player = player
    if(nextPlayer.equals(PlayerA))
      nextPlayer = PlayerB
    else
      nextPlayer = PlayerA

      */

    //check if field is not set yet
    if(this.moveHistory.get(p).contains(noPlayer)){
      if (player.equals(PlayerA))
         TicTacToe((this.moveHistory + (p -> player)), PlayerB)
      else
         TicTacToe((this.moveHistory + (p -> player)), PlayerA)

    }
    else{ //if its set do nothing and return the original map
       TicTacToe(this.moveHistory, nextPlayer)
    }





  }

}


