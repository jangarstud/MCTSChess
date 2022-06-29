package com.mctschess.model.board;

import java.util.List;

import com.mctschess.dto.BoardDto;
import com.mctschess.model.location.Location;
import com.mctschess.model.location.Location.File;
import com.mctschess.model.pieces.AbstractPiece.PieceColor;
import com.mctschess.model.pieces.Piece;
import com.mctschess.model.location.Move;

public interface IBoard {
	
	List<Move> getValidMoves();

	List<Move> getValidMoves(Location l);

	Piece getPieceAt(Location l);
	
	IBoard applyMove(Move move);

	boolean isEnPassantAllowed(File file);
	
	boolean isKingOnCheck();
	
	PieceColor getCurrentColor();
	
	int getAvailablePieces();
	
	boolean isOnDeadPosition();

	BoardDto toDto();
}
