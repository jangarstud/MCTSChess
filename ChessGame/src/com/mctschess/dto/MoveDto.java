package com.mctschess.dto;

public class MoveDto {

	private LocationDto from;
	private LocationDto to;
	private PieceDto promotionPiece;
	
	public MoveDto() {
	}
	
	public MoveDto(LocationDto from, LocationDto to, PieceDto promotionPiece) {
		super();
		this.from = from;
		this.to = to;
		this.promotionPiece = promotionPiece;
	}

	public LocationDto getFrom() {
		return from;
	}

	public void setFrom(LocationDto from) {
		this.from = from;
	}

	public LocationDto getTo() {
		return to;
	}

	public void setTo(LocationDto to) {
		this.to = to;
	}

	public PieceDto getPromotionPiece() {
		return promotionPiece;
	}

	public void setPromotionPiece(PieceDto promotionPiece) {
		this.promotionPiece = promotionPiece;
	}
	
	
	
	
}
