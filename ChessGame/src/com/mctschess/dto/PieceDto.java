package com.mctschess.dto;

import com.mctschess.model.pieces.AbstractPiece.PieceColor;
import com.mctschess.model.pieces.AbstractPiece.PieceType;

public class PieceDto {

	private PieceColor color;
	private PieceType type;
	
	public PieceDto() {
	}
	
	public PieceDto(PieceColor color, PieceType type) {
		super();
		this.color = color;
		this.type = type;
	}

	public PieceColor getColor() {
		return color;
	}
	
	public void setColor(PieceColor color) {
		this.color = color;
	}
	
	public PieceType getType() {
		return type;
	}

	public void setType(PieceType type) {
		this.type = type;
	}
	
}
