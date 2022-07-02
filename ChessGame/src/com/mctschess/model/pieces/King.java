package com.mctschess.model.pieces;

import java.util.List;

import com.mctschess.model.board.Board;
import com.mctschess.model.location.Location;
import com.mctschess.model.location.LocationFactory;
import com.mctschess.model.location.Move;

public class King extends AbstractPiece implements Piece {

	public King(PieceColor pieceColor) {
		super(pieceColor);
		this.type = PieceType.KING;
	}

	@Override
	public void getValidMoves(Board boardState, Location currentLocation, List<Move> moves) {
		addIfValid(boardState, currentLocation, moves, 1, 0);
		addIfValid(boardState, currentLocation, moves, 1, 1);
		addIfValid(boardState, currentLocation, moves, 0, 1);
		addIfValid(boardState, currentLocation, moves, -1, 1);
		addIfValid(boardState, currentLocation, moves, -1, 0);
		addIfValid(boardState, currentLocation, moves, -1, -1);
		addIfValid(boardState, currentLocation, moves, 0, -1);
		addIfValid(boardState, currentLocation, moves, 1, -1);
		
		addKingsideCastlingIfPossible(boardState, currentLocation, moves);
		addQueensideCastlingIfPosible(boardState, currentLocation, moves);
	}

	private void addIfValid(Board boardState, Location currentLocation, List<Move> moves, int offsetX, int offsetY) {
		Location to = LocationFactory.build(currentLocation, offsetX, offsetY);
		if (to != null && boardState.isEmptyOrCapturable(to, getColor()))
			moves.add(new Move(currentLocation, to));
	}
	
	private void addKingsideCastlingIfPossible(Board boardState, Location currentLocation, List<Move> moves) {
		Location loc1 = LocationFactory.build(currentLocation, 1, 0);
		Location toKingside = LocationFactory.build(currentLocation, 2, 0);
		
		if (!boardState.isKingsideCastling(color)) return;

		if (boardState.isOnCheck(currentLocation)) return;
		if (!boardState.isEmpty(loc1) || boardState.isOnCheck(loc1)) return;
		if (!boardState.isEmpty(toKingside) || boardState.isOnCheck(toKingside)) return;
		
		moves.add(new Move(currentLocation, toKingside));		
	}

	private void addQueensideCastlingIfPosible(Board boardState, Location currentLocation, List<Move> moves) {
		Location loc1 = LocationFactory.build(currentLocation, -1, 0);
		Location toQueenside = LocationFactory.build(currentLocation, -2, 0);
		Location loc2 = LocationFactory.build(currentLocation, -3, 0);
		
		if (!boardState.isQueensideCastling(color)) return;
		
		if (boardState.isOnCheck(currentLocation)) return;
		if (!boardState.isEmpty(loc1) || boardState.isOnCheck(loc1)) return;
		if (!boardState.isEmpty(toQueenside) || boardState.isOnCheck(toQueenside)) return;
		if (!boardState.isEmpty(loc2)) return;
		
		moves.add(new Move(currentLocation, toQueenside));
	}


}
