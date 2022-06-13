package com.mctschess.model.pieces;

import java.util.List;

import com.mctschess.model.board.Board;
import com.mctschess.model.location.Location;
import com.mctschess.model.location.LocationFactory;
import com.mctschess.model.location.Move;

public class Bishop extends AbstractPiece implements Piece {

	public Bishop(PieceColor pieceColor) {
		super(pieceColor);
		this.type = PieceType.BISHOP;
	}
	
	@Override
	public void getValidMoves(Board boardState, Location currentLocation, List<Move> moves) {
		getMoves(moves, boardState, currentLocation, 1, 1);
		getMoves(moves, boardState, currentLocation, 1, -1);
		getMoves(moves, boardState, currentLocation, -1, -1);
		getMoves(moves, boardState, currentLocation, -1, 1);
	}

	private void getMoves(List<Move> possibleMoves, Board currentBoardState,
			Location currentLocation, int rankOffset, int fileOffset) {
		
		Location next = LocationFactory.build(currentLocation, fileOffset, rankOffset);
		while (next != null) {
			if (currentBoardState.isEmpty(next)) {
				possibleMoves.add(new Move(currentLocation, next));
			}
			else if (currentBoardState.isEmptyOrCapturable(next, getColor())) {
				possibleMoves.add(new Move(currentLocation, next));
				break;
			}
			else {
				break;
			}
			next = LocationFactory.build(next, fileOffset, rankOffset);
		}
	}

}
