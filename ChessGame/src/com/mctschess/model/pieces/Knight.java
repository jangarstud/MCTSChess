package com.mctschess.model.pieces;

import java.util.List;

import com.mctschess.model.board.Board;
import com.mctschess.model.location.Location;
import com.mctschess.model.location.LocationFactory;
import com.mctschess.model.location.Move;

public class Knight extends AbstractPiece implements Piece {

	public Knight(PieceColor pieceColor) {
		super(pieceColor);
		this.type = PieceType.NKNIGHT;
	}

	@Override
	public void getValidMoves(Board boardState, Location currentLocation, List<Move> moves) {
		addIfValid(boardState, currentLocation, moves, 1, 2);
		addIfValid(boardState, currentLocation, moves, 2, 1);
		addIfValid(boardState, currentLocation, moves, 2, -1);
		addIfValid(boardState, currentLocation, moves, 1, -2);
		addIfValid(boardState, currentLocation, moves, -1, -2);
		addIfValid(boardState, currentLocation, moves, -2, -1);
		addIfValid(boardState, currentLocation, moves, -2, 1);
		addIfValid(boardState, currentLocation, moves, -1, 2);
	}

	private void addIfValid(Board boardState, Location currentLocation, List<Move> moves, int offsetX, int offsetY) {
		Location to = LocationFactory.build(currentLocation, offsetX, offsetY);
		if (to != null && boardState.isEmptyOrCapturable(to, getColor()))
			moves.add(new Move(currentLocation, to));
	}

}
