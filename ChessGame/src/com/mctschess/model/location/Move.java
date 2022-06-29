package com.mctschess.model.location;

import java.util.Objects;

import com.mctschess.model.pieces.Piece;
import com.mctschess.model.pieces.PieceFactory;
import com.mctschess.dto.MoveDto;
import com.mctschess.model.location.Location.File;
import com.mctschess.model.pieces.AbstractPiece.PieceColor;

public class Move {

	private Location from;
	private Location to;
	private Piece promotionPiece;
	
	public Move(Location from, Location to, Piece promotionPiece) {
		this.from = from;
		this.to = to;
		this.promotionPiece = promotionPiece;
	}

	public Move(Location from, Location to) {
		this(from, to, null);
	}

	public Location getFrom() {
		return from;
	}
	
	public Location getTo() {
		return to;
	}
	
	public Piece getPromotionPiece() {
		return promotionPiece;
	}
	
	public boolean isKingsideCastling(PieceColor color) {
		if (color == PieceColor.WHITE) {
			return from.getFile() == File.E && from.getRank() == 1 && to.getFile() == File.G;
		}
		else {
			return from.getFile() == File.E && from.getRank() == 8 && to.getFile() == File.G;
		}
	}
	
	public boolean isQueensideCastling(PieceColor color) {
		if (color == PieceColor.WHITE) {
			return from.getFile() == File.E && from.getRank() == 1 && to.getFile() == File.C;
		}
		else {
			return from.getFile() == File.E && from.getRank() == 8 && to.getFile() == File.C;
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(from, promotionPiece, to);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Move other = (Move) obj;
		return Objects.equals(from, other.from) && Objects.equals(promotionPiece, other.promotionPiece)
				&& Objects.equals(to, other.to);
	}

	@Override
	public String toString() {
		return "Move [from=" + from + ", to=" + to + ", promotionPiece=" + promotionPiece + "]";
	}
	
	public static Move fromDto(MoveDto dto) {
		return new Move(Location.fromDto(dto.getFrom()), Location.fromDto(dto.getTo()), PieceFactory.fromDto(dto.getPromotionPiece()));
	}

	public MoveDto toDto() {
		return new MoveDto(from.toDto(), to.toDto(), promotionPiece == null ? null : promotionPiece.toDto());
	}
	
	
}
