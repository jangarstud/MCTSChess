package com.mctschess.model.pieces;

import java.util.List;

import com.mctschess.model.board.Board;
import com.mctschess.model.location.Location;
import com.mctschess.model.location.Move;
import com.mctschess.model.pieces.AbstractPiece.PieceColor;
import com.mctschess.model.pieces.AbstractPiece.PieceType;

public interface Piece {

	PieceColor getColor();
	PieceType getType();
	void getValidMoves(Board boardState, Location currentLocation, List<Move> moves);
}
