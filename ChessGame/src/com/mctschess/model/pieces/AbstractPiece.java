package com.mctschess.model.pieces;

import java.util.Objects;

import com.mctschess.dto.PieceDto;

public abstract class AbstractPiece implements Piece{
	public enum PieceType{ROOK, NKNIGHT, BISHOP, QUEEN, KING, PAWN}; //KNIGHT starts with an 'N' to differentiate itself from the KING
	protected PieceType type;
	public enum PieceColor{WHITE, BLACK};
	protected PieceColor color;
	
	public AbstractPiece(PieceColor pieceColor) {
		this.color = pieceColor;
	}
	
	public PieceType getType() {
		return this.type;
	}
	
	public PieceColor getColor() {
		return this.color;
	}
	
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
	
	@Override
	public PieceDto toDto() {
		return new PieceDto(color, type);
	}


}
