package com.mctschess.model.pieces;

import java.util.Objects;

public abstract class AbstractPiece implements Piece{
	public enum PieceType{ROOK, NKNIGHT, BISHOP, QUEEN, KING, PAWN}; //KNIGHT starts with an 'N' to differentiate itself from the KING
	protected PieceType type;
	public enum PieceColor{WHITE, BLACK};
	protected PieceColor color;
	//protected Square currentSquare;
	
	public AbstractPiece(PieceColor pieceColor) {
		this.color = pieceColor;
	}
	
	public PieceType getType() {
		return this.type;
	}
	
	public PieceColor getColor() {
		return this.color;
	}
	
	/*
	public Square getCurrentSquare() {
		return this.currentSquare;
	}
	
	public void setCurrentSquare(Square currentSquare) {
		this.currentSquare = currentSquare;
	}
	*/
	
	@Override
	public String toString() {
		return "Piece{" +
			"Type = " + type + '\'' +
			"; Color = " + color +
			"}";
		
	}

	@Override
	public int hashCode() {
		return Objects.hash(color, type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractPiece other = (AbstractPiece) obj;
		return color == other.color && type == other.type;
	}
	
	
	

}