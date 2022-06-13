package com.mctschess.model.pieces;

import java.util.List;

import com.mctschess.model.board.Board;
import com.mctschess.model.location.Location;
import com.mctschess.model.location.Move;

public class Queen extends AbstractPiece implements Piece {

	private Piece bishop;
	private Piece rook;

	public Queen(PieceColor pieceColor) {
		this(pieceColor, new Bishop(pieceColor), new Rook(pieceColor));
	}

	public Queen(PieceColor pieceColor, Piece bishop, Piece rook) {
		super(pieceColor);
		this.type = PieceType.QUEEN;
		this.bishop = bishop;
		this.rook = rook;
	}

	@Override
	public void getValidMoves(Board boardState, Location currentLocation, List<Move> moves) {
		bishop.getValidMoves(boardState, currentLocation, moves);
		rook.getValidMoves(boardState, currentLocation, moves);
	}

}
