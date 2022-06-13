package com.mctschess.model.pieces;

import java.util.List;

import com.mctschess.model.board.Board;
import com.mctschess.model.location.Location;
import com.mctschess.model.location.LocationFactory;
import com.mctschess.model.location.Move;

public class Pawn extends AbstractPiece implements Piece {

	public Pawn(PieceColor pieceColor) {
		super(pieceColor);
		this.type = PieceType.PAWN;
	}

	@Override
	public void getValidMoves(Board boardState, Location currentLocation, List<Move> moves) {
		if (color == PieceColor.WHITE) {
			Location loc1 = LocationFactory.build(currentLocation, 0, 1);
			if (boardState.getPieceAt(loc1) == null) {
				addMoves(currentLocation, moves, loc1, color); // Must add methods for all the cases
				if (currentLocation.getRank() == 2) {
					Location loc2 = LocationFactory.build(currentLocation, 0, 2);
					if (boardState.getPieceAt(loc2) == null) {
						addMoves(currentLocation, moves, loc2, color);
					}
				}
			}
			Location locRightDia1 = LocationFactory.build(currentLocation, 1, 1);
			if (locRightDia1 != null && boardState.getPieceAt(locRightDia1) != null
					&& boardState.getPieceAt(locRightDia1).getColor() != color) {
				addMoves(currentLocation, moves, locRightDia1, color);
			} else {
				if (locRightDia1 != null && locRightDia1.getRank() == 6
						&& boardState.isEnPassantAllowed(locRightDia1.getFile())) {
					addMoves(currentLocation, moves, locRightDia1, color);
				}
			}

			Location locLeftDia2 = LocationFactory.build(currentLocation, -1, 1);
			if (locLeftDia2 != null && boardState.getPieceAt(locLeftDia2) != null
					&& boardState.getPieceAt(locLeftDia2).getColor() != color) {
				addMoves(currentLocation, moves, locLeftDia2, color);
			} else {
				if (locLeftDia2 != null && locLeftDia2.getRank() == 6
						&& boardState.isEnPassantAllowed(locLeftDia2.getFile())) {
					addMoves(currentLocation, moves, locLeftDia2, color);
				}
			}
		} else {

			Location loc1 = LocationFactory.build(currentLocation, 0, -1);
			if (boardState.getPieceAt(loc1) == null) {
				addMoves(currentLocation, moves, loc1, color);
				if (currentLocation.getRank() == 7) {
					Location loc2 = LocationFactory.build(currentLocation, 0, -2);
					if (boardState.getPieceAt(loc2) == null) {
						addMoves(currentLocation, moves, loc2, color);
					}
				}
			}

			Location locRightDia1 = LocationFactory.build(currentLocation, 1, -1);
			if (locRightDia1 != null && boardState.getPieceAt(locRightDia1) != null
					&& boardState.getPieceAt(locRightDia1).getColor() != color) {
				addMoves(currentLocation, moves, locRightDia1, color);
			} else {
				if (locRightDia1 != null && locRightDia1.getRank() == 3
						&& boardState.isEnPassantAllowed(locRightDia1.getFile())) {
					addMoves(currentLocation, moves, locRightDia1, color);
				}
			}
			Location locLeftDia2 = LocationFactory.build(currentLocation, -1, -1);
			if (locLeftDia2 != null && boardState.getPieceAt(locLeftDia2) != null
					&& boardState.getPieceAt(locLeftDia2).getColor() != color) {
				addMoves(currentLocation, moves, locLeftDia2, color);
			} else {
				if (locLeftDia2 != null && locLeftDia2.getRank() == 3
						&& boardState.isEnPassantAllowed(locLeftDia2.getFile())) {
					addMoves(currentLocation, moves, locLeftDia2, color);
				}
			}
		}

	}

	private void addMoves(Location currentLocation, List<Move> moves, Location to, PieceColor color) {
		if ((color == PieceColor.WHITE && to.getRank() == 8) || (color == PieceColor.BLACK && to.getRank() == 1)) {
			moves.add(new Move(currentLocation, to, PieceFactory.getBishop(color)));
			moves.add(new Move(currentLocation, to, PieceFactory.getRook(color)));
			moves.add(new Move(currentLocation, to, PieceFactory.getKnight(color)));
			moves.add(new Move(currentLocation, to, PieceFactory.getQueen(color)));
		} else {
			moves.add(new Move(currentLocation, to));
		}

	}
}
