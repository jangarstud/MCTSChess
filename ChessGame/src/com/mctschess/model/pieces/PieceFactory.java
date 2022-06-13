package com.mctschess.model.pieces;

import com.mctschess.model.pieces.AbstractPiece.PieceColor;

public class PieceFactory {
	
	private static AbstractPiece[] rooks = new AbstractPiece[] {
			new Rook(PieceColor.WHITE),
			new Rook(PieceColor.BLACK)
	};
	
	private static AbstractPiece[] knights = new AbstractPiece[] {
			new Knight(PieceColor.WHITE),
			new Knight(PieceColor.BLACK)
	};
	
	private static AbstractPiece[] bishops = new AbstractPiece[] {
			new Bishop(PieceColor.WHITE),
			new Bishop(PieceColor.BLACK)
	};
	
	private static AbstractPiece[] queen = new AbstractPiece[] {
			new Queen(PieceColor.WHITE),
			new Queen(PieceColor.BLACK)
	};
	
	private static AbstractPiece[] king = new AbstractPiece[] {
			new King(PieceColor.WHITE),
			new King(PieceColor.BLACK)
	};
	
	private static AbstractPiece[] pawns = new AbstractPiece[] {
			new Pawn(PieceColor.WHITE),
			new Pawn(PieceColor.BLACK)
	};
	
	
	public static AbstractPiece getRook(PieceColor color) {
		return rooks[color.ordinal()];
	}
	
	public static AbstractPiece getKnight(PieceColor color) {
		return knights[color.ordinal()];
	}
	
	public static AbstractPiece getBishop(PieceColor color) {
		return bishops[color.ordinal()];
	}
	
	public static AbstractPiece getQueen(PieceColor color) {
		return queen[color.ordinal()];
	}
	
	public static AbstractPiece getKing(PieceColor color) {
		return king[color.ordinal()];
	}
	
	public static AbstractPiece getPawn(PieceColor color) {
		return pawns[color.ordinal()];
	}
	
}
