package com.mctschess.dto;

import java.util.List;

import com.mctschess.model.location.Location.File;
import com.mctschess.model.pieces.AbstractPiece.PieceColor;

public class BoardDto {

	private List<LocationAndPieceDto> boardState;
	private PieceColor currentColor;

	private File enPassant;

	private boolean whiteQueensideCastling;
	private boolean whiteKingsideCastling;

	private boolean blackQueensideCastling;
	private boolean blackKingsideCastling;
	
	public BoardDto() {
	}
	
	public BoardDto(List<LocationAndPieceDto> boardState, PieceColor currentColor, File enPassant,
			boolean whiteQueensideCastling, boolean whiteKingsideCastling, boolean blackQueensideCastling,
			boolean blackKingsideCastling) {
		super();
		this.boardState = boardState;
		this.currentColor = currentColor;
		this.enPassant = enPassant;
		this.whiteQueensideCastling = whiteQueensideCastling;
		this.whiteKingsideCastling = whiteKingsideCastling;
		this.blackQueensideCastling = blackQueensideCastling;
		this.blackKingsideCastling = blackKingsideCastling;
	}


	public List<LocationAndPieceDto> getBoardState() {
		return boardState;
	}
	
	public void setBoardState(List<LocationAndPieceDto> boardState) {
		this.boardState = boardState;
	}

	public PieceColor getCurrentColor() {
		return currentColor;
	}

	public void setCurrentColor(PieceColor currentColor) {
		this.currentColor = currentColor;
	}

	public File getEnPassant() {
		return enPassant;
	}

	public void setEnPassant(File enPassant) {
		this.enPassant = enPassant;
	}

	public boolean isWhiteQueensideCastling() {
		return whiteQueensideCastling;
	}

	public void setWhiteQueensideCastling(boolean whiteQueensideCastling) {
		this.whiteQueensideCastling = whiteQueensideCastling;
	}

	public boolean isWhiteKingsideCastling() {
		return whiteKingsideCastling;
	}

	public void setWhiteKingsideCastling(boolean whiteKingsideCastling) {
		this.whiteKingsideCastling = whiteKingsideCastling;
	}

	public boolean isBlackQueensideCastling() {
		return blackQueensideCastling;
	}

	public void setBlackQueensideCastling(boolean blackQueensideCastling) {
		this.blackQueensideCastling = blackQueensideCastling;
	}

	public boolean isBlackKingsideCastling() {
		return blackKingsideCastling;
	}

	public void setBlackKingsideCastling(boolean blackKingsideCastling) {
		this.blackKingsideCastling = blackKingsideCastling;
	}
	
}
