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
	
	/*
	public static Map<Location, AbstractPiece> initializer(){
		Map<Location, AbstractPiece> pieces = new HashMap<Location, AbstractPiece>(); //? HashMap<>()
		
		// Rooks
		pieces.put(new Location(File.A, 1), new Rook(PieceColor.WHITE));
		pieces.put(new Location(File.H, 1), new Rook(PieceColor.WHITE));
		pieces.put(new Location(File.A, 8), new Rook(PieceColor.BLACK));
		pieces.put(new Location(File.H, 8), new Rook(PieceColor.BLACK));
		
		// Knights
		pieces.put(new Location(File.B, 1), new Knight(PieceColor.WHITE));
		pieces.put(new Location(File.G, 1), new Knight(PieceColor.WHITE));
		pieces.put(new Location(File.B, 8), new Knight(PieceColor.BLACK));
		pieces.put(new Location(File.G, 8), new Knight(PieceColor.BLACK));
		
		// Bishops
		pieces.put(new Location(File.C, 1), new Bishop(PieceColor.WHITE));
		pieces.put(new Location(File.F, 1), new Bishop(PieceColor.WHITE));
		pieces.put(new Location(File.C, 8), new Bishop(PieceColor.BLACK));
		pieces.put(new Location(File.F, 8), new Bishop(PieceColor.BLACK));
		
		// Queen
		pieces.put(new Location(File.D, 1), new Queen(PieceColor.WHITE));
		pieces.put(new Location(File.D, 8), new Queen(PieceColor.BLACK));
		
		// King
		pieces.put(new Location(File.E, 1), new King(PieceColor.WHITE));
		pieces.put(new Location(File.E, 8), new King(PieceColor.BLACK));
				
		// Pawns - entire ranks 2 (whites) and 7 (blacks)
		for(File file : File.values()) {
			pieces.put(new Location(file, 2), new Pawn(PieceColor.WHITE));
			pieces.put(new Location(file, 7), new Pawn(PieceColor.BLACK));
		}
		
		return pieces;
	}
*/
}
