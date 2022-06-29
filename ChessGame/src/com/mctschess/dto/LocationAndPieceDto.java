package com.mctschess.dto;

public class LocationAndPieceDto {
	
	private LocationDto location;
	private PieceDto piece;
	
	public LocationAndPieceDto() {
	}

	public LocationAndPieceDto(LocationDto location, PieceDto pieceDto) {
		super();
		this.location = location;
		this.piece = pieceDto;
	}

	public LocationDto getLocation() {
		return location;
	}

	public void setLocation(LocationDto location) {
		this.location = location;
	}

	public PieceDto getPiece() {
		return piece;
	}

	public void setPiece(PieceDto piece) {
		this.piece = piece;
	}
	
}
